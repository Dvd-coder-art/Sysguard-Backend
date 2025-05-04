package com.arquivs.sysguard.controller;


import com.arquivs.sysguard.dto.LocatarioRequestDTO;
import com.arquivs.sysguard.dto.LocatarioResponseDTO;
import com.arquivs.sysguard.entity.LocatarioEntity;
import com.arquivs.sysguard.mapper.LocatarioMapper;
import com.arquivs.sysguard.repositoy.LocatarioRepository;
import com.arquivs.sysguard.service.LocatarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locatarios")
public class LocatarioController {

    @Autowired
    private LocatarioService service;

    @Autowired
    private LocatarioRepository locatarioRepository;

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
    public ResponseEntity<Optional<LocatarioEntity>> listarPorCpf(@PathVariable String cpf) {
        Optional<LocatarioEntity> locatario = service.buscarPorCpf(cpf);
        if (locatario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(locatario);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Optional<LocatarioResponseDTO>> atualizar(@PathVariable String cpf, @RequestBody LocatarioEntity locatario) {
        Optional<LocatarioEntity> locatorio = service.buscarPorCpf(cpf);
        if (locatorio.isPresent()) {
            locatario.setCpf(cpf);
            LocatarioEntity locatarioAtualizado = service.salvar(locatario);
            return ResponseEntity.ok(Optional.of(LocatarioMapper.toDTO(locatarioAtualizado)));
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{cpf}/pagamento")
    public ResponseEntity<String> registrarPagamento(
            @PathVariable String cpf,
            @RequestBody(required = false) LocatarioRequestDTO request) {

        Optional<LocatarioEntity> optional = locatarioRepository.findByCpf(cpf);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LocatarioEntity loc = optional.get();
        loc.setStatus(true);

        if (request != null) {
            if (request.getValorAluguel() != null) {
                loc.setValorAluguel(request.getValorAluguel());
            }
            if (request.getNovaDataCobranca() != null) {
                loc.setDataCobranca(request.getNovaDataCobranca());
            }
        }

        locatarioRepository.save(loc);
        return ResponseEntity.ok("Pagamento registrado com sucesso.");
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
