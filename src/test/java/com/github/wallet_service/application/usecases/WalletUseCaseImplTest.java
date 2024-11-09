package com.github.wallet_service.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import com.github.wallet_service.application.model.OrderDTO;
import com.github.wallet_service.application.model.OrderTypeEnum;
import com.github.wallet_service.application.model.WalletDTO;
import com.github.wallet_service.infrastructure.outbound.repository.entity.TransactionEntity;
import com.github.wallet_service.infrastructure.outbound.repository.entity.UserEntity;
import com.github.wallet_service.infrastructure.outbound.repository.entity.WalletEntity;
import com.github.wallet_service.infrastructure.port.TransactionRepository;
import com.github.wallet_service.infrastructure.port.UserRepository;
import com.github.wallet_service.infrastructure.port.WalletRepository;

@ExtendWith(MockitoExtension.class)
public class WalletUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WalletUseCaseImpl walletApplication;


    @Test
    public void givenWalletInformation_whenWallet_thenReturnCreatedWaller() {
        final var createdAt =  LocalDateTime.now();
        final var updatedAt =  LocalDateTime.now();

        UserEntity mockedUserEntity = buildUserEntityMock();
        WalletEntity mockedWalletEntity = buildWalletEntityMock(new BigDecimal(100));

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockedUserEntity));

        when(walletRepository.save(any(WalletEntity.class))).thenReturn(mockedWalletEntity);

        WalletDTO wallet = WalletDTO.builder()
            .name("Wallet 1")
            .userId(1L)
            .build();

        final var createdWallet = walletApplication.create(wallet);

        assertEquals(1L, createdWallet.getId());
        assertEquals(new BigDecimal(100), createdWallet.getBalance());
        assertEquals("Wallet 1", createdWallet.getName());
        assertEquals(createdAt.getMinute(), createdWallet.getCreatedAt().getMinute());
        assertEquals(updatedAt.getMinute(), createdWallet.getUpdatedAt().getMinute());
    }

    @Test
    public void givenWalletAndOrderInformation_whenExecuteDepositOrder_thenExecuteOrder() {
        WalletEntity currentMockedWalletEntity = buildWalletEntityMock(new BigDecimal(100));

        OrderDTO orderDTO = OrderDTO.builder()
            .value(new BigDecimal(50))
            .type(OrderTypeEnum.DEPOSIT)
            .build();

        WalletDTO wallet = WalletDTO.builder()
            .id(1L)
            .userId(1L)
            .build();

        WalletEntity newWalletEntity = buildWalletEntityMock(currentMockedWalletEntity.getBalance().add(orderDTO.getValue()));

        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(currentMockedWalletEntity));
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(newWalletEntity);

        walletApplication.executeOrder(wallet, orderDTO);

        verify(transactionRepository).save(any(TransactionEntity.class));
    }

    @Test
    public void givenWalletAndOrderInformation_whenExecuteWithdrawOrder_thenExecuteOrder() {
        WalletEntity currentMockedWalletEntity = buildWalletEntityMock(new BigDecimal(100));

        OrderDTO orderDTO = OrderDTO.builder()
            .value(new BigDecimal(50))
            .type(OrderTypeEnum.WITHDRAW)
            .build();

        WalletDTO wallet = WalletDTO.builder()
            .id(1L)
            .userId(1L)
            .build();

        WalletEntity newWalletEntity = buildWalletEntityMock(currentMockedWalletEntity.getBalance().subtract(orderDTO.getValue()));

        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(currentMockedWalletEntity));
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(newWalletEntity);

        walletApplication.executeOrder(wallet, orderDTO);

        verify(transactionRepository).save(any(TransactionEntity.class));
    }

    @Test
    public void givenWalletAndOrderInformation_whenExecuteWithdrawOrderWithInsufficientBalance_thenThrowException() {
        WalletEntity currentMockedWalletEntity = buildWalletEntityMock(new BigDecimal(100));

        OrderDTO orderDTO = OrderDTO.builder()
            .value(new BigDecimal(150))
            .type(OrderTypeEnum.WITHDRAW)
            .build();

        WalletDTO wallet = WalletDTO.builder()
            .id(1L)
            .userId(1L)
            .build();

        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(currentMockedWalletEntity));

        assertThrows(
            RuntimeException.class, 
            () -> walletApplication.executeOrder(wallet, orderDTO),
            "Insufficient balance"
        );
    }
    
    @Test
    public void givenWalletAndOrderInformation_whenExecuteTransferOrder_thenExecuteOrder() {
        WalletEntity currentMockedWalletEntity = buildWalletEntityMock(new BigDecimal(100));
        WalletEntity targetMockedWalletEntity = buildWalletEntityMock(2L, new BigDecimal(200));

        OrderDTO orderDTO = OrderDTO.builder()
            .value(new BigDecimal(50))
            .type(OrderTypeEnum.TRANSFER)
            .build();

        WalletDTO wallet = WalletDTO.builder()
            .id(1L)
            .userId(1L)
            .targetId(2L)
            .build();

        WalletEntity newWalletEntity = buildWalletEntityMock(currentMockedWalletEntity.getBalance().subtract(orderDTO.getValue()));

        when(walletRepository.findByIdAndUserId(currentMockedWalletEntity.getId(), 1L)).thenReturn(Optional.of(currentMockedWalletEntity));
        when(walletRepository.findByIdAndUserId(targetMockedWalletEntity.getId(), 1L)).thenReturn(Optional.of(targetMockedWalletEntity));
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(newWalletEntity);

        walletApplication.executeOrder(wallet, orderDTO);

        verify(transactionRepository, times(2)).save(any(TransactionEntity.class));
    }

    @Test
    public void givenWalletAndDateInformation_whenExecuteRetrieveBalance_thenReturnBalance() {
        WalletDTO wallet = WalletDTO.builder()
            .id(1L)
            .userId(1L)
            .build();

        LocalDate startDate = LocalDate.now().minusDays(1L);

        when(transactionRepository.findLastBalanceOnThisDate(wallet.getId(), startDate)).thenReturn(Optional.of(new BigDecimal(100)));

        final var balance = walletApplication.retrieveBalance(wallet, startDate);

        assertEquals(new BigDecimal(100), balance);
    }

    @Test
    public void givenWalletAndDateNullInformation_whenExecuteRetrieveBalance_thenReturnBalance() {
        WalletDTO wallet = WalletDTO.builder()
            .id(1L)
            .userId(1L)
            .build();

        WalletEntity currentMockedWalletEntity = buildWalletEntityMock(new BigDecimal(100));

        when(walletRepository.findByIdAndUserId(wallet.getId(), wallet.getUserId())).thenReturn(Optional.of(currentMockedWalletEntity));

        final var balance = walletApplication.retrieveBalance(wallet, null);

        assertEquals(currentMockedWalletEntity.getBalance(), balance);
    }

    private WalletEntity buildWalletEntityMock(Long id, BigDecimal balance) {

        return WalletEntity.builder()
            .id(id)
            .name("Wallet 1")
            .user(buildUserEntityMock())
            .balance(balance)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    private WalletEntity buildWalletEntityMock(BigDecimal balance) {
        
        return WalletEntity.builder()
            .id(1L)
            .name("Wallet 1")
            .user(buildUserEntityMock())
            .balance(balance)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    private UserEntity buildUserEntityMock() {

        return UserEntity.builder()
            .id(1L)
            .name("Victor Arduini")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
