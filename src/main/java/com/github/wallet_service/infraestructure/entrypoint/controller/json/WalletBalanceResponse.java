package com.github.wallet_service.infraestructure.entrypoint.controller.json;

import java.math.BigDecimal;

public record WalletBalanceResponse(
    BigDecimal balance
) {}
