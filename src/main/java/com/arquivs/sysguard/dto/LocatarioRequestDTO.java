package com.arquivs.sysguard.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Data
public class LocatarioRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Size(max = 11, message = "CPF deve ter no máximo 11 caracteres")
    private String cpf;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "Data de cobrança é obrigatória")
    private LocalDate dataCobranca;

    @NotBlank(message = "Valor do aluguel é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que 0")
    private BigDecimal valorAluguel;

    @NotBlank(message = "Email destinatário é obrigatório")
    private String emailDestinatario;


    private LocalDate novaDataCobranca;
}
