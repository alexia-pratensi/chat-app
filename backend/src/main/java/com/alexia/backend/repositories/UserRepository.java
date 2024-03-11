package com.alexia.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.alexia.backend.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     * 
     * @param username the username of the user to find
     * @return the found user, or null if no user with the given username exists
     */
    public User findByUsername(String username);

    /**
     * Finds a random user with role "AGENT".
     * 
     * @return a random user with role "AGENT"
     */
    @Query(value = "SELECT * FROM users WHERE role = 'AGENT' ORDER BY RAND() LIMIT 1", nativeQuery = true)
    public User findRandomAgent();

}
