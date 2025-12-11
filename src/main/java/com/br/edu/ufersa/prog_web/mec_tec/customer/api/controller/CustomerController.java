package com.br.edu.ufersa.prog_web.mec_tec.customer.api.controller;

import com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto.CreateCustomerDTO;
import com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto.ReturnCustomerDTO;
import com.br.edu.ufersa.prog_web.mec_tec.customer.api.dto.UpdateCustomerDTO;
import com.br.edu.ufersa.prog_web.mec_tec.customer.exception.CustomerAlreadyExistsException;
import com.br.edu.ufersa.prog_web.mec_tec.customer.exception.CustomerNotFoundException;
import com.br.edu.ufersa.prog_web.mec_tec.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Page<ReturnCustomerDTO>> getAll(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(service.findAll(searchTerm,page,size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnCustomerDTO> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReturnCustomerDTO> post(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
        return new ResponseEntity<>(service.create(createCustomerDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ReturnCustomerDTO> put(@Valid @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        return new ResponseEntity<>(service.update(updateCustomerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
