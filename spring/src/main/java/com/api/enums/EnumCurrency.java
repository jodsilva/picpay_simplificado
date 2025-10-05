package com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCurrency {

    USD("Dólar Americano", "$"),
    BRL("Real Brasileiro", "R$");

    private final String displayName;
    private final String symbol;

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }
}
