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

   @Override
    public String toString() {
        return name() + " (" + symbol + ")";
    }
}
