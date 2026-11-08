package com.auth.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotNull(message = "O campo de nome não pode ser nulo")
        @Size(min = 3, message = "O campo de nome deve ter mais de 3 letras")
        String nome,

        @NotNull(message = "O campo de senha não pode ser nulo")
        @Size(min = 8, message = "O campo de senha deve conter no minimo 8 caracteres")
        String senha,

        @NotNull(message = "O campo de email não pode ser nulo")
        @Email(message = "O campo deve ter um email válido")
        String email
) {}