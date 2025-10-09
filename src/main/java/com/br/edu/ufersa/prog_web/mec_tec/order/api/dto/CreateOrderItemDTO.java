package com.br.edu.ufersa.prog_web.mec_tec.order.api.dto;

import com.br.edu.ufersa.prog_web.mec_tec.order.model.entity.Order;
import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.Task;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemDTO {
    @NotNull
    private Order order;

    @NotNull
    private Task task;

    private Date createdAt;
}
