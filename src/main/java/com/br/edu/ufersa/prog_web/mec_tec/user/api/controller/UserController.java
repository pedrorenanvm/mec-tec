package com.br.edu.ufersa.prog_web.mec_tec.user.api.controller;

import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.CreateUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.PasswordChangeUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.ReturnUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<ReturnUserDTO>> getAll(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(service.findAll(searchTerm,page,size), HttpStatus.OK);
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
    public ResponseEntity<Void> passwordChange(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody PasswordChangeUserDTO passwordChangeUserDTO) {
        service.passwordChange(userDetails.getUsername(),passwordChangeUserDTO);
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
}
