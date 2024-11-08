package com.github.wallet_service.infrastructure.entrypoint.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wallet_service.application.model.WalletDTO;
import com.github.wallet_service.application.port.WalletApplication;
import com.github.wallet_service.infrastructure.entrypoint.controller.json.WalletBalanceResponse;
import com.github.wallet_service.infrastructure.entrypoint.controller.json.WalletOrderRequest;
import com.github.wallet_service.infrastructure.entrypoint.controller.json.WalletRequest;
import com.github.wallet_service.infrastructure.entrypoint.controller.json.WalletResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletApplication walletApplication;

    @PostMapping(consumes="application/json")
    public ResponseEntity<WalletResponse> createWallet(@Valid @RequestBody WalletRequest walletRequest,
                                                              @RequestHeader(value = "clientId", required = true) Long clientId) {
        
        final var createdWallet = walletApplication.create(walletRequest.toModel(clientId));
        return ResponseEntity.ok(createdWallet.toResponse());
    }

    @PostMapping(value = "/{id}/order", consumes="application/json")
    public ResponseEntity<Void> executeOrder(@Valid @RequestBody WalletOrderRequest walletOrderRequest,
                                                    @PathVariable(value = "id", required=true) Long walletId,
                                                    @RequestHeader(value = "clientId", required = true) Long clientId,
                                                    @RequestHeader(value = "walletTargetId", required = false) Long targetId) {
        final var wallet = WalletDTO.builder()
            .userId(clientId)
            .id(walletId)
            .targetId(targetId)
            .build();

        walletApplication.executeOrder(wallet, walletOrderRequest.toModel());
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/{id}/balance", consumes="application/json")
    public ResponseEntity<WalletBalanceResponse> retrieveWalletBalance(@RequestHeader(value = "clientId", required = true) Long clientId,
                                                                       @PathVariable(value = "id", required=true) Long walletId,
                                                                       @RequestParam(value = "date", required = false) LocalDate date) {
        final var wallet = WalletDTO.builder()
            .userId(clientId)
            .id(walletId)
            .build();

        final var retrievedBalance = walletApplication.retrieveBalance(wallet, date);
        
        return ResponseEntity.ok(new WalletBalanceResponse(retrievedBalance));
    }
    
}
