package com.arquivs.sysguard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "locatario")
public class LocatarioEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(length = 11, nullable = false,unique = true)
    private String cpf;

    private String endereco;

    @Temporal(TemporalType.DATE)
    private LocalDate dataCobranca;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorAluguel;

    private String emailDestinatario;

    private Boolean pago = false;

    private BigDecimal valorMulta;


}
