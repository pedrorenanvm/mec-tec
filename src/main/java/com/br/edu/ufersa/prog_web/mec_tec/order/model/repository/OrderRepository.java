package com.br.edu.ufersa.prog_web.mec_tec.order.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.order.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o")
    Page<Order> findAllPaginate(Pageable pageable);
}
