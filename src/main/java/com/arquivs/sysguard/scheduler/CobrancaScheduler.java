package com.arquivs.sysguard.scheduler;

import com.arquivs.sysguard.entity.LocatarioEntity;
import com.arquivs.sysguard.repositoy.LocatarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CobrancaScheduler {
    private final LocatarioRepository locatarioRepository;

    @Scheduled(fixedRate = 15000)
    public void verificarCobrancasDias(){
        LocalDate hoje = LocalDate.now();

        List<LocatarioEntity> cobranca = locatarioRepository.findAll().stream()
                .filter(loc -> {
                    LocalDate data = loc.getDataCobranca();
                    return data.equals(hoje);
                })
                .toList();
        if (!cobranca.isEmpty()) {
            log.info("Cobranças encotradas para hoje: " + hoje);
            cobranca.forEach(loc ->
                    log.info(" - Locatário: {} | Valor: {}", loc.getNome(), loc.getValorAluguel())
            );
        } else {
            log.info("Nenhuma cobrança encontrada para hoje: " + hoje);
        }
    }
}
