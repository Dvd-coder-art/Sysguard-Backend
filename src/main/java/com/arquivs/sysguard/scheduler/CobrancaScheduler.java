package com.arquivs.sysguard.scheduler;

import com.arquivs.sysguard.entity.LocatarioEntity;
import com.arquivs.sysguard.repositoy.LocatarioRepository;
import com.arquivs.sysguard.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private EmailService emailService;

    @Value("${app.cobranca.multa}")
    private BigDecimal MULTA;

    @Value("${app.cobranca.juros-diario}")
    private BigDecimal JUROS_DIARIO_FIXO;

    @Scheduled(cron = "0 0 1 * * *")
    public void verificarCobrancasDias() {
        LocalDate hoje = LocalDate.now();

        List<LocatarioEntity> lista = locatarioRepository.findByPagoFalse();

        for (LocatarioEntity loc : lista) {
            LocalDate vencimento = loc.getDataCobranca();

            if (!Boolean.TRUE.equals(loc.getStatus()) && vencimento.isBefore(hoje)) {
                long diasAtraso = ChronoUnit.DAYS.between(vencimento, hoje);
                BigDecimal valorOriginal = loc.getValorAluguel();

                BigDecimal juros = JUROS_DIARIO_FIXO.multiply(BigDecimal.valueOf(diasAtraso));

                BigDecimal valorAtualizado = valorOriginal.add(MULTA).add(juros);

                loc.setValorAluguel(valorAtualizado);
                locatarioRepository.save(loc);

                log.warn("Locatário em atraso: {} | Dias em atraso: {} | Valor atualizado com multa/juros: {}",
                        loc.getNome(), diasAtraso, valorAtualizado);

                emailService.enviarEmail(
                        loc.getEmailDestinatario(),
                        "Alerta de Atraso no Pagamento",
                        String.format("Prezado(a) %s,\n\nSeu pagamento está atrasado há %d dias. O valor atualizado com multa e juros é: R$ %.2f.\n\nAtenciosamente,\nEquipe SysGuard",
                                loc.getNome(), diasAtraso, valorAtualizado)
                );

            } else {
                log.info("Locatário em dia ou pago: {}", loc.getNome());
            }
        }

    }
}
