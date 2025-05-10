package com.arquivs.sysguard.controller;

import com.arquivs.sysguard.dto.PropriedadeDTO;
import com.arquivs.sysguard.response.ApiResponse;
import com.arquivs.sysguard.service.EmpresaService;
import com.arquivs.sysguard.service.PropriedadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/propriedades")
public class PropriedadeController {

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<ApiResponse<PropriedadeDTO>> cadastrar(@RequestBody PropriedadeDTO propriedadeDTO) {
        Long userId = getUserId();
        Long empresaId = empresaService.obterEmpresaDoUsuario(userId).getId();
        propriedadeDTO.setEmpresaId(empresaId);

        PropriedadeDTO novaPropriedade = propriedadeService.cadastrarPropriedade(propriedadeDTO);

        ApiResponse<PropriedadeDTO> response = new ApiResponse<>(
                "success",
                "Propriedade cadastrada com sucesso",
                novaPropriedade
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<PropriedadeDTO>>> listar() {
        Long userId = getUserId();
        List<PropriedadeDTO> propriedades = propriedadeService.listarPropriedadesDoUsuario(userId);
        ApiResponse<List<PropriedadeDTO>> response = new ApiResponse<>(
                "success",
                "Propriedades listadas com sucesso",
                propriedades
        );
        return ResponseEntity.ok(response);
    }

    private Long getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof com.arquivs.sysguard.entity.UserEntity user) {
            return user.getId();
        }
        throw new IllegalStateException("Usuário não autenticado");
    }


}
