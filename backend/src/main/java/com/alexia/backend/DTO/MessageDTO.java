package com.alexia.backend.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
    private Long id;
    private String content;
    private UserDTO sourceUser;
    private UserDTO targetUser;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;
}
