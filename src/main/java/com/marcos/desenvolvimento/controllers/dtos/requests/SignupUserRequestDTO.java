package com.marcos.desenvolvimento.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record SignupUserRequestDTO(

        @NotBlank
        String name,


        @NotBlank
        String login,

        @NotBlank
        String password,

        @NotBlank
        String email
) {

}
