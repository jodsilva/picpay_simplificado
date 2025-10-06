package com.api.users.dto;

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
public class UserCreateRequestDTO {
    @NotBlank(message = "O nome completo é obrigatório")
    private String fullName;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email informado é inválido")
    @CheckUnique(entityClass = UserModel.class, fieldName = "email", message = "Email já cadastrado")
    private String email;

    @NotBlank(message = "O Cpf/Cnpj é obrigatório")
    @Size(min = 8, message = "O CPF/CNPJ deve ter no mínimo 8 caracteres")
    @CheckUnique(entityClass = UserModel.class, fieldName = "email", message = "CPF / CNPJ já cadastrado")
    private String taxId;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
    private String password;

    @CheckEnum(enumClass = EnumUsersType.class, message = "Tipo de usuário inválido")
    private String type;
}