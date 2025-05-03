package com.arquivs.sysguard.service;


import com.arquivs.sysguard.entity.LocatarioEntity;
import com.arquivs.sysguard.repositoy.LocatarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LocatarioService {

    @Autowired
    public LocatarioRepository repository;

    public LocatarioEntity salvar(LocatarioEntity locatario) {
        return repository.save(locatario);
    }

    public List<LocatarioEntity> buscarTodos() {
        return repository.findAll();
    }

    public boolean cpfExistente(String cpf) {
        return repository.findAll().stream()
                .anyMatch(locatario -> locatario.getCpf().equals(cpf));
    }

    public Optional<LocatarioEntity> buscarPorCpf(String cpf) {
        return repository.findAll().stream()
                .filter(locatario -> locatario.getCpf().equals(cpf))
                .findFirst();
    }

    public void deletar(String cpf){
        repository.findAll().stream()
                .filter(locatario -> locatario.getCpf().equals(cpf))
                .findFirst()
                .ifPresent(repository::delete);
    }

    public void registrarPagamento(String cpf) {
        LocatarioEntity locatario = repository.findAll().stream()
                .filter(l -> l.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Locatário não encontrado"));

        locatario.setPago(true);
        locatario.setValorMulta(null);


        LocalDate novaData = locatario.getDataCobranca().plusMonths(1);
        locatario.setDataCobranca(novaData);

        repository.save(locatario);
    }

}
