package com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity;

import com.br.edu.ufersa.prog_web.mec_tec.customer.model.entity.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Table(name = "tb_machine")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Category category;

    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id", nullable = false) // fk na tabela
    private Customer customer;

}
