package com.alexia.backend.transformers;

import org.springframework.stereotype.Component;
import com.alexia.backend.DTO.UserDTO;
import com.alexia.backend.models.User;

/**
 * This class is used to transform User entities to DTOs and vice versa.
 */
@Component
public class UserTransformer {

    public UserDTO transformEntityToDTO(User user) {
        return entityToDTO(user);
    }

    public UserDTO entityToDTO(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        return dto;
    }

    public User transformDTOToEntity(UserDTO user) {
        return DTOToEntity(user);
    }

    public User DTOToEntity(UserDTO dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }

}
