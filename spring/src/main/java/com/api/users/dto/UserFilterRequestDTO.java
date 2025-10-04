package com.api.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilterRequestDTO {
    private String fullName;
    private String email;
    private String taxId;
    private String type;
}
