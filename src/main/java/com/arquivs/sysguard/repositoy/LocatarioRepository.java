package com.arquivs.sysguard.repositoy;

import com.arquivs.sysguard.entity.LocatarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocatarioRepository extends JpaRepository<LocatarioEntity, Long> {
    List<LocatarioEntity> findByPagoFalse();
}
