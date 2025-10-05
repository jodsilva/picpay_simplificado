package com.api.wallets.dto;

import java.math.BigDecimal;

import com.api.wallets.WalletModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseDTO {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private String currency;
    private Boolean isPrimary;

    public WalletResponseDTO(WalletModel wallet){
        this.id = wallet.getId();
        this.userId = wallet.getUser().getId();
        this.balance = wallet.getBalance();
        this.currency = wallet.getEnumCurrency().name();
        this.isPrimary = wallet.getIsPrimary();
    }
}