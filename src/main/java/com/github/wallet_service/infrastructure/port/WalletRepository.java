package com.github.wallet_service.infrastructure.port;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.wallet_service.infrastructure.outbound.repository.entity.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

    static final String QUERY_FIND = 
        """
            SELECT we
            FROM WalletEntity we
            WHERE we.id = :id AND we.user.id = :userId
        """;

    @Query(QUERY_FIND)
    Optional<WalletEntity> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
    
}
