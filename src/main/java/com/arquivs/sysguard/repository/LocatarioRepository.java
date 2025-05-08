package com.arquivs.sysguard.repository;

import com.arquivs.sysguard.entity.LocatarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocatarioRepository extends JpaRepository<LocatarioEntity, Long> {
    List<LocatarioEntity> findByStatus(Boolean status);

    Optional<LocatarioEntity> findByCpf(String cpf);
}
