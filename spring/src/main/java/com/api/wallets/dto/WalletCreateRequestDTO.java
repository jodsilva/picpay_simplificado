package com.api.wallets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletCreateRequestDTO {
    @NonNull()
    private Long userId;

    @NonNull()
    @PositiveOrZero(message = "O saldo não pode ser negativo")
    private Double balance;

    @NotBlank(message = "Tipo de moeda é obrigatório")
    private String currency;
}