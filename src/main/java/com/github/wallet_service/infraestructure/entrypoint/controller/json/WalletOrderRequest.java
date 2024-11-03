package com.github.wallet_service.infraestructure.entrypoint.controller.json;

import java.math.BigDecimal;

import com.github.wallet_service.application.enums.OrderTypeEnum;

import jakarta.validation.constraints.NotNull;

public record WalletOrderRequest(
    @NotNull
    OrderTypeEnum type,
    @NotNull
    BigDecimal value
) {}
