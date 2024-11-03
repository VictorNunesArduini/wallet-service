package com.github.wallet_service.infraestructure.entrypoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation. RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.wallet_service.infraestructure.entrypoint.controller.json.WalletBalanceResponse;
import com.github.wallet_service.infraestructure.entrypoint.controller.json.WalletOrderRequest;
import com.github.wallet_service.infraestructure.entrypoint.controller.json.WalletRequest;
import com.github.wallet_service.infraestructure.entrypoint.controller.json.WalletResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<WalletResponse> createWallet(@Valid @RequestBody WalletRequest walletRequest,
                                                              @RequestHeader(value = "clientId", required = true) Long clientId) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/order")
    public ResponseEntity<Void> executeOrder(@Valid @RequestBody WalletOrderRequest walletOrderRequest,
                                                    @RequestHeader(value = "clientId", required = true) Long clientId) {
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/balance")
    public ResponseEntity<WalletBalanceResponse> retrieveWalletBalance(@RequestHeader(value = "clientId", required = true) Long clientId,
                                                                       @PathVariable(value = "id", required=true) Long walletId) {
        return ResponseEntity.ok(null);
    }
    
}
