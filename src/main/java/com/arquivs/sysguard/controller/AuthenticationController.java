package com.arquivs.sysguard.controller;


import com.arquivs.sysguard.dto.LoginResponseDTO;
import com.arquivs.sysguard.dto.infra.AuthenticationDTO;
import com.arquivs.sysguard.dto.infra.RegisterDTO;
import com.arquivs.sysguard.entity.UserEntity;
import com.arquivs.sysguard.repository.UserRepository;
import com.arquivs.sysguard.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }



    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto) {
        if (this.repository.findByLogin(dto.login())!= null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        UserEntity newUser = new UserEntity(dto.login(), encryptedPassword, dto.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
