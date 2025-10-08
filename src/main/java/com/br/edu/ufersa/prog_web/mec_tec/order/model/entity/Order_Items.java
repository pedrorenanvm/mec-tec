package com.br.edu.ufersa.prog_web.mec_tec.order.model.entity;

import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_order_service")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order_Items {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(updatable = false, nullable = false)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @Column(nullable = true)
    private Date deletedAt;
}
