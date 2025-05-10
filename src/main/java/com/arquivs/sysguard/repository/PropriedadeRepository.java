package com.arquivs.sysguard.repository;

import com.arquivs.sysguard.dto.PropriedadeDTO;
import com.arquivs.sysguard.entity.PropriedadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropriedadeRepository extends JpaRepository<PropriedadeEntity, Long> {
    List<PropriedadeEntity> findAllByEmpresaId(Long empresaId);
}
