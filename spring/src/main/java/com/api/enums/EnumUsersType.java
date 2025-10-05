package com.api.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumUsersType {
    MERCHANT("Merchant"),
    CUSTOMER("Customer");

    private final String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
