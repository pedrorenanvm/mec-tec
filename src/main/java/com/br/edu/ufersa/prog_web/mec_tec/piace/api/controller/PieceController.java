package com.br.edu.ufersa.prog_web.mec_tec.piace.api.controller;

import com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto.CreatePieceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto.ReturnPieceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.piace.api.dto.UpdatePieceDTO;
import com.br.edu.ufersa.prog_web.mec_tec.piace.exception.PieceAlreadyExist;
import com.br.edu.ufersa.prog_web.mec_tec.piace.exception.PieceNotFound;
import com.br.edu.ufersa.prog_web.mec_tec.piace.service.PieceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pieces")
public class PieceController {
    private final PieceService service;

    @Autowired
    public PieceController(PieceService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Page<ReturnPieceDTO>> getAll(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        String term = (searchTerm == null) ? "" : searchTerm.toLowerCase();
        return new ResponseEntity<>(service.findAll(term, page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnPieceDTO> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReturnPieceDTO> post(@Valid @RequestBody CreatePieceDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnPieceDTO> put(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePieceDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(value = PieceAlreadyExist.class)
    public ResponseEntity<Map<String, Object>> peaceAlreadyExist(PieceAlreadyExist ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", HttpStatus.UNPROCESSABLE_ENTITY.value());
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = PieceNotFound.class)
    public ResponseEntity<Map<String, Object>> peaceNotFound(PieceNotFound ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", HttpStatus.NOT_FOUND.value());
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
