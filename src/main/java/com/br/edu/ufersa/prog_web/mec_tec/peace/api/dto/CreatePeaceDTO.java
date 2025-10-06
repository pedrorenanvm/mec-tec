package com.br.edu.ufersa.prog_web.mec_tec.peace.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Min(value = 0, message = "Amount must be at least 0")
    private double price;

    @NotBlank
    private String brand;

    @Size(min = 1, max = 150, message = "Description must be between 1 and 150 characters.")
    @NotBlank
    private String description;


}
