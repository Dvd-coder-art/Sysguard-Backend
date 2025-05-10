package com.arquivs.sysguard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO {
    private Long id;
    private String nome;

    public EmpresaDTO() {
        this.id = id;
        this.nome = nome;
    }
}

