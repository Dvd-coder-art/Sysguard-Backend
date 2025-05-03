package com.arquivs.sysguard.repositoy;

import com.arquivs.sysguard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UserEntity, String > {
    UserDetails findByLogin(String login);
}
