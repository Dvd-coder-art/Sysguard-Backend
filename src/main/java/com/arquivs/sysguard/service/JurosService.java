package com.arquivs.sysguard.service;

import com.arquivs.sysguard.entity.JurosEntity;
import com.arquivs.sysguard.entity.PropriedadeEntity;
import com.arquivs.sysguard.repository.JurosRepository;
import com.arquivs.sysguard.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class JurosService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private JurosRepository jurosRespository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "*/10 * * * * *")
    public void calcularJuros() {
        System.out.println("Executando o cálculo de juros e multas...");
        List<PropriedadeEntity> propriedades = propriedadeRepository.findAll();
        LocalDate hoje = LocalDate.now();

        for (PropriedadeEntity prop : propriedades) {
            if (prop.getDataVencimento() != null && hoje.isAfter(prop.getDataVencimento())) {
                long diasAtraso = ChronoUnit.DAYS.between(prop.getDataVencimento(), hoje);

                if (prop.getValorAluguelOriginal() == null) {
                    prop.setValorAluguelOriginal(prop.getValorAluguel());
                }


                BigDecimal valorOriginal = prop.getValorAluguelOriginal();
                BigDecimal multaFixa = valorOriginal.multiply(prop.getValorMulta());
                BigDecimal multaDiaria = BigDecimal.TEN.multiply(BigDecimal.valueOf(diasAtraso)); // 10 reais por dia
                BigDecimal valorComJuros = valorOriginal.add(multaFixa).add(multaDiaria);
                BigDecimal valorMultas = valorComJuros.subtract(valorOriginal);





                prop.setValorAluguel(valorComJuros);
                propriedadeRepository.save(prop);


                JurosEntity juros = jurosRespository.findByPropriedadeId(prop.getId())
                        .orElse(new JurosEntity());
                juros.setPropriedade(prop);
                juros.setValorMulta(valorMultas);
                juros.setDataCalculo(hoje);
                jurosRespository.save(juros);

                System.out.printf("Propriedade ID %s | Dias de atraso: %d | Valor base: R$%.2f | Com juros: R$%.2f%n",
                        prop.getId(), diasAtraso, valorOriginal, valorComJuros);
            }
        }
    }
}
//    private void enviarEmailDeAviso(PropriedadeEntity prop, BigDecimal multa, long dias) {
//        List<PropriedadeEntity> propriedades = propriedadeRepository.findAll();
//        String email = prop.getGmail();
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email); // Substitua com o e-mail real do dono da propriedade
//        message.setSubject("Aviso de Vencimento e Multa");
//        message.setText("Sua propriedade com vencimento em " + prop.getDataVencimento() +
//                " está com " + dias + " dia(s) de atraso.\nMulta total: R$ " + multa);
//        mailSender.send(message);
//    }

