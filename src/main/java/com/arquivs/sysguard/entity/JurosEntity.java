package com.arquivs.sysguard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "juros")
@Getter
@Setter
public class JurosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private BigDecimal valorMulta;

    private LocalDate dataCalculo;

    @ManyToOne
    @JoinColumn(name = "propriedade_id", nullable = false)
    private PropriedadeEntity propriedade;
}
