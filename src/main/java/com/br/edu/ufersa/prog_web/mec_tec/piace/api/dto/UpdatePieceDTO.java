package com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePieceDTO {
    private String name;
    private double price;
    private String brand;
    private String description;
}
