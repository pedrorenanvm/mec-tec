package com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePieceDTO {
    private UUID id;
    private String name;
    private double price;
    private String brand;
    private String description;
}
