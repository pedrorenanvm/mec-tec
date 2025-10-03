package com.br.edu.ufersa.prog_web.mec_tec.peace.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.peace.model.entity.Peace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeaceRepository extends JpaRepository<Peace, UUID> {
    Optional<Peace> findByName(String name);
    Optional<Peace> findByBrand(String brand);
}
