package com.github.wallet_service.infrastructure.entrypoint.controller.json;

import com.github.wallet_service.application.model.WalletDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WalletRequest(
    @NotNull
    @Size(min = 3, max= 150)
    String name
) {

    public WalletDTO toModel(Long clientId) {
        return WalletDTO.builder()
            .userId(clientId)
            .name(this.name)
            .build();
    }
}
