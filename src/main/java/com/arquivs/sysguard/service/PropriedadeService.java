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
import java.util.Optional;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepo;
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpresaRepository empresaRepo;

    public PropriedadeDTO cadastrarPropriedade(PropriedadeDTO dto) {
        List<EmpresaEntity> empresas = empresaRepo.findByUserId(dto.getEmpresaId());

        Optional<EmpresaEntity> empresaOpt = empresas.stream()
                .filter(empresa -> empresa.getId().equals(dto.getEmpresaId()))
                .findFirst();

        EmpresaEntity empresa = empresaRepo.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada para o ID: " + dto.getEmpresaId()));

        PropriedadeEntity propriedade = PropriedadeMapper.toEntity(dto);
        propriedade.setEmpresa(empresa);

        PropriedadeEntity novaPropriedade = propriedadeRepo.save(propriedade);


        return PropriedadeMapper.toDTO(novaPropriedade);
    }

    public List<PropriedadeDTO> listarPropriedadesDoUsuario(String userId){
        List<EmpresaEntity> empresas = empresaService.obterEmpresaDoUsuario(userId);

        return empresas.stream()
                .flatMap(empresa -> propriedadeRepo.findAllByEmpresaId(empresa.getId()).stream())
                .map(PropriedadeMapper::toDTO)
                .toList();
    }

    public Optional<PropriedadeDTO> buscarPropriedadePorId(String id) {
        return propriedadeRepo.findById(id)
                .map(PropriedadeMapper::toDTO);
    }


    public PropriedadeDTO atualizarPropriedade(String id, PropriedadeDTO dto) {
        Optional<PropriedadeEntity> propriedadeOpt = propriedadeRepo.findById(id);

        if (propriedadeOpt.isPresent()) {
            PropriedadeEntity propriedade = propriedadeOpt.get();
            propriedade.setNome(dto.getNome());
            propriedade.setEndereco(dto.getEndereco());
            propriedade.setValorAluguel(dto.getValorAluguel());
            propriedade.setDataVencimento(dto.getDataVencimento());

            PropriedadeEntity propriedadeAtualizada = propriedadeRepo.save(propriedade);
            return PropriedadeMapper.toDTO(propriedadeAtualizada);
        } else {
            return null;
        }
    }

    public Void deletarPropriedade(String id){
        Optional<PropriedadeEntity> propriedades = propriedadeRepo.findById(id);

        PropriedadeEntity propriedade = propriedades.get();

        propriedadeRepo.delete(propriedade);
        return null;
    }
}
