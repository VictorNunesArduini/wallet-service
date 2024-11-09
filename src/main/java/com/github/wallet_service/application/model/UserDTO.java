package com.github.wallet_service.application.model;

import java.time.LocalDateTime;

import com.github.wallet_service.infrastructure.entrypoint.controller.json.UserResponse;
import com.github.wallet_service.infrastructure.outbound.repository.entity.UserEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class UserDTO {
    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserEntity toEntity() {

        return UserEntity.builder()
            .name(this.name)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public UserResponse toResponse() {
        return new UserResponse(this.id, this.name, this.createdAt, this.updatedAt);
    }

    public static UserDTO from(UserEntity entity) {
        return UserDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}