package com.arquivs.sysguard.controller;


import com.arquivs.sysguard.dto.LocatarioRequestDTO;
import com.arquivs.sysguard.dto.LocatarioResponseDTO;
import com.arquivs.sysguard.entity.LocatarioEntity;
import com.arquivs.sysguard.mapper.LocatarioMapper;
import com.arquivs.sysguard.service.LocatarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locatarios")
public class LocatarioController {

    @Autowired
    private LocatarioService service;

    @PostMapping
    public ResponseEntity<LocatarioResponseDTO> salvar(@RequestBody LocatarioRequestDTO locatario) {
        if (service.cpfExistente(locatario.getCpf())){
            return ResponseEntity.badRequest().build();
        }
        LocatarioEntity salvo = LocatarioMapper.toEntity(locatario);
        LocatarioEntity locatarioSalvo = service.salvar(salvo);

        return ResponseEntity.ok(LocatarioMapper.toDTO(locatarioSalvo));
    }

    @GetMapping
    public List<LocatarioEntity> listarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<LocatarioEntity> listarPorCpf(@PathVariable String cpf) {
        return service.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<LocatarioEntity> atualizar(@PathVariable String cpf, @RequestBody LocatarioEntity locatario) {
        if (service.buscarPorCpf(cpf).isPresent()) {
            locatario.setCpf(cpf);
            return ResponseEntity.ok(service.salvar(locatario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        if (service.buscarPorCpf(cpf).isPresent()) {
            service.deletar(cpf);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
