package com.br.edu.ufersa.prog_web.mec_tec.machine.api.controller;

import com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto.CreateMachineDTO;
import com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto.ReturnMachineDTO;
import com.br.edu.ufersa.prog_web.mec_tec.machine.api.dto.UpdateMachineDTO;
import com.br.edu.ufersa.prog_web.mec_tec.machine.exception.MachineNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.machine.model.entity.Machine;
import com.br.edu.ufersa.prog_web.mec_tec.machine.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/machines")
public class MachineController {

    private final MachineService service;

    @Autowired
    public MachineController(MachineService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Page<ReturnMachineDTO>> findAll(Pageable pageable){
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnMachineDTO> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(service.findByID(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReturnMachineDTO> save(@Valid @RequestBody CreateMachineDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnMachineDTO> update(@Valid @PathVariable UUID id, @RequestBody UpdateMachineDTO dto){
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
