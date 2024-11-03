package com.github.wallet_service.application.boundary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.wallet_service.infraestructure.exitpoint.repository.entity.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    
}
