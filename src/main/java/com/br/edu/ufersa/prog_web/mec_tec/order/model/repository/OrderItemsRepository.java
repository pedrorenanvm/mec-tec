package com.br.edu.ufersa.prog_web.mec_tec.order.model.repository;

import com.br.edu.ufersa.prog_web.mec_tec.order.model.entity.Order_Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemsRepository extends JpaRepository<Order_Items, UUID> {
    List<Order_Items> findByOrderId(Long orderId);
    List<Order_Items> findByTaskId(UUID taskId);
    Optional<Order_Items> findByOrderIdAndTaskId(Long orderId, UUID taskId);
}