package com.api.wallets;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.wallets.dto.WalletResponseDTO;

@RestController
@RequestMapping("/api/wallets")
public class WalletController{

    private final WalletService service;

    public WalletController(WalletService walletService){
        this.service = walletService;
    }

    /**
     * Get a wallet
     * 
     * @param id ID of the wallet retrieve
     * @return Wallet data as WalletResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<WalletResponseDTO> get(@PathVariable Long id){
        return ResponseEntity.ok(this.service.get(id));
    }

    /**
     * Get the first wallet by user id
     * 
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<WalletResponseDTO> getByUser(@PathVariable Long userId){
        return ResponseEntity.ok(this.service.findByUserId(userId));
    }
}