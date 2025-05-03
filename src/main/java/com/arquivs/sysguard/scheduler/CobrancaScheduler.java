package com.arquivs.sysguard.scheduler;

import com.arquivs.sysguard.entity.LocatarioEntity;
import com.arquivs.sysguard.repositoy.LocatarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CobrancaScheduler {

    private final LocatarioRepository locatarioRepository;

    private static final BigDecimal MULTA = new BigDecimal("30.0");
    private static final BigDecimal JUROS_DIARIO_FIXO = new BigDecimal("10.00");

    @Scheduled(cron = "0 0 0 * * *")
    public void verificarCobrancasDias() {
        LocalDate hoje = LocalDate.now();

        List<LocatarioEntity> lista = locatarioRepository.findAll();

        for (LocatarioEntity loc : lista) {
            LocalDate vencimento = loc.getDataCobranca();

            if (!Boolean.TRUE.equals(loc.getPago()) && vencimento.isBefore(hoje)) {
                long diasAtraso = ChronoUnit.DAYS.between(vencimento, hoje);
                BigDecimal valorOriginal = loc.getValorAluguel();

                BigDecimal juros = JUROS_DIARIO_FIXO.multiply(BigDecimal.valueOf(diasAtraso));

                BigDecimal valorAtualizado = valorOriginal.add(MULTA).add(juros);

                loc.setValorAluguel(valorAtualizado);
                locatarioRepository.save(loc);

                log.warn("Locatário em atraso: {} | Dias em atraso: {} | Valor atualizado com multa/juros: {}",
                        loc.getNome(), diasAtraso, valorAtualizado);
            } else {
                log.info("Locatário em dia ou pago: {}", loc.getNome());
            }
        }
    }
}
