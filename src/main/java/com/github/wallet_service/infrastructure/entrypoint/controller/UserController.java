package com.github.wallet_service.infrastructure.entrypoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wallet_service.application.port.UserApplication;
import com.github.wallet_service.infrastructure.entrypoint.controller.json.UserRequest;
import com.github.wallet_service.infrastructure.entrypoint.controller.json.UserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserApplication userApplication;

    @PostMapping(consumes="application/json")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody(required=true) UserRequest userRequest) {

        final var createdUser = userApplication.create(userRequest.toModel());
        return ResponseEntity.ok(createdUser.toResponse());
    }
    
}
