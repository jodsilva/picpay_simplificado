package com.api.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDTO {
    @Size(min = 3, message = "Nome completo deve ter no mínimo 3 caracteres")
    private String fullName;

    @Email(message = "Email informado é inválido")
    private String email;

    @Size(min = 8, message = "O CPF/CNPJ deve ter no mínimo 8 caracteres")
    private String taxId;
}