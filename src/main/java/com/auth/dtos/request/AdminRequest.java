package com.auth.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminRequest(
  @NotNull(message = "O campo do nome não pode ser nulo")
  @Size(min = 3, message = "O campo do nome precisa ter no minimo 3 digitos")
  String nome,

  @NotNull(message = "O campo da senha não pode ser nulo")
  @Size(min = 8, message = "A senha deve conter no minimo 8 caracteres")
  String senha,

  @NotNull(message = "O campo de email não pode ser nulo")
  @Email(message = "O campo de email deve conter um email valido")
  String email
){}
