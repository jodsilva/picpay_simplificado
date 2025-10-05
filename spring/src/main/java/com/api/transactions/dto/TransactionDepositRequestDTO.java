package com.api.transactions.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDepositRequestDTO {
    @NonNull()
    private Long payeeWalletId;

    @NonNull()
    @DecimalMin(value = "0.1", inclusive = true, message = "O valor deve ser no mínimo 0.1")
    private BigDecimal amount;
}