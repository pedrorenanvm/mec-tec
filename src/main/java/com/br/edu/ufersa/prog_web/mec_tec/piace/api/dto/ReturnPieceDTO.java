package com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnPieceDTO {
    private UUID id;
    private String name;
    private double price;
    private String brand;
    private String description;
}
