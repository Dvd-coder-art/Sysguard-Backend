package com.arquivs.sysguard.controller;

import com.arquivs.sysguard.dto.EmpresaDTO;
import com.arquivs.sysguard.dto.PropriedadeDTO;
import com.arquivs.sysguard.mapper.EmpresaMapper;
import com.arquivs.sysguard.mapper.PropriedadeMapper;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/propriedade")
public class PropriedadeController {



    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpresaMapper empresaMapper;

    @PostMapping("/empresa/{empresaId}")
    public ResponseEntity<ApiResponse<PropriedadeDTO>> cadastrar(@PathVariable String empresaId, @RequestBody PropriedadeDTO propriedadeDTO) {
        String userId = getUserId();

        List<PropriedadeDTO> propriedades = propriedadeService.listarPropriedadesDoUsuario(userId);
        Optional<PropriedadeDTO> propriedadeExistente = propriedades.stream()
                .filter(propriedade -> propriedade.getNome().equals(propriedadeDTO.getNome()))
                .findFirst();
        if (propriedadeExistente.isPresent()) {
            String propriedadeString = propriedadeExistente.get().getNome();
            ApiResponse<PropriedadeDTO> response = new ApiResponse<>(
                    "error",
                    "Propriedade com nome " + propriedadeString + " já cadastrada",
                    null
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<String> empresaIdOpt = empresaService.obterEmpresaDoUsuario(userId)
                .stream()
                .findFirst()
                .map(empresaMapper::toDTO)
                .map(EmpresaDTO::getId);


        if (empresaIdOpt.isEmpty()) {
            ApiResponse<PropriedadeDTO> response = new ApiResponse<>(
                    "error",
                    "Usuário não possui empresa cadastrada",
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        propriedadeDTO.setEmpresaId(empresaIdOpt.get());
        PropriedadeDTO novaPropriedade = propriedadeService.cadastrarPropriedade(propriedadeDTO);

        ApiResponse<PropriedadeDTO> response = new ApiResponse<>(
                "success",
                "Propriedade cadastrada com sucesso",
                novaPropriedade
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/empresa/{empresaId}/{id}")
    public ResponseEntity<ApiResponse<Optional<PropriedadeDTO>>> atualizar(@PathVariable String empresaId ,@PathVariable String id, @RequestBody PropriedadeDTO propriedadeAtualizada) {
        List<PropriedadeDTO> propriedades = propriedadeService.listarPropriedadesDoUsuario(getUserId());
        Optional<EmpresaDTO> empresa = empresaService.obterEmpresaPorId(empresaId).map(empresaMapper::toDTO);

        if(empresa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "error",
                    "Empresa não encontrada",
                    null
            ));
        }
        Optional<PropriedadeDTO> propriedadeExistente = propriedades.stream()
                .filter(propriedade -> propriedade.getId().equals(id))
                .findFirst();
        if (propriedadeExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "error",
                    "Propriedade não encontrada",
                    null
            ));
        }

        PropriedadeDTO propriedadeAtualizadaDTO = propriedadeService.atualizarPropriedade(id, propriedadeAtualizada);
        propriedadeAtualizadaDTO.setId(null);

        Optional<PropriedadeDTO> resposta = Optional.of(propriedadeAtualizadaDTO);

        ApiResponse<Optional<PropriedadeDTO>> response = new ApiResponse<>(
                "success",
                "Propriedade atualizada com sucesso",
                resposta
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<ApiResponse<List<PropriedadeDTO>>> listar(@PathVariable String empresaId){
        String userId = getUserId();

        Optional<EmpresaDTO> empresa = empresaService.obterEmpresaPorId(empresaId).map(empresaMapper::toDTO);

        if(empresa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "error",
                    "Empresa não encontrada",
                    null
            ));
        }
        List<PropriedadeDTO> propriedades = propriedadeService.listarPropriedadesDoUsuario(userId);

        ApiResponse<List<PropriedadeDTO>> response = new ApiResponse<>(
                "success",
                "Propriedades listadas com sucesso",
                propriedades
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/empresa/{empresaId}/{id}")
    public ResponseEntity<ApiResponse<Optional<PropriedadeDTO>>> buscarPorId(@PathVariable String empresaId,@PathVariable String id) {
        Optional<PropriedadeDTO> propriedade = propriedadeService.buscarPropriedadePorId(id);

        Optional<EmpresaDTO> empresa = empresaService.obterEmpresaPorId(empresaId).map(empresaMapper::toDTO);

        if(empresa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "error",
                    "Empresa não encontrada",
                    null
            ));
        }

        if (propriedade.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "error",
                    "Propriedade não encontrada",
                    null
            ));
        }

        ApiResponse<Optional<PropriedadeDTO>> response = new ApiResponse<>(
                "success",
                "Propriedade encontrada com sucesso",
                propriedade
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/empresa/{empresaId}/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable String empresaId,@PathVariable String id) {
        Optional<PropriedadeDTO> propriedades = propriedadeService.buscarPropriedadePorId(id);

        Optional<EmpresaDTO> empresa = empresaService.obterEmpresaPorId(empresaId).map(empresaMapper::toDTO);

        if(empresa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "error",
                    "Empresa não encontrada",
                    null
            ));
        }

       if(propriedades.isEmpty()){
           ApiResponse<Void> response = new ApiResponse<>(
                     "error",
                     "Propriedade não encontrada",
                     null
           );
           return ResponseEntity.badRequest().body(response);
       }
       ApiResponse<Void> response = new ApiResponse<>(
               "success",
               "Propriedade deletada com sucesso",
               null
         );

        propriedadeService.deletarPropriedade(id);
        return ResponseEntity.ok(response);
    }


    private String getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof com.arquivs.sysguard.entity.UserEntity user) {
            return user.getId();
        }
        throw new IllegalStateException("Usuário não autenticado");
    }


}
