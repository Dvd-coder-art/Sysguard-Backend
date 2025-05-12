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

    public EmpresaDTO salvarEmpresa(EmpresaEntity empresa, String userId) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + userId));
        empresa.setUser(user);
        return empresaRepo.save(empresa);
    }



    public Optional<EmpresaEntity> obterEmpresaPorId(String empresaId) {
        return empresaRepo.findById(empresaId);
    }


    public List<EmpresaEntity> obterEmpresaDoUsuario(String userId) {
        return empresaRepo.findByUserId(userId);
    }

    public void deletarEmpresa(String empresaId) {

        empresaRepo.deleteById(empresaId);
    }
}