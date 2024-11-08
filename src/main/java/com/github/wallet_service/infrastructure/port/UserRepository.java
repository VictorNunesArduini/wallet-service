package com.github.wallet_service.infrastructure.port;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.wallet_service.infrastructure.outbound.repository.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}