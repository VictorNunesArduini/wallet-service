package com.github.wallet_service.infraestructure.entrypoint.controller.json;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotNull
    @Size(min = 3, max= 150)
    String name
) {}
