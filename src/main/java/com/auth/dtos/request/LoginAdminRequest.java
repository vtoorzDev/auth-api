package com.auth.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginAdminRequest(
        @Email
        @NotNull
        String email,

        @NotNull
        @Size(min = 8)
        String senha
) {
}
