package com.api.wallets;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.api.common.BaseService;
import com.api.wallets.dto.WalletCreateDTO;

@Service
public class WalletService extends BaseService<WalletModel>{

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository repository){
        super(repository);
        this.walletRepository = repository;
    }

    /**
     * Create a new wallet using a data as WalletCreateQuestDTO
     * 
     * @param walletDTO
     * @return WalletResponseDTO
     */
    public WalletModel create(WalletCreateDTO walletDTO){ 
        WalletModel wallet = new WalletModel(walletDTO);
        return this.repository.save(wallet);
    }

    /**
     *  Makes a deposit to the specified wallet.
     * 
     * @param id
     * @param amount
     * @return
     */
    public WalletModel deposit(Long id, BigDecimal amount){ 
        if (amount.compareTo(BigDecimal.valueOf(0.1)) < 0) {
            throw new IllegalArgumentException("O valor deve ser maior que 0");
        } 

        var wallet = this.findOrFail(id);
        BigDecimal newBalance = wallet.getBalance().add(amount);
        
        wallet.setBalance(newBalance);
        return repository.save(wallet);
    }

    /**
     * Makes a withdrawal to the specified wallet
     * 
     * @param id
     * @param amount
     * @return
     */
    public WalletModel withDraw(Long id, BigDecimal amount){
        if ((amount.compareTo(BigDecimal.valueOf(0.1)) < 0)) {
            throw new IllegalArgumentException("O valor deve ser maior que 0");
        } 

        var wallet = this.findOrFail(id);
        BigDecimal newBalance = wallet.getBalance().subtract(amount);

        if((newBalance.compareTo(BigDecimal.valueOf(0)) < 0)){
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        wallet.setBalance(newBalance);
        return repository.save(wallet);
    }

    public WalletModel findPrimaryByUserId(Long userId){
        return this.walletRepository.findFirstByUserIdAndIsPrimaryTrue(userId);
    }
}