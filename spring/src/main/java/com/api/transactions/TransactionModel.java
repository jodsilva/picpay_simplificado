package com.api.transactions;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.api.enums.EnumCurrency;
import com.api.enums.EnumTransactionsType;
import com.api.transactions.dto.TransactionCreateDTO;
import com.api.wallets.WalletModel;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_wallets_id")
    private WalletModel payerWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payee_wallets_id", nullable = false)
    private WalletModel payeeWallet;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "enum_transactions_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTransactionsType enumTransactionsType;

    @Column(name = "enum_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumCurrency enumCurrency;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    public TransactionModel(TransactionCreateDTO transactionDTO){
        this.payerWallet = transactionDTO.getPayerWallet();
        this.payeeWallet = transactionDTO.getPayeeWallet();
        this.amount = transactionDTO.getAmount();
        this.enumTransactionsType = EnumTransactionsType.valueOf(transactionDTO.getType());
        this.enumCurrency = EnumCurrency.valueOf(transactionDTO.getCurrency());
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}