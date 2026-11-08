package com.auth.dtos.response;

import com.auth.enums.RoleEnum;

public record AdminResponse(
 Long id,
 String nome,
 String email,
 RoleEnum role
){
}
