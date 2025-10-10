package com.br.edu.ufersa.prog_web.mec_tec.task.api.dto;

import com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto.ReturnMachineDTO;
import com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto.ReturnPieceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnTaskDTO {

    private UUID id;
    private String name;
    private Double price;
    private String description;
    private ReturnMachineDTO machine;
    private List <ReturnPieceDTO> pieces;

}
