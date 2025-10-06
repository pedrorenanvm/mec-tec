package com.br.edu.ufersa.prog_web.mec_tec.user.api.controller;

import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.CreateUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.PasswordChangeUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.ReturnUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserAlreadyExistsException;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserInvalidPasswordException;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserNotFoundException;
import com.br.edu.ufersa.prog_web.mec_tec.user.service.UserService;
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
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<ReturnUserDTO>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnUserDTO> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReturnUserDTO> post(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return new ResponseEntity<>(service.create(createUserDTO), HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<Void> passwordChange(@Valid @RequestBody PasswordChangeUserDTO passwordChangeUserDTO) {
        service.passwordChange(passwordChangeUserDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> passwordReset(@PathVariable UUID id) {
        service.passwordReset(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", HttpStatus.UNPROCESSABLE_ENTITY.value());
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", HttpStatus.NOT_FOUND.value());
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserInvalidPasswordException.class)
    public ResponseEntity<Map<String, Object>> handleUserInvalidPasswordException(UserInvalidPasswordException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", HttpStatus.UNAUTHORIZED.value());
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }
}
