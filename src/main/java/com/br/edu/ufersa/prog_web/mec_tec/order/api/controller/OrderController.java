package com.br.edu.ufersa.prog_web.mec_tec.order.api.controller;

import com.br.edu.ufersa.prog_web.mec_tec.order.api.dto.CreateOrderDTO;
import com.br.edu.ufersa.prog_web.mec_tec.order.api.dto.ReturnAllOrderDTO;
import com.br.edu.ufersa.prog_web.mec_tec.order.api.dto.ReturnOrderDTO;
import com.br.edu.ufersa.prog_web.mec_tec.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<ReturnOrderDTO> create(@RequestBody CreateOrderDTO dto){
        return  ResponseEntity.ok(service.create(dto));
    }

    @GetMapping()
    public ResponseEntity<Page<ReturnAllOrderDTO>> getAllOrders(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(service.findAllOrders(page, size), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReturnOrderDTO> getOrder(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
