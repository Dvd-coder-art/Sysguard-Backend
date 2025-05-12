package com.arquivs.sysguard.controller;

import com.arquivs.sysguard.dto.EmpresaDTO;
import com.arquivs.sysguard.entity.EmpresaEntity;
import com.arquivs.sysguard.entity.UserEntity;
import com.arquivs.sysguard.mapper.EmpresaMapper;
import com.arquivs.sysguard.response.ApiResponse;
import com.arquivs.sysguard.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmpresaDTO>> criarEmpresa(@RequestBody EmpresaDTO dto) {
        String userId = getUserId();
        try {
            EmpresaEntity entity = EmpresaMapper.toEntity(dto);
            EmpresaEntity empresaSalva = (EmpresaEntity) empresaService.salvarEmpresa(entity, userId);
            EmpresaDTO empresaCriada = EmpresaMapper.toDTO(empresaSalva);
            ApiResponse<EmpresaDTO> response = new ApiResponse<>(
                    "success",
                    "Empresa criada com sucesso",
                    empresaCriada
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            ApiResponse<EmpresaDTO> response = new ApiResponse<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpresaDTO>>> obterEmpresaDoUsuario() {
        String userId = getUserId();
        List<EmpresaDTO> empresas = empresaService.obterEmpresaDoUsuario(userId)
                .stream()
                .map(EmpresaMapper::toDTO)
                .toList();

        if (empresas == null || empresas.isEmpty()) {
            ApiResponse<List<EmpresaDTO>> errorResponse = new ApiResponse<>("error", "Não possui empresa cadastrada", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ApiResponse<List<EmpresaDTO>> response = new ApiResponse<>(
                "success",
                "Empresas obtidas com sucesso",
                empresas
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpresaDTO>> buscarPorId(@PathVariable String id){
        return empresaService.obterEmpresaPorId(id).map(empresa ->{
            EmpresaDTO empresaDTO = EmpresaMapper.toDTO(empresa);
            ApiResponse<EmpresaDTO> response = new ApiResponse<>(
                    "success",
                    "Empresa encontrada com sucesso",
                    empresaDTO
            );
            return ResponseEntity.ok(response);
        }).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpresaDTO>> atualizar(@PathVariable String id, @RequestBody EmpresaDTO dto){
        Optional<EmpresaEntity> existente = empresaService.obterEmpresaPorId(id);

        if(existente.isEmpty()){
            ApiResponse<EmpresaDTO> response = new ApiResponse<>(
                    "error",
                    "Empresa não encontrada",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        EmpresaEntity atualizada = existente.get();
        atualizada.setNome(dto.getNome());


        EmpresaEntity salvo = (EmpresaEntity) empresaService.salvarEmpresa(atualizada, atualizada.getUser().getId());
        EmpresaDTO salvoDTO = EmpresaMapper.toDTO(salvo);

        ApiResponse<EmpresaDTO> response = new ApiResponse<>(
                "success",
                "Empresa atualizada com sucesso",
                salvoDTO
        );

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable String id){
        try {
            empresaService.deletarEmpresa(id);
            ApiResponse<Void> response = new ApiResponse<>(
                    "success",
                    "Empresa deletada com sucesso",
                    null);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    private String getUserId() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}