package com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnCustomerDTO {
    private UUID id;
    private String name;
    private String cpf;
    private String phone;
    private String email;
    private String address;
}
