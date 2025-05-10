package com.arquivs.sysguard.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropriedadeDTO {

    private Long id;
    private String nome;
    private Long empresaId;
    private String empresaNome;

    public PropriedadeDTO() {
        this.id = id;
        this.nome = nome;
        this.empresaId = empresaId;
        this.empresaNome = empresaNome;
    }
}
