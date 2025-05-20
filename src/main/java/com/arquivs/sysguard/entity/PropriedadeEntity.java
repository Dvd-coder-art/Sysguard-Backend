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
import java.util.ArrayList;
import java.util.List;

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

    @Column(unique = true)
    private String gmail;


    private BigDecimal valorJuros;

    private BigDecimal valorMulta;

    private BigDecimal valorAluguel;

    @Column(nullable = true)
    private BigDecimal valorAluguelOriginal;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimento;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;

    @OneToMany(mappedBy = "propriedade", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<JurosEntity> juros = new ArrayList<>();

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
        if (this.valorAluguelOriginal == null && valorAluguel != null){
            this.valorAluguelOriginal = valorAluguel;
        }
    }

}
