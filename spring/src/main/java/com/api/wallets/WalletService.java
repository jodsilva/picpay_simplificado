package com.api.wallets;

import org.springframework.stereotype.Service;

import com.api.common.BaseService;
import com.api.wallets.dto.WalletCreateRequestDTO;
import com.api.wallets.dto.WalletResponseDTO;


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
    public WalletResponseDTO create(WalletCreateRequestDTO walletDTO){ 
        WalletModel wallet = new WalletModel(walletDTO);
        this.repository.save(wallet);
        return new WalletResponseDTO(wallet);
    }

    /**
     * Get a wallet by id
     * 
     * @param id
     * @return WalletResponseDTO
     */
    public WalletResponseDTO get(Long id){ 
        return new WalletResponseDTO(this.findOrFail(id));
    }

    /**
     *  Makes a deposit to the specified wallet.
     * 
     * @param id
     * @param amount
     * @return
     */
    public WalletResponseDTO deposit(Long id, Double amount){ 
        if (amount < 1) {
            throw new IllegalArgumentException("O valor deve ser maior que 0");
        } 

        var wallet = this.findOrFail(id);
        Double newBalance = wallet.getBalance() + amount;
        
        wallet.setBalance(newBalance);
        repository.save(wallet);
        return new WalletResponseDTO(wallet);
    }

    /**
     * Makes a withdrawal to the specified wallet
     * 
     * @param id
     * @param amount
     * @return
     */
    public WalletResponseDTO withDraw(Long id, Double amount){
        if (amount < 1) {
            throw new IllegalArgumentException("O valor deve ser maior que 0");
        } 

        var wallet = this.findOrFail(id);
        Double newBalance = wallet.getBalance() - amount;

        if(newBalance < 0){
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        wallet.setBalance(newBalance);
        repository.save(wallet);
        return new WalletResponseDTO(wallet);
    }

    public WalletResponseDTO findByUserId(Long userId){
        var wallet = this.walletRepository.findFirstByUserId(userId);
        return new WalletResponseDTO(wallet);
    }
}