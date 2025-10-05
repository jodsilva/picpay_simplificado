package com.api.wallets;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletModel, Long> {

    WalletModel findFirstByUserId(Long userId);
}