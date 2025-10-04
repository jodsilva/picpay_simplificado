package com.api.users.dto;

import com.api.enums.EnumUsersType;
import com.api.users.UserModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String taxId;
    private String type;

    public UserResponseDTO(UserModel user){
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.taxId = user.getTaxId();
        this.type = user.getEnumUsersType();
    }

    public UserResponseDTO(String fullName, String email, String taxId, EnumUsersType enumUsersType) {
        this.fullName = fullName;
        this.email = email;
        this.taxId = taxId;
        this.type = enumUsersType.name();
    }
}