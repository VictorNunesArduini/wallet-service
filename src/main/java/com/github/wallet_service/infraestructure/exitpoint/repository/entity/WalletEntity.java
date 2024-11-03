package com.github.wallet_service.infraestructure.exitpoint.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wallet")
public record WalletEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    @Column(length = 150)
    String name,
    @Column(precision = 15, scale = 2)
    BigDecimal balance,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}