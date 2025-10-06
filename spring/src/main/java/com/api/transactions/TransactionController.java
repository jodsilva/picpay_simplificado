package com.api.transactions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.transactions.dto.TransactionDepositRequestDTO;
import com.api.transactions.dto.TransactionResponseDTO;
import com.api.transactions.dto.TransactionTransferRequestDTO;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController{

    private final TransactionService service;

    public TransactionController(TransactionService transactionService){
        this.service = transactionService;
    }

    /**
     * Make a transfer between users
     * 
     * @param request DTO with containing to transfer money
     * @return Transaction data as TransactionResponseDTO
     */
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transfer(@RequestBody TransactionTransferRequestDTO request){
        var transaction = this.service.transfer(request);
        this.service.notifyTransfer(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponseDTO(
            transaction,
            transaction.getPayerWallet(),
            transaction.getPayeeWallet().getUser()
        ));
    }

    /**
     * Create a new user
     * 
     * @param request DTO containing data to deposit money
     * @return Transaction data as TransactionResponseDTO
     */
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(@RequestBody TransactionDepositRequestDTO request){
        var transaction = this.service.deposit(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponseDTO(
            transaction,
            transaction.getPayeeWallet()
        ));
    }
}