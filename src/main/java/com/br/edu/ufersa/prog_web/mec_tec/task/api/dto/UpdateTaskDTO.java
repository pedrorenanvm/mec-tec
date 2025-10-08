package com.br.edu.ufersa.prog_web.mec_tec.task.api.dto;

import com.br.edu.ufersa.prog_web.mec_tec.config.validation.EnumValidator;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateTaskDTO {

    @NotNull(message = "ID cannot be null for an update")
    private UUID id;

    @NotBlank(message = "Name cannot be empty or null")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "Price cannot be empty or null")
    @PositiveOrZero(message = "Price must be a positive value or zero")
    private Double price;

    private String description;

    @NotNull(message = "Machine ID is mandatory")
    private UUID machineId;

    private List<UUID> piecesId;

}
