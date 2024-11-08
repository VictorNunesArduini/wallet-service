package com.github.wallet_service.infrastructure.entrypoint.controller.json;

import java.math.BigDecimal;

import com.github.wallet_service.application.model.OrderDTO;

import jakarta.validation.constraints.NotNull;

public record WalletOrderRequest(
    @NotNull
    OrderTypeEnum type,
    @NotNull
    BigDecimal value
) {

    public OrderDTO toModel() {

        return OrderDTO.builder()
            .value(this.value)
            .type(com.github.wallet_service.application.model.OrderTypeEnum.valueOf(this.type.name()))
            .build();
    }
}
