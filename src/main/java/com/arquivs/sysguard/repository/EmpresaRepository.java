package com.arquivs.sysguard.repository;

import com.arquivs.sysguard.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, String> {
    List<EmpresaEntity> findByUserId(String userId);

}