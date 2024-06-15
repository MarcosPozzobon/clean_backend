package com.marcos.desenvolvimento.controllers.dtos.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String login,
        String email,
        boolean admin,
        boolean active,
        LocalDate createdAt,
        LocalDateTime lastAccess
) {

}
