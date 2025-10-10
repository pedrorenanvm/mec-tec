package com.br.edu.ufersa.prog_web.mec_tec.piace.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.piace.model.entity.Piece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PieceRepository extends JpaRepository<Piece, UUID> {
    @Query("SELECT p FROM Piece p WHERE LOWER(p.name) LIKE %:searchTerm% OR LOWER(p.brand) LIKE %:searchTerm%")
    Page<Piece> findAllPaginate(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<Piece> findByName(String name);
    Optional<Piece> findByBrand(String brand);

}