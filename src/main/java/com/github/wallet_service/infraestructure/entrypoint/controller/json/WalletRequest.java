package com.github.wallet_service.infraestructure.entrypoint.controller.json;

import jakarta.validation.constraints.*;

public record WalletRequest(
    @NotNull
    @Size(min = 3, max= 150)
    String name
) {}
