package com.github.wallet_service.infraestructure.entrypoint.controller.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletResponse(
    Long id,
    String name,
    BigDecimal balance,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
