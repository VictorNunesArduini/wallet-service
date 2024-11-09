package com.github.wallet_service.application.usecases;

import org.springframework.stereotype.Service;

import com.github.wallet_service.application.model.UserDTO;
import com.github.wallet_service.application.port.UserApplication;
import com.github.wallet_service.infrastructure.port.UserRepository;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Singleton
@Service
public class UserUseCaseImpl implements UserApplication {

    private final UserRepository userRepository;

    @Override
    public UserDTO create(UserDTO user) {
        final var userEntity = userRepository.save(user.toEntity());

        return UserDTO.from(userEntity);
    }
    
}
