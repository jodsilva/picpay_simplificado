package com.api.wallets.dto;

import java.math.BigDecimal;

import com.api.users.UserModel;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletCreateDTO {
    @NonNull()
    private UserModel user;

    @NonNull()
    @DecimalMin(value = "0", inclusive = true, message = "O valor não pode ser menor que 0")
    private BigDecimal balance;

    @NotBlank(message = "Tipo de moeda é obrigatório")
    private String currency;

    @NonNull()
    private Boolean isPrimary;
}