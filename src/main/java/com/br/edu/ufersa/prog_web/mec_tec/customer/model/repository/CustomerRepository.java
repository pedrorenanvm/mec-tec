package com.br.edu.ufersa.prog_web.mec_tec.customer.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.customer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByCpf(String cpf);

    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByEmail(String email);
}
