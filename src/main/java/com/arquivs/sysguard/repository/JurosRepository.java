package com.arquivs.sysguard.repository;

import com.arquivs.sysguard.entity.JurosEntity;
import com.arquivs.sysguard.entity.PropriedadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JurosRepository extends JpaRepository<JurosEntity, Long> {
    Optional<JurosEntity> findByPropriedadeId(String propriedadeId);

}
