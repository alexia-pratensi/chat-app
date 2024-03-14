package com.alexia.backend.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alexia.backend.DTO.LoginRequest;
import com.alexia.backend.DTO.UserDTO;
import com.alexia.backend.models.User;
import com.alexia.backend.repositories.UserRepository;
import com.alexia.backend.services.UserService;
import com.alexia.backend.transformers.UserTransformer;

/**
 * Service implementation class for managing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransformer userTransformer;

    /**
     * Attempts to log in a user with the provided credentials.
     * 
     * @param loginRequest the login request containing user credentials
     * @return UserDTO representing the logged-in user if successful, null otherwise
     */
    @Override
    public UserDTO loginUser(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByUsername(loginRequest.getUsername());
            // Check if user exists and password matches
            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                System.out.println("User found: " + user.getUsername());
                return userTransformer.transformEntityToDTO(user);
            }
        } catch (Exception e) {
            System.err.println("An error occurred during login: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id the ID of the user to retrieve
     * @return UserDTO representing the retrieved user
     * @throws IllegalArgumentException if the user ID is null or invalid
     */
    @Override
    public UserDTO findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User id is null or invalid");
        }
        // Retrieve user from repository and transform to DTO
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return userTransformer.entityToDTO(user);
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }

}
