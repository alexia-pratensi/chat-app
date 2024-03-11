package com.alexia.backend.services;

import com.alexia.backend.DTO.LoginRequest;
import com.alexia.backend.DTO.UserDTO;

public interface UserService {
    public UserDTO loginUser(LoginRequest loginRequest);

    public UserDTO findById(Long id);
}
