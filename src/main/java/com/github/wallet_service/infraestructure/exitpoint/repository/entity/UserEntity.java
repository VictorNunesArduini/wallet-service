package com.github.wallet_service.infraestructure.exitpoint.repository.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public record UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    @OneToMany
    @JoinColumn(name = "walletId")
    List<WalletEntity> wallet,
    @Column(length = 150)
    String name,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}