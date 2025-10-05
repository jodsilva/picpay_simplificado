package com.api.wallets;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.api.enums.EnumCurrency;
import com.api.wallets.dto.WalletCreateRequestDTO;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "users_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Double balance;

    @Column(name = "enum_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumCurrency enumCurrency;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    public WalletModel(WalletCreateRequestDTO walletDTO){
        this.userId = walletDTO.getUserId();
        this.balance = walletDTO.getBalance();
        this.enumCurrency = EnumCurrency.valueOf(walletDTO.getCurrency());
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