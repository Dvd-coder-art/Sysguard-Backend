package com.arquivs.sysguard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Table(name = "propriedades")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PropriedadeEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    private String nome;

    private String endereco;

    private BigDecimal valorAluguel;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimento;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;
    

}
