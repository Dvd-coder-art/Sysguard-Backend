package com.arquivs.sysguard.service;

import com.arquivs.sysguard.dto.PropriedadeDTO;
import com.arquivs.sysguard.entity.EmpresaEntity;
import com.arquivs.sysguard.entity.PropriedadeEntity;
import com.arquivs.sysguard.mapper.PropriedadeMapper;
import com.arquivs.sysguard.repository.EmpresaRepository;
import com.arquivs.sysguard.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepo;
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpresaRepository empresaRepo;

    public PropriedadeDTO cadastrarPropriedade(PropriedadeDTO dto) {

        EmpresaEntity empresa = empresaRepo.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));



        PropriedadeEntity propriedade = PropriedadeMapper.toEntity(dto);
        propriedade.setEmpresa(empresa);

        PropriedadeEntity novaPropriedade = propriedadeRepo.save(propriedade);


        return PropriedadeMapper.toDTO(novaPropriedade);
    }

    public List<PropriedadeDTO> listarPropriedadesDoUsuario(Long userId) {
        EmpresaEntity empresa = empresaService.obterEmpresaDoUsuario(userId);
        List<PropriedadeEntity> propriedades = propriedadeRepo.findAllByEmpresaId(empresa.getId());

        return propriedades.stream()
                .map(PropriedadeMapper::toDTO)
                .toList();
    }
}
