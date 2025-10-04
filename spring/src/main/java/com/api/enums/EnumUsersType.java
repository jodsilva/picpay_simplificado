package com.api.enums;

public enum EnumUsersType {
    MERCHANT("merchant"),
    CUSTOMER("customer");

    private final String value;

    EnumUsersType(String value){
        this.value = value;
    }
}
