package com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto;

import jakarta.validation.constraints.Email;
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
public class CreateCustomerDTO {
    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "Cpf must contain exactly 11 digits.")
    private String cpf;

    @NotBlank
    @Pattern(regexp = "\\d{1,20}", message = "Phone must contain 1 to 20 digits.")
    private String phone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;
}
