package com.api.wallets;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.api.enums.EnumCurrency;
import com.api.users.UserModel;
import com.api.wallets.dto.WalletCreateDTO;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "enum_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumCurrency enumCurrency;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    public WalletModel(WalletCreateDTO walletDTO){
        this.user = walletDTO.getUser();
        this.balance = walletDTO.getBalance();
        this.enumCurrency = EnumCurrency.valueOf(walletDTO.getCurrency());
        this.isPrimary = walletDTO.getIsPrimary();
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