package com.api.transactions.dto;

import java.math.BigDecimal;

import com.api.transactions.TransactionModel;
import com.api.users.UserModel;
import com.api.wallets.WalletModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private Long id;
    private String payerName;
    private String payeeName;
    private String type;
    private String currency;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount; 
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balanceAfterTransaction;

    public TransactionResponseDTO(
        TransactionModel transaction,
        WalletModel payerWallet,
        UserModel payee
    ){
        this.id = transaction.getId();
        this.payerName = payerWallet.getUser().getFullName();
        this.payeeName = payee.getFullName();
        this.amount = transaction.getAmount();
        this.type = transaction.getEnumTransactionsType().name();
        this.balanceAfterTransaction = payerWallet.getBalance();
        this.currency = transaction.getEnumCurrency().name();
    }
    
    public TransactionResponseDTO(
        TransactionModel transaction,
        WalletModel payeeWallet
    ){
        this.id = transaction.getId();
        this.payerName = "";
        this.payeeName = payeeWallet.getUser().getFullName();
        this.amount = transaction.getAmount();
        this.type = transaction.getEnumTransactionsType().name();
        this.balanceAfterTransaction = payeeWallet.getBalance();
        this.currency = transaction.getEnumCurrency().name();
    }
}