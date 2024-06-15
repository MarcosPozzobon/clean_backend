package com.marcos.desenvolvimento.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record UserPutRequestDTO(

        Long id,

        @NotBlank
        String name,

        @NotBlank
        String login,

        @NotBlank
        String email,

        boolean admin,

        boolean active
) {
}
