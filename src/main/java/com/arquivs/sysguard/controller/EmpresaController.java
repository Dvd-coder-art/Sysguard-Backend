package com.arquivs.sysguard.controller;

import com.arquivs.sysguard.dto.EmpresaDTO;
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

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmpresaDTO>> criarEmpresa(@RequestBody EmpresaDTO dto) {
        Long userId = getUserId();
        try {
            EmpresaDTO empresaCriada = empresaService.criarEmpresa(dto, userId);
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
        Long userId = getUserId();
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id){
        try {
            empresaService.deletar(id);
            ApiResponse<Void> response = new ApiResponse<>(
                    "success",
                    "Empresa deletada com sucesso",
                    null);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    private Long getUserId() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}