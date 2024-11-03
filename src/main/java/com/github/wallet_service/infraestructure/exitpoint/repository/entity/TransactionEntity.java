package com.github.wallet_service.infraestructure.exitpoint.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.wallet_service.application.enums.OrderTypeEnum;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public record TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    @ManyToOne
    @JoinColumn(name = "walletId")
    WalletEntity wallet,
    @Column(precision = 15, scale = 2)
    BigDecimal balance,
    OrderTypeEnum orderType,
    @Column(precision = 15, scale = 2)
    BigDecimal orderValue,
    LocalDateTime createdAt
) {}
