package com.github.wallet_service.infrastructure.entrypoint.controller.json;

import java.math.BigDecimal;

public record WalletBalanceResponse(
    BigDecimal balance
) {}
