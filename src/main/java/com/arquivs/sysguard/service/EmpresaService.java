package com.arquivs.sysguard.service;

import com.arquivs.sysguard.dto.EmpresaDTO;
import com.arquivs.sysguard.entity.EmpresaEntity;
import com.arquivs.sysguard.entity.UserEntity;
import com.arquivs.sysguard.repository.EmpresaRepository;
import com.arquivs.sysguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired private EmpresaRepository empresaRepo;
    @Autowired
    private UserRepository userRepo;

    public EmpresaEntity criarEmpresa(EmpresaDTO dto) {
        if (empresaRepo.findByUserId(dto.getId()).isPresent()) {
            throw new RuntimeException("Usuário já possui uma empresa.");
        }

        UserEntity usuario = userRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        EmpresaEntity empresa = new EmpresaEntity();
        empresa.setNome(dto.getNome());
        empresa.setUser(usuario);


        return empresaRepo.save(empresa);
    }

    public EmpresaEntity obterEmpresaDoUsuario(Long userId) {
        return empresaRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }
}