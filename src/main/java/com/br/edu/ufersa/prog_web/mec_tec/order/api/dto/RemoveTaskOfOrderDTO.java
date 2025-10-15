package com.br.edu.ufersa.prog_web.mec_tec.order.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveTaskOfOrderDTO {
    @NotNull
    private Long orderId;

    @NotEmpty
    private String taskId;
}
