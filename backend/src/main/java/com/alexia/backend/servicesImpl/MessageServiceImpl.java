package com.alexia.backend.servicesImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alexia.backend.DTO.MessageDTO;
import com.alexia.backend.DTO.UserDTO;
import com.alexia.backend.models.Message;
import com.alexia.backend.models.User;
import com.alexia.backend.repositories.MessageRepository;
import com.alexia.backend.repositories.UserRepository;
import com.alexia.backend.services.MessageService;
import com.alexia.backend.transformers.MessageTransformer;
import com.alexia.backend.transformers.UserTransformer;

/**
 * Service implementation class for managing message-related operations.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageTransformer messageTransformer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransformer userTransformer;

    /**
     * Saves a message and assigns a target user based on the role of the source
     * user.
     * 
     * @param messageDTO the message to be saved
     * @param userId     the ID of the source user
     * @return MessageDTO representing the saved message
     * @throws IllegalArgumentException if source user or target user is not found
     * @throws RuntimeException         if an error occurs during message saving
     */
    @Override
    public MessageDTO saveMessage(MessageDTO messageDTO, Long userId) {
        try {
            User sourceUser = userRepository.findById(messageDTO.getSourceUser().getId()).orElse(null);
            if (sourceUser == null) {
                throw new IllegalArgumentException("Source user not found");
            }
            User targetUser;
            if (sourceUser.getRole().equals("USER")) {
                targetUser = userRepository.findRandomAgent();
            } else if (sourceUser.getRole().equals("AGENT")) {
                targetUser = userRepository.findById(userId).orElse(null);
            } else {
                throw new RuntimeException("Invalid user role");
            }
            if (targetUser == null) {
                throw new IllegalArgumentException("Target user not found");
            }
            messageDTO.setTargetUser(userTransformer.transformEntityToDTO(targetUser));
            Message message = messageTransformer.transformDTOToEntity(messageDTO);
            message = messageRepository.save(message);
            return messageTransformer.entityToDTO(message);
        } catch (Exception ex) {
            throw new RuntimeException("Error saving message: " + ex.getMessage());
        }
    }

    /**
     * Retrieves the message history for a specific user.
     * 
     * @param userId the ID of the user
     * @return List of MessageDTO representing the message history
     * @throws IllegalArgumentException if user ID is invalid or user not found
     */
    @Override
    public List<MessageDTO> getMessagesByUserId(Long userId) {
        try {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException("Invalid user ID");
            }
            List<Message> sentMessages = messageRepository.findBySourceUserId(userId);
            Set<Long> targetUserIds = new HashSet<>();
            for (Message message : sentMessages) {
                targetUserIds.add(message.getTargetUser().getId());
            }
            List<Message> conversationMessages = new ArrayList<>();
            for (Long targetUserId : targetUserIds) {
                conversationMessages.addAll(messageRepository.findConversationBetweenUsers(userId, targetUserId));
                conversationMessages.sort(Comparator.comparing(Message::getCreatedAt));
            }
            return conversationMessages.stream()
                    .map(messageTransformer::entityToDTO)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error retrieving message history: " + ex.getMessage());
        }
    }

    /**
     * Retrieves the list of users who sent messages to a specific agent.
     * 
     * @param agentId the ID of the agent
     * @return List of UserDTO representing users who sent messages to the agent
     * @throws IllegalArgumentException if agent ID is invalid or agent not found
     */
    @Override
    public List<UserDTO> getUsersWhoSentMessagesToAgent(Long agentId) {
        try {
            if (agentId == null || agentId <= 0) {
                throw new IllegalArgumentException("Invalid agent ID");
            }
            List<User> customerSenders = messageRepository.findUsersWhoSentMessagesToAgent(agentId);
            return customerSenders.stream()
                    .map(userTransformer::transformEntityToDTO)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error retrieving users who sent messages to agent: " + ex.getMessage());
        }
    }
}
