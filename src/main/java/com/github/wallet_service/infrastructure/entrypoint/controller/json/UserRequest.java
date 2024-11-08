package com.github.wallet_service.infrastructure.entrypoint.controller.json;

import com.github.wallet_service.application.model.UserDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotNull
    @Size(min = 3, max= 150)
    String name
) {

    public UserDTO toModel() {

        return UserDTO.builder()
            .name(this.name)
            .build();
    }
}
