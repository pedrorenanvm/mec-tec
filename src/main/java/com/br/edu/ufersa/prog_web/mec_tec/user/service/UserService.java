package com.br.edu.ufersa.prog_web.mec_tec.user.service;

import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.CreateUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.PasswordChangeUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.api.dto.ReturnUserDTO;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserAlreadyExistsException;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserInvalidPasswordException;
import com.br.edu.ufersa.prog_web.mec_tec.user.exception.UserNotFoundException;
import com.br.edu.ufersa.prog_web.mec_tec.user.model.entity.Role;
import com.br.edu.ufersa.prog_web.mec_tec.user.model.entity.User;
import com.br.edu.ufersa.prog_web.mec_tec.user.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Page<ReturnUserDTO> findAll(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<User> pageUser;

        if (searchTerm == null || searchTerm.isBlank()) {
            pageUser = repository.findAll(pageable);
        } else {
            pageUser = repository.findAll(searchTerm,pageable);
        }

        return pageUser.map(u -> modelMapper.map(u, ReturnUserDTO.class));
    }

    public ReturnUserDTO findById(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found."));
        return modelMapper.map(user, ReturnUserDTO.class);
    }

    public ReturnUserDTO create(CreateUserDTO dto) {
        if (repository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this username already exists.");
        }

        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this email already exists.");
        }

        User user = modelMapper.map(dto, User.class);
        user.setRole(Role.USER);
        user.setPassword("senha123");
        user.setRequiresPasswordChange(true);

        User createUser = repository.save(user);

        return modelMapper.map(createUser, ReturnUserDTO.class);
    }

    public void passwordReset(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found."));

        user.setPassword("senha123");
        user.setRequiresPasswordChange(true);

        repository.save(user);
    }

    public void passwordChange(PasswordChangeUserDTO dto) {
        User user = repository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException("User not found."));

        if (!user.getPassword().equals(dto.getOldPassword())) {
            throw new UserInvalidPasswordException("Old password is incorrect.");
        }

        user.setPassword(dto.getNewPassword());
        user.setRequiresPasswordChange(false);

        repository.save(user);
    }

    public void delete(UUID id) {
        User user = new User();
        user.setId(id);
        repository.delete(user);
    }

    // Em UserService
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + username));
    }
    public boolean existsByUsername(String username) {
        return repository.findByUsername(username).isPresent();
    }
    public void save(User user) {
        repository.save(user);
    }

}
