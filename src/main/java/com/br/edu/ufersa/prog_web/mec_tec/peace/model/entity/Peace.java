package com.br.edu.ufersa.prog_web.mec_tec.peace.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "tb_peace")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Peace {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String description;
}
