package com.br.edu.ufersa.prog_web.mec_tec.peace.api.controller;

import com.br.edu.ufersa.prog_web.mec_tec.peace.api.dto.CreatePeaceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.peace.api.dto.ReturnPeaceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.peace.api.dto.UpdatePeaceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.peace.exception.PeaceAlreadyExist;
import com.br.edu.ufersa.prog_web.mec_tec.peace.exception.PeaceNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.peace.service.PeaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/peace")
public class PeaceController {
    private final PeaceService service;

    @Autowired
    public PeaceController(PeaceService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<ReturnPeaceDTO>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnPeaceDTO> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReturnPeaceDTO> post(@Valid @RequestBody CreatePeaceDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ReturnPeaceDTO> put(@Valid @RequestBody UpdatePeaceDTO dto) {
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(value = PeaceAlreadyExist.class)
    public ResponseEntity<Map<String, Object>> peaceAlreadyExist(PeaceAlreadyExist ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", HttpStatus.UNPROCESSABLE_ENTITY.value());
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = PeaceNotFound.class)
    public ResponseEntity<Map<String, Object>> peaceNotFound(PeaceNotFound ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", HttpStatus.NOT_FOUND.value());
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
