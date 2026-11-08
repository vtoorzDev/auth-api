package com.auth.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginUsuarioRequest(
        @NotNull
        @Email
         String email,

        @NotNull
        @Size(min = 8)
         String senha
) {}


