package com.alexia.backend.DTO;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String role;
}
