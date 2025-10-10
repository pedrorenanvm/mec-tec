package com.br.edu.ufersa.prog_web.mec_tec.task.api.controller;

import com.br.edu.ufersa.prog_web.mec_tec.task.api.dto.CreateTaskDTO;
import com.br.edu.ufersa.prog_web.mec_tec.task.api.dto.ReturnTaskDTO;
import com.br.edu.ufersa.prog_web.mec_tec.task.api.dto.UpdateTaskDTO;
import com.br.edu.ufersa.prog_web.mec_tec.task.model.entity.Task;
import com.br.edu.ufersa.prog_web.mec_tec.task.service.TaskService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<ReturnTaskDTO>> findAll(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size){
        String term = (searchTerm == null || searchTerm.isBlank()) ? "" : searchTerm.toLowerCase();
        return new ResponseEntity<>(taskService.findAll(term, page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnTaskDTO> findById(@PathVariable UUID id){
        return new ResponseEntity<>(taskService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReturnTaskDTO> save(@Valid @RequestBody CreateTaskDTO dto){
        return new ResponseEntity<>(taskService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnTaskDTO> update( @Valid @PathVariable UUID id, @RequestBody UpdateTaskDTO dto) {
        return new ResponseEntity<>(taskService.update(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
