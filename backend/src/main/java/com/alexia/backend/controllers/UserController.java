package com.alexia.backend.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alexia.backend.DTO.LoginRequest;
import com.alexia.backend.DTO.UserDTO;
import com.alexia.backend.servicesImpl.UserServiceImpl;

/**
 * Controller class for managing user-related endpoints.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Endpoint for user login.
     * 
     * @param loginRequest the login request containing user credentials
     * @return ResponseEntity containing user details if login is successful, or
     *         error message if failed
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            UserDTO userDTO = userService.loginUser(loginRequest);
            if (userDTO != null) {
                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while logging in.");
        }
    }

    /**
     * Endpoint to retrieve user details by ID.
     * 
     * @param id the ID of the user to retrieve
     * @return ResponseEntity containing user details if found, or error message if
     *         user is not found or ID is invalid
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("User id is null or invalid");
            }
            UserDTO user = userService.findById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching user details.");
        }
    }

}
