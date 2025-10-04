package com.api.users.dto;

import java.util.Optional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDTO {
    @NotBlank(message = "O nome completo é obrigatório")
    private Optional<String> fullName = Optional.empty();

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email informado é inválido")
    private Optional<String> email = Optional.empty();

    @NotBlank(message = "O Cpf/Cnpj é obrigatório")
    @Size(min = 8, message = "O CPF/CNPJ deve ter no mínimo 8 caracteres")
    private Optional<String> taxId = Optional.empty();

    @NotBlank(message = "Tipo de usuário é obrigatório")
    private Optional<String> type = Optional.empty();
}