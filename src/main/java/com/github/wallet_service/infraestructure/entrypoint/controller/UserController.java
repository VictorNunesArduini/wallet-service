package com.github.wallet_service.infraestructure.entrypoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wallet_service.infraestructure.entrypoint.controller.json.UserRequest;
import com.github.wallet_service.infraestructure.entrypoint.controller.json.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest,
                                                   @RequestHeader(value = "clientId", required = true) Long clientId) {
        return ResponseEntity.ok(null);
    }
    
}
