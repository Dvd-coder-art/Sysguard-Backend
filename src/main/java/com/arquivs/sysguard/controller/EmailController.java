package com.arquivs.sysguard.controller;


import com.arquivs.sysguard.dto.infra.EmailRequest;
import com.arquivs.sysguard.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public String enviarEmail(@RequestBody EmailRequest emailRequest) {
        emailService.enviarEmail(
                emailRequest.getDestinatario(), emailRequest.getAssunto(), emailRequest.getMensagem()
        );
        return "Email enviado com sucesso!";
    }
}
