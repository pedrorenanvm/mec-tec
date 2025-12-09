package com.br.edu.ufersa.prog_web.mec_tec.order.api.dto;

import com.br.edu.ufersa.prog_web.mec_tec.order.api.enums.OrderStatus;
import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderDTO {
    @NotNull
    private Long orderId;

    @NotBlank
    private String description;

    private Instant createdAt;

    private OrderStatus status;

    private List<Task> tasks;
}
