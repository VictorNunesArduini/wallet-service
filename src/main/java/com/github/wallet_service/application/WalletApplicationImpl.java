package com.github.wallet_service.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.github.wallet_service.application.model.OrderDTO;
import com.github.wallet_service.application.model.WalletDTO;
import com.github.wallet_service.application.port.WalletApplication;
import com.github.wallet_service.infrastructure.exitpoint.repository.entity.TransactionEntity;
import com.github.wallet_service.infrastructure.exitpoint.repository.entity.WalletEntity;
import com.github.wallet_service.infrastructure.port.TransactionRepository;
import com.github.wallet_service.infrastructure.port.UserRepository;
import com.github.wallet_service.infrastructure.port.WalletRepository;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Singleton
@Service
public class WalletApplicationImpl implements WalletApplication {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    private final static ConcurrentHashMap<Long, Boolean> processingUsers = new ConcurrentHashMap<>();

    @Override
    public WalletDTO create(WalletDTO wallet) {

        final var userEntity = userRepository.findById(wallet.getUserId())
            .orElseThrow(() -> new RuntimeException(String.format("User not found! userId: %s.", wallet.getUserId())));

        final var walletEntity = walletRepository.save(wallet.toEntity(userEntity));

        return WalletDTO.from(walletEntity);
    }

    @Override
    public void executeOrder(WalletDTO wallet, OrderDTO order) {
        if (processingUsers.putIfAbsent(wallet.getUserId(), true) != null)
            return;
        
        try {
            final var currentWallet = walletRepository.findByIdAndUserId(wallet.getId(), wallet.getUserId())
                .orElseThrow(() -> new RuntimeException(String.format("Wallet not found! walletId: %s, userId: %s.", wallet.getId(), wallet.getUserId())));
            
            switch (order.getType()) {

                case DEPOSIT -> executeDeposit(order, currentWallet);
                case WITHDRAW -> executeWithdraw(order, currentWallet);
                case TRANSFER -> executeTransfer(order, currentWallet, wallet.getTargetId());
                default -> throw new AssertionError("Order type not allowed: ".concat(order.getType().name()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while executing operation.", e);
        } finally {
            processingUsers.remove(wallet.getUserId());
        }
        
    }

    private void executeDeposit(OrderDTO order, WalletEntity currentWallet) {
        final var newBalance = currentWallet.getBalance().add(order.getValue());
        transactionRepository.save(
            TransactionEntity.builder()
                .orderValue(order.getValue())
                .orderType(order.getType().name())
                .balance(newBalance)
                .wallet(updateWalletBalance(currentWallet, newBalance))
                .createdAt(LocalDateTime.now())
                .build()
        );
    }
    
    private void executeWithdraw(OrderDTO order, WalletEntity currentWallet) {
        final var newBalance = currentWallet.getBalance().subtract(order.getValue());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException(String.format("Insufficient balance: %s", currentWallet.getBalance()));

        transactionRepository.save(
            TransactionEntity.builder()
                .orderValue(order.getValue())
                .orderType(order.getType().name())
                .balance(newBalance)
                .wallet(updateWalletBalance(currentWallet, newBalance))
                .createdAt(LocalDateTime.now())
                .build()
        );
    }
    
    private void executeTransfer(OrderDTO order, WalletEntity currentWallet, Long targetWalletId) {
        final var targetWallet = walletRepository.findByIdAndUserId(targetWalletId, currentWallet.getUser().getId())
                .orElseThrow(() -> new RuntimeException(String.format("Target wallet not found for this user! walletId: %s, userId: %s.", targetWalletId, currentWallet.getUser().getId())));

        final var subtractedBalance = currentWallet.getBalance().subtract(order.getValue());
    
        if (subtractedBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException(String.format("Insufficient balance: %s", currentWallet.getBalance()));
        

        transactionRepository.save(
            TransactionEntity.builder()
                .orderValue(order.getValue())
                .orderType(order.getType().name())
                .balance(subtractedBalance)
                .wallet(updateWalletBalance(currentWallet, subtractedBalance))
                .createdAt(LocalDateTime.now())
                .build()
        );
    
        final var addedBalance = targetWallet.getBalance().add(order.getValue());    

        transactionRepository.save(
            TransactionEntity.builder()
                .orderValue(order.getValue())
                .orderType(order.getType().name())
                .balance(addedBalance)
                .wallet(updateWalletBalance(targetWallet, addedBalance))
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    private WalletEntity updateWalletBalance(WalletEntity wallet, BigDecimal newBalance) {
        WalletDTO updatedWallet = WalletDTO.from(wallet);
        return walletRepository.save(updatedWallet.toEntity(wallet.getUser(), newBalance));
    }

    @Override
    public BigDecimal retrieveBalance(WalletDTO wallet, LocalDate date) {

        if (date != null) {
            return transactionRepository.findLastBalanceOnThisDate(wallet.getId(), date)
                .orElseThrow(() -> new RuntimeException(String.format("Transaction not found on this date! walletId: %s, date: %s.", wallet.getId(),date)));
        }

        return walletRepository.findByIdAndUserId(wallet.getId(), wallet.getUserId())
            .map(retrievedWallet -> retrievedWallet.getBalance())
            .orElseThrow(() -> new RuntimeException(String.format("Wallet not found! walletId: %s, userId: %s.", wallet.getId(), wallet.getUserId())));
    }
    
}
