package com.arquivs.sysguard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePropriedadeDTO(String nome, String endereco,String gmail, BigDecimal valorMulta, BigDecimal valorJuros,LocalDate dataVencimento, BigDecimal valorAluguel,BigDecimal valorAluguelOriginal) {
}
