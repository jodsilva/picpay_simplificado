package com.api.users.dto;

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
}