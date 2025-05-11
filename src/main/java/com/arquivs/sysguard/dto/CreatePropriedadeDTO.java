package com.arquivs.sysguard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePropriedadeDTO(String nome, String endereco,LocalDate dataVencimento, BigDecimal valorAluguel) {
}
