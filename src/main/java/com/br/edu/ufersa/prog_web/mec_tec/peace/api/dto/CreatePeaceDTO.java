package com.br.edu.ufersa.prog_web.mec_tec.peace.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePeaceDTO {
    @NotBlank
    private String name;

    @Min(value = 1, message = "Amount must be at least 1")
    private int amount;

    @NotBlank
    private String brand;

    @NotBlank
    @Pattern(regexp = "\\d{1,150}", message = "Description must contain 1 to 150 digits.")
    private String description;

}
