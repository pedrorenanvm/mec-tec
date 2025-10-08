package com.br.edu.ufersa.prog_web.mec_tec.task.model.entity;

import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Machine;
import com.br.edu.ufersa.prog_web.mec_tec.piace.model.entity.Piece;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_task_pieces",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "piece_id")
    )
    private List<Piece> pieces = new ArrayList<>();


}
