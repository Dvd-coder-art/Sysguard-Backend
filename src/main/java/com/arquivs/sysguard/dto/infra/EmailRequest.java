package com.arquivs.sysguard.dto.infra;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {

    private String destinatario;
    private String assunto;
    private String mensagem;

}
