package com.alexia.backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alexia.backend.DTO.MessageDTO;
import com.alexia.backend.DTO.UserDTO;
import com.alexia.backend.servicesImpl.MessageServiceImpl;

/**
 * Controller class for managing message-related endpoints.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageServiceImpl messageService;

    /**
     * Retrieves the list of users who sent messages to a specific agent.
     * 
     * @param agentId the ID of the agent
     * @return List of UserDTOs representing users who sent messages to the agent
     */
    @GetMapping("/{agentId}")
    public ResponseEntity<?> getUsersWhoSentMessagesToAgent(@PathVariable Long agentId) {
        try {
            List<UserDTO> users = messageService.getUsersWhoSentMessagesToAgent(agentId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving users who sent messages to the agent.");
        }
    }

    /**
     * Retrieves the message history for a specific user.
     * 
     * @param userId the ID of the user
     * @return ResponseEntity containing the message history as a List of
     *         MessageDTOs
     */
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getMessageHistory(@PathVariable Long userId) {
        try {
            List<MessageDTO> messageHistory = messageService.getMessagesByUserId(userId);
            return ResponseEntity.ok(messageHistory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the message history for the user.");
        }
    }

}
