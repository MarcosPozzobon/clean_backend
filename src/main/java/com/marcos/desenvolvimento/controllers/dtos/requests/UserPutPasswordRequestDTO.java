package com.marcos.desenvolvimento.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserPutPasswordRequestDTO(
        @NotNull
        Long id,

        @NotBlank
        String password
) {
}
