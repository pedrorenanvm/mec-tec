package com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto;

import com.br.edu.ufersa.prog_web.mec_tec.config.validation.EnumValidator;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;
@Data
public class UpdateMachineDTO {
    private UUID id;
    @NotBlank(message = "Model cannot be empty or null")
    private String model;
    @NotBlank(message = "Brand cannot be empty or null")
    private String brand;
    @NotNull
    @EnumValidator(enumClass = Category.class, message = "Categegory must be any of [POWER_TOOL, HOME_APPLIANCE]")
    private String category;
    @NotBlank(message = "Description cannot be empty or null")
    @Size(min = 10, message = "The description must have at least 10 characters")
    private String description;
    @NotNull
    private UUID customerID;
}
