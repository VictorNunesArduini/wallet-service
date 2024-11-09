package com.github.wallet_service.infrastructure.port;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.wallet_service.infrastructure.outbound.repository.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    static final String QUERY_HISTORICAL_BALANCE = 
        """
            SELECT te.balance
            FROM TransactionEntity te
            WHERE te.wallet.id = :walletId AND cast(te.createdAt AS DATE) = :date
            ORDER BY te.createdAt DESC
            LIMIT 1
        """;
    
    @Query(QUERY_HISTORICAL_BALANCE)
    Optional<BigDecimal> findLastBalanceOnThisDate(@Param("walletId") Long walletId, @Param("date") LocalDate date);
}
