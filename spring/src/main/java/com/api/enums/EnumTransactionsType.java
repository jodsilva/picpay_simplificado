package com.api.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumTransactionsType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER("Transfer");

    private final String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
