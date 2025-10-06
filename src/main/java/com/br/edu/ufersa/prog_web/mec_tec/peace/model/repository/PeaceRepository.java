package com.br.edu.ufersa.prog_web.mec_tec.peace.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.peace.model.entity.Peace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeaceRepository extends JpaRepository<Peace, UUID> {
    @Query("SELECT p FROM Peace p WHERE LOWER(p.name) LIKE %:searchTerm% OR LOWER(p.brand) LIKE %:searchTerm%")
    Page<Peace> findAllPaginate(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<Peace> findByName(String name);
    Optional<Peace> findByBrand(String brand);
    Page<Peace> findByNameContainingIgnoreCase(String name, Pageable pageable);

}