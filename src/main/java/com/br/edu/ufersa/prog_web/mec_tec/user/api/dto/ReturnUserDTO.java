package com.br.edu.ufersa.prog_web.mec_tec.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnUserDTO {
    private UUID id;
    private String username;
    private String email;
    private Boolean requiresPasswordChange;
    private RoleDTO role;
}
