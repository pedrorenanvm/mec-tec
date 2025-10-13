package com.br.edu.ufersa.prog_web.mec_tec.user.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("""
            SELECT u FROM User u WHERE
            u.username ILIKE %:searchTerm% OR
            u.email ILIKE %:searchTerm%
            """)
    Page<User> findAll(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
