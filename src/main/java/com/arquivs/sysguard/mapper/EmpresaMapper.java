package com.arquivs.sysguard.mapper;

import com.arquivs.sysguard.dto.EmpresaDTO;
import com.arquivs.sysguard.entity.EmpresaEntity;

public class EmpresaMapper {
    public static EmpresaDTO toDTO(EmpresaEntity entity) {
        if (entity == null) return null;

        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        // Adicione outros campos se necessário

        return dto;
    }

    public static EmpresaEntity toEntity(EmpresaDTO dto) {
        if (dto == null) return null;
        EmpresaEntity entity = new EmpresaEntity();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        // Adicione outros campos se necessário

        return entity;
    }
}