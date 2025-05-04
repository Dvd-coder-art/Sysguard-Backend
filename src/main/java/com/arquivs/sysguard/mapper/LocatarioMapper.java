package com.arquivs.sysguard.mapper;

import com.arquivs.sysguard.dto.LocatarioRequestDTO;
import com.arquivs.sysguard.dto.LocatarioResponseDTO;
import com.arquivs.sysguard.entity.LocatarioEntity;

import java.util.List;
import java.util.stream.Collectors;

public class LocatarioMapper {


    public static LocatarioEntity toEntity(LocatarioRequestDTO dto) {
        LocatarioEntity entity = new LocatarioEntity();
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setEndereco(dto.getEndereco());
        entity.setDataCobranca(dto.getDataCobranca());
        entity.setValorAluguel(dto.getValorAluguel());
        entity.setEmailDestinatario(dto.getEmailDestinatario());
        return entity;
    }

    public static LocatarioResponseDTO toDTO(LocatarioEntity entity) {
        return new LocatarioResponseDTO(
                entity.getNome(),
                entity.getCpf(),
                entity.getEndereco(),
                entity.getDataCobranca(),
                entity.getValorAluguel(),
                entity.getEmailDestinatario()
        );
    }
}
