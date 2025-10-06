package com.api.users.dto;

import java.util.Optional;

import com.api.enums.EnumUsersType;
import com.api.users.UserModel;
import com.api.validations.CheckEnum;
import com.api.validations.CheckUnique;

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
    @CheckUnique(entityClass = UserModel.class, fieldName = "email", message = "Email já cadastrado")
    private Optional<String> email = Optional.empty();

    @NotBlank(message = "O Cpf/Cnpj é obrigatório")
    @Size(min = 11, message = "O CPF/CNPJ deve ter no mínimo 8 caracteres")
    @CheckUnique(entityClass = UserModel.class, fieldName = "taxId", message = "CPF / CNPJ já cadastrado")
    private Optional<String> taxId = Optional.empty();

    @CheckEnum(enumClass = EnumUsersType.class, message = "Tipo de usuário inválido")
    private Optional<String> type = Optional.empty();
}