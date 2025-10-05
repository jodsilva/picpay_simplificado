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
        var response = new WalletResponseDTO(this.service.findOrFail(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Get the first wallet by user id
     * 
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<WalletResponseDTO> getByUser(@PathVariable Long userId){
        var response = new WalletResponseDTO(this.service.findPrimaryByUserId(userId));
        return ResponseEntity.ok(response);
    }
}