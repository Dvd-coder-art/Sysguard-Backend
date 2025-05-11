package com.arquivs.sysguard.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PropriedadeDTO {

    private Long id;
    private String nome;
    private String endereco;
    private BigDecimal valorAluguel;
    private LocalDate dataVencimento;
    private Long empresaId;
    private String empresaNome;

    public PropriedadeDTO() {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
        this.dataVencimento = dataVencimento;
        this.empresaId = empresaId;
        this.empresaNome = empresaNome;
    }
}
