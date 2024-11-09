package com.github.wallet_service.application.usecases;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.wallet_service.application.model.UserDTO;
import com.github.wallet_service.infrastructure.outbound.repository.entity.UserEntity;
import com.github.wallet_service.infrastructure.port.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCaseImpl userApplication;

    @Test
    public void givenUserInformation_whenCreateUser_thenReturnCreatedUser() {
       final var createdAt =  LocalDateTime.now();
       final var updatedAt =  LocalDateTime.now();

        UserEntity mockedUserEntity = new UserEntity(1L, "Victor Arduini", createdAt, updatedAt);

        when(userRepository.save(any(UserEntity.class))).thenReturn(mockedUserEntity);

        final var userDTOMock = buildUserDTOMock(createdAt, updatedAt);

        UserDTO createdUser = userApplication.create(userDTOMock);

        assertEquals(userDTOMock.getId(), createdUser.getId());
        assertEquals(userDTOMock.getName(), createdUser.getName());
        assertEquals(userDTOMock.getCreatedAt(), createdUser.getCreatedAt());
        assertEquals(userDTOMock.getUpdatedAt(), createdUser.getUpdatedAt());
    }

    private UserDTO buildUserDTOMock(LocalDateTime createdAt, LocalDateTime updatedAt) {

        return UserDTO.builder()
            .id(1L)
            .name("Victor Arduini")
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }
}
