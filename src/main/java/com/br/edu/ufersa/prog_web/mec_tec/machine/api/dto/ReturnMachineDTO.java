package com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto;

import com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto.ReturnCustomerDTO;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnMachineDTO {
    private UUID id;
    private String model;
    private String brand;
    private Category category;
    private String description;
    private ReturnCustomerDTO customer;
}
