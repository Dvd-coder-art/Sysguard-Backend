package com.arquivs.sysguard.mapper;

import com.arquivs.sysguard.dto.EmpresaDTO;
import com.arquivs.sysguard.entity.EmpresaEntity;
import com.arquivs.sysguard.security.AesIdEncoder;
import org.springframework.stereotype.Component;


@Component
public class EmpresaMapper {

    private final AesIdEncoder aesIdEncoder;

    public EmpresaMapper(AesIdEncoder aesIdEncoder) {
        this.aesIdEncoder = aesIdEncoder;
    }

    public  EmpresaDTO toDTO(EmpresaEntity entity) {
        if (entity == null) return null;

        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        if (entity.getUser() != null && entity.getUser().getId() != null) {
            dto.setUserIdCriptografado(aesIdEncoder.encode(entity.getUser().getId().toString()));
        }

        return dto;
    }

    public  EmpresaEntity toEntity(EmpresaDTO dto) {
        if (dto == null) return null;
        EmpresaEntity entity = new EmpresaEntity();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());


        return entity;
    }
}