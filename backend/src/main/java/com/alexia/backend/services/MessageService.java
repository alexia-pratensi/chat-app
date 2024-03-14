package com.alexia.backend.services;

import java.util.List;
import com.alexia.backend.DTO.MessageDTO;
import com.alexia.backend.DTO.UserDTO;

public interface MessageService {

    public List<UserDTO> getUsersWhoSentMessagesToAgent(Long agentId);

    public MessageDTO saveMessage(MessageDTO messageDTO, Long userId);

    public List<MessageDTO> getMessagesByUserId(Long userId);

}
