package com.arquivs.sysguard.entity;

import com.arquivs.sysguard.dto.EmpresaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "empresa")
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaEntity extends EmpresaDTO {

    @Id
    private String id;

    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<PropriedadeEntity> propriedades;


}
