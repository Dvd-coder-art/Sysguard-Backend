package com.arquivs.sysguard.controller;

import com.arquivs.sysguard.dto.EmpresaDTO;
import com.arquivs.sysguard.entity.UserEntity;
import com.arquivs.sysguard.response.ApiResponse;
import com.arquivs.sysguard.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmpresaDTO>> criarEmpresa(@RequestBody EmpresaDTO dto) {
        Long userId = getUserId();
        EmpresaDTO empresaCriada = empresaService.criarEmpresa(dto);
        ApiResponse<EmpresaDTO> response = new ApiResponse<>(
                "success",
                "Empresa criada com sucesso",
                empresaCriada
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Long getUserId() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}