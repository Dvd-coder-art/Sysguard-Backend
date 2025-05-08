package com.arquivs.sysguard.security;

import com.arquivs.sysguard.entity.UserEntity;
import com.arquivs.sysguard.model.Role;
import com.arquivs.sysguard.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setLogin("${ADMIN_LOGIN}");
            admin.setPassword(new BCryptPasswordEncoder().encode("${ADMIN_PASSWORD}"));
            admin.setRole(Role.valueOf("ADMIN"));
            usuarioRepository.save(admin);
            System.out.println("Admin criado com sucesso.");
        }
    }
}
