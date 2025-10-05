package com.api.wallets.dto;

import com.api.wallets.WalletModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseDTO {
    private Long id;
    private Long userId;
    private Double balance;
    private String currency;

    public WalletResponseDTO(WalletModel wallet){
        this.id = wallet.getId();
        this.userId = wallet.getUserId();
        this.balance = wallet.getBalance();
        this.currency = wallet.getEnumCurrency().name();
    }
}