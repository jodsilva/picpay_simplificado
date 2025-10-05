package com.api.transactions;

import org.springframework.stereotype.Service;

import com.api.common.BaseService;
import com.api.enums.EnumTransactionsType;
import com.api.transactions.dto.TransactionCreateDTO;
import com.api.transactions.dto.TransactionDepositRequestDTO;
import com.api.transactions.dto.TransactionTransferRequestDTO;
import com.api.wallets.WalletService;

import jakarta.transaction.Transactional;


@Service
public class TransactionService extends BaseService<TransactionModel>{

    private final WalletService walletService;

    public TransactionService(TransactionRepository repository, WalletService walletService){
        super(repository);
        this.walletService = walletService;
    }

    /**
     * Create a new transaction using a data as TransactionCreateRequestDTO
     * 
     * @param TransactionCreateRequestDTO
     * @return TransactionResponseDTO
     */
    @Transactional
    public TransactionModel create(TransactionCreateDTO transactionDTO){ 
        TransactionModel transaction = new TransactionModel(transactionDTO);
        return this.repository.save(transaction);
    }

    /**
     * Make a transfer between users 
     * 
     * @param id
     * @return TransactionResponseDTO
     */
    public TransactionModel transfer(TransactionTransferRequestDTO transferDTO){ 
        var payerWallet = walletService.findOrFail(transferDTO.getPayerWalletId());
        var payeeWallet = walletService.findPrimaryByUserId(transferDTO.getPayee());

        this.walletService.withDraw(payerWallet.getId(), transferDTO.getAmount());
        this.walletService.deposit(payeeWallet.getId(), transferDTO.getAmount());
        
        var createDTO = new TransactionCreateDTO(
            payerWallet,
            payeeWallet,
            transferDTO.getAmount(),
            EnumTransactionsType.TRANSFER.name(),
            payerWallet.getEnumCurrency().name()
        );

        return this.create(createDTO);
    }

    /**
     * Make a transfer between users 
     * 
     * @param id
     * @return TransactionResponseDTO
     */
    public TransactionModel deposit(TransactionDepositRequestDTO depositDTO){ 
        var payeeWallet = walletService.findOrFail(depositDTO.getPayeeWalletId());

        this.walletService.deposit(depositDTO.getPayeeWalletId(), depositDTO.getAmount());
        
        var createDTO = new TransactionCreateDTO(
            null,
            payeeWallet,
            depositDTO.getAmount(),
            EnumTransactionsType.DEPOSIT.name(),
            payeeWallet.getEnumCurrency().name()
        );

        return this.create(createDTO);
    }
}