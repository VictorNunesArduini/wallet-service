package com.github.wallet_service.application.port;

import com.github.wallet_service.application.model.UserDTO;

public interface UserApplication {

    UserDTO create(UserDTO user);
    
}
