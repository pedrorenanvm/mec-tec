package com.br.edu.ufersa.prog_web.mec_tec.order.api.dto;

import com.br.edu.ufersa.prog_web.mec_tec.order.api.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
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
public class ReturnAllOrderDTO {
    @NotBlank
    private Long orderId;

    @NotBlank
    private String description;

    private OrderStatus status;

    private Date createdAt;
}
