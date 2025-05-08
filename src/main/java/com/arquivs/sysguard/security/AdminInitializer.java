package com.arquivs.sysguard.security;

import com.arquivs.sysguard.entity.UserEntity;
import com.arquivs.sysguard.model.Role;
import com.arquivs.sysguard.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${ADMIN_LOGIN}")
    private String adminLogin;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setLogin(adminLogin);
            admin.setPassword(new BCryptPasswordEncoder().encode(adminPassword));
            admin.setRole(Role.ADMIN);
            usuarioRepository.save(admin);
            System.out.println("Admin criado com sucesso.");
        }
    }
}
