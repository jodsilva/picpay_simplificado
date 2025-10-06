package com.api.transactions;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.api.common.BaseService;
import com.api.common.NotificationService;
import com.api.enums.EnumTransactionsType;
import com.api.enums.EnumUsersType;
import com.api.transactions.dto.TransactionCreateDTO;
import com.api.transactions.dto.TransactionDepositRequestDTO;
import com.api.transactions.dto.TransactionTransferRequestDTO;
import com.api.wallets.WalletService;
import jakarta.transaction.Transactional;

@Service
public class TransactionService extends BaseService<TransactionModel>{

    private final WalletService walletService; 
    private final WebClient webClient;
    private final NotificationService notificationService;

    public TransactionService(
        TransactionRepository repository, 
        WalletService walletService,
        WebClient webClient,
        NotificationService notificationService
    ){
        super(repository);
        this.walletService = walletService;
        this.webClient = webClient;
        this.notificationService = notificationService;
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
    @Transactional
    public TransactionModel transfer(TransactionTransferRequestDTO transferDTO){ 
        this.checkTransferAuthorization();

        var payerWallet = walletService.findOrFail(transferDTO.getPayerWalletId());
        var payeeWallet = walletService.findPrimaryByUserId(transferDTO.getPayee());

        if(
            payerWallet.getUser().getEnumUsersType() == EnumUsersType.MERCHANT ||
            payerWallet.getId().equals(payeeWallet.getId())
        ){
             throw new IllegalArgumentException("Sem permissão para realizar a transferência");
        }

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

    @SuppressWarnings("unchecked")
    public void checkTransferAuthorization() {
        Map<String, Object> response = webClient.get()
                .uri("util.devi.tools/api/v2/authorize")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Transferência não autorizada");
        }

        Map<String, Object> data = (Map<String, Object>) response.get("data");
        if (data == null || !Boolean.TRUE.equals(data.get("authorization"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Transferência não autorizada");
        }
    }


    /**
     * Make a transfer between users 
     * 
     * @param id
     * @return TransactionResponseDTO
     */
    @Transactional
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

    public void notifyTransfer(TransactionModel transaction){
        var payee = transaction.getPayerWallet().getUser();
        var payer = transaction.getPayeeWallet().getUser();
        this.notificationService.sendEmail(
            payee.getEmail(), 
            payer.getEmail() + "enviou uma transfêrencia no valor de " + transaction.getAmount()
        );
    }
}