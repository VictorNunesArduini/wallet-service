package com.github.wallet_service.application.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.wallet_service.infrastructure.entrypoint.controller.json.WalletResponse;
import com.github.wallet_service.infrastructure.outbound.repository.entity.UserEntity;
import com.github.wallet_service.infrastructure.outbound.repository.entity.WalletEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class WalletDTO {

    private final Long userId;
    private final Long targetId;

    private final Long id;
    private final String name;
    private final BigDecimal balance;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public WalletEntity toEntity(UserEntity user) {

        return WalletEntity.builder()
            .name(this.name)
            .balance(BigDecimal.ZERO)
            .user(user)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public WalletEntity toEntity(UserEntity user, BigDecimal newBalance) {

        return WalletEntity.builder()
            .id(this.id)
            .name(this.name)
            .user(user)
            .balance(newBalance)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public WalletResponse toResponse() {
        return new WalletResponse(this.id, this.name, this.balance, this.createdAt, this.updatedAt);
    }

    public static WalletDTO from(WalletEntity walletEntity) {

        return WalletDTO.builder()
            .id(walletEntity.getId())
            .name(walletEntity.getName())
            .balance(walletEntity.getBalance())
            .createdAt(walletEntity.getCreatedAt())
            .updatedAt(walletEntity.getUpdatedAt())
            .build();
    }
}