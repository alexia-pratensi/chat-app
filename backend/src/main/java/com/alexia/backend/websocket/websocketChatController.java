package com.alexia.backend.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.alexia.backend.DTO.MessageDTO;
import com.alexia.backend.servicesImpl.MessageServiceImpl;

/**
 * Controller class for WebSocket chat functionality.
 */
@Controller
@CrossOrigin(origins = "*")
public class WebsocketChatController {

    @Autowired
    private MessageServiceImpl messageService;

    /**
     * Sends a message to a specific user.
     * 
     * @param userId  the ID of the user
     * @param message the message to be sent
     * @return the saved message DTO
     */
    @MessageMapping("/chat.sendMessage/{userId}")
    @SendTo("/chat/user/{userId}")
    public MessageDTO sendMessageToUser(@DestinationVariable Long userId, @Payload MessageDTO message) {
        MessageDTO savedMessage = messageService.saveMessage(message, userId);
        return savedMessage;
    }

}
