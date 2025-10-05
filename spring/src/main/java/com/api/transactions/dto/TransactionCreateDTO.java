package com.api.transactions.dto;

import java.math.BigDecimal;

import com.api.wallets.WalletModel;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateDTO {
    private WalletModel payerWallet;
    
    @NonNull()
    private WalletModel payeeWallet;

    @NonNull()
    @DecimalMin(value = "0.1", inclusive = true, message = "O valor deve ser no mínimo 0.1")
    private BigDecimal amount;

    @NotBlank(message = "Tipo de transação é obrigatório")
    private String type;

    @NotBlank(message = "Tipo de moeda é obrigatório")
    private String currency;
}