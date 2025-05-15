// PropriedadeMapper.java
package com.arquivs.sysguard.mapper;

import com.arquivs.sysguard.dto.PropriedadeDTO;
import com.arquivs.sysguard.entity.EmpresaEntity;
import com.arquivs.sysguard.entity.PropriedadeEntity;


public class PropriedadeMapper {
    public static PropriedadeDTO toDTO(PropriedadeEntity entity) {
        if (entity == null) {
            return null;
        }

        PropriedadeDTO dto = new PropriedadeDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEndereco(entity.getEndereco());
        dto.setGmail(entity.getGmail());
        dto.setValorJuros(entity.getValorJuros());
        dto.setValorMulta(entity.getValorMulta());
        dto.setValorAluguel(entity.getValorAluguel());
        dto.setValorAluguelOriginal(entity.getValorAluguelOriginal());
        dto.setDataVencimento(entity.getDataVencimento());
        dto.setEmpresaNome(entity.getEmpresa().getNome());
        dto.setEmpresaId(entity.getEmpresa().getId());

        return dto;
    }

    public static PropriedadeEntity toEntity(PropriedadeDTO dto) {
        if (dto == null) {
            return null;
        }

        PropriedadeEntity entity = new PropriedadeEntity();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEndereco(dto.getEndereco());
        entity.setGmail(dto.getGmail());
        entity.setValorJuros(dto.getValorJuros());
        entity.setValorMulta(dto.getValorMulta());
        entity.setValorAluguel(dto.getValorAluguel());
        entity.setValorAluguelOriginal(dto.getValorAluguelOriginal());
        entity.setDataVencimento(dto.getDataVencimento());
        EmpresaEntity empresa = new EmpresaEntity();
        empresa.setId(dto.getEmpresaId());
        entity.setEmpresa(empresa);

        return entity;
    }
}