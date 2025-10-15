package com.br.edu.ufersa.prog_web.mec_tec.config;

import com.br.edu.ufersa.prog_web.mec_tec.user.model.entity.User;
import com.br.edu.ufersa.prog_web.mec_tec.user.model.entity.Role; // Supondo que você tenha um Enum de Role
import com.br.edu.ufersa.prog_web.mec_tec.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userService.existsByUsername("admin")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("felipepe@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(Role.ADMIN);

            // CORREÇÃO: Adicione esta linha para definir o valor
            adminUser.setRequiresPasswordChange(false);

            userService.save(adminUser);
            System.out.println("Usuário 'admin' padrão criado com sucesso.");
        }
    }
}