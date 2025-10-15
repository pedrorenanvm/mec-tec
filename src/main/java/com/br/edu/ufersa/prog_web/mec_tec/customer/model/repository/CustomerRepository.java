package com.br.edu.ufersa.prog_web.mec_tec.customer.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.customer.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("""
            SELECT c FROM Customer c WHERE
            c.name ILIKE %:searchTerm% OR
            c.cpf ILIKE %:searchTerm% OR
            c.phone ILIKE %:searchTerm% OR
            c.email ILIKE %:searchTerm% OR
            c.address ILIKE %:searchTerm%
            """)
    Page<Customer> findAll(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<Customer> findByCpf(String cpf);

    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByEmail(String email);
}
