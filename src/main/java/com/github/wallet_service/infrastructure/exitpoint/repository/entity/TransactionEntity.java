package com.github.wallet_service.infrastructure.exitpoint.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "payments", name = "transaction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    WalletEntity wallet;
    @Column(precision = 15, scale = 2)
    BigDecimal balance;
    @Column(name = "order_type")
    String orderType;
    @Column(name = "order_value", precision = 15, scale = 2)
    BigDecimal orderValue;
    @Column(name = "created_at")
    LocalDateTime createdAt;
}
