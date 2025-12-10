package com.br.edu.ufersa.prog_web.mec_tec.user.api.dto;

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
public class PasswordChangeUserDTO {
    @NotBlank
    @Size(min = 5, max = 20, message = "Old password must contain 5 to 20 characters")
    private String oldPassword;

    @NotBlank
    @Size(min = 5, max = 20, message = "New password must contain 5 to 20 characters")
    private String newPassword;
}
