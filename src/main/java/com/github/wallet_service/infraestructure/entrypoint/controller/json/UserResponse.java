package com.github.wallet_service.infraestructure.entrypoint.controller.json;

import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String name,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
