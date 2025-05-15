package com.arquivs.sysguard.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PropriedadeDTO {

    private String id;
    private String nome;
    private String endereco;
    private String gmail;
    private BigDecimal valorJuros;
    private BigDecimal valorMulta;
    private BigDecimal valorAluguel;
    private BigDecimal valorAluguelOriginal;
    private LocalDate dataVencimento;
    private String empresaId;
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
