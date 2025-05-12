package com.arquivs.sysguard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO {
    private String id;
    private String nome;
    private String userIdCriptografado;

    public EmpresaDTO() {
        this.id = id;
        this.nome = nome;
        this.userIdCriptografado = userIdCriptografado;
    }
}

