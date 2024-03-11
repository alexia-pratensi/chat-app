package com.alexia.backend.transformers;

import com.alexia.backend.models.Message;
import org.springframework.stereotype.Component;
import com.alexia.backend.DTO.MessageDTO;
import com.alexia.backend.DTO.UserDTO;
import com.alexia.backend.models.User;

/**
 * This class is used to transform Message entities to DTOs and vice versa.
 */
@Component
public class MessageTransformer {

    public MessageDTO transformEntityToDTO(Message message) {
        return entityToDTO(message);
    }

    public MessageDTO entityToDTO(Message entity) {
        MessageDTO dto = new MessageDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());

        UserDTO userSourceDTO = new UserDTO();
        userSourceDTO.setId(entity.getSourceUser().getId());
        userSourceDTO.setUsername(entity.getSourceUser().getUsername());
        userSourceDTO.setPassword(entity.getSourceUser().getPassword());
        userSourceDTO.setRole(entity.getSourceUser().getRole());

        dto.setSourceUser(userSourceDTO);

        UserDTO userTargetDTO = new UserDTO();
        userTargetDTO.setId(entity.getTargetUser().getId());
        userTargetDTO.setUsername(entity.getTargetUser().getUsername());
        userTargetDTO.setPassword(entity.getTargetUser().getPassword());
        userTargetDTO.setRole(entity.getTargetUser().getRole());

        dto.setTargetUser(userTargetDTO);
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public Message transformDTOToEntity(MessageDTO message) {
        return DTOToEntity(message);
    }

    public Message DTOToEntity(MessageDTO dto) {
        Message entity = new Message();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());

        User userSource = new User();
        userSource.setId(dto.getSourceUser().getId());
        userSource.setUsername(dto.getSourceUser().getUsername());
        userSource.setPassword(dto.getSourceUser().getPassword());
        userSource.setRole(dto.getSourceUser().getRole());

        entity.setSourceUser(userSource);

        User userTarget = new User();
        userTarget.setId(dto.getTargetUser().getId());
        userTarget.setUsername(dto.getTargetUser().getUsername());
        userTarget.setPassword(dto.getTargetUser().getPassword());
        userTarget.setRole(dto.getTargetUser().getRole());

        entity.setTargetUser(userTarget);
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;
    }

}