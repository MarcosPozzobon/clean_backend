package com.marcos.desenvolvimento.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank String login, @NotBlank String password) {
}
