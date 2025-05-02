package com.arquivs.sysguard.repositoy;

import com.arquivs.sysguard.entity.LocatarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocatarioRepository extends JpaRepository<LocatarioEntity, Long> {

}
