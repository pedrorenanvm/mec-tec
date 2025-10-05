package com.br.edu.ufersa.prog_web.mec_tec.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank
    @Size(min = 5, max = 20, message = "Username must contain 5 to 20 characters")
    private String username;

    @NotBlank
    @Email
    private String email;
}
