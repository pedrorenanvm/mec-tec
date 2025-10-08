package com.br.edu.ufersa.prog_web.mec_tec.order.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Override
    Optional<Order> findById(UUID uuid);
}
