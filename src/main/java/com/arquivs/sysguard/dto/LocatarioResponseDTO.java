package com.arquivs.sysguard.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class LocatarioResponseDTO {
    private String nome;
    private String cpf;
    private String endereco;
    private Date dataCobranca;
    private BigDecimal valorAluguel;
}
