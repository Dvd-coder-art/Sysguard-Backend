package com.arquivs.sysguard.service;

import com.arquivs.sysguard.dto.EmpresaDTO;
import com.arquivs.sysguard.entity.EmpresaEntity;
import com.arquivs.sysguard.entity.UserEntity;
import com.arquivs.sysguard.mapper.EmpresaMapper;
import com.arquivs.sysguard.repository.EmpresaRepository;
import com.arquivs.sysguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepo;
    @Autowired
    private UserRepository userRepo;

    public EmpresaDTO criarEmpresa(EmpresaDTO dto, Long userId) {
        if (userId == null) {
            throw new RuntimeException("Usuário não autenticado");
        }

        List<EmpresaEntity> empresas = empresaRepo.findByUserId(userId);
        if (empresas != null && !empresas.isEmpty()) {
            throw new RuntimeException("Usuário já possui empresa cadastrada");
        }

        Optional<UserEntity> usuarioOpt = userRepo.findById(userId);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        EmpresaEntity empresa = new EmpresaEntity();
        empresa.setNome(dto.getNome());
        empresa.setUser(usuarioOpt.get());

        EmpresaEntity empresaSalva = empresaRepo.save(empresa);
        return EmpresaMapper.toDTO(empresaSalva);
    }

    public List<EmpresaEntity> obterEmpresaDoUsuario(Long userId) {
        return empresaRepo.findByUserId(userId);
    }

    public EmpresaEntity deletar(Long empresaId) {
        EmpresaEntity empresa = empresaRepo.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        empresaRepo.delete(empresa);
        return empresa;
    }
}