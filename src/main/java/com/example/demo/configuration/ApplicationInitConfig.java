package com.example.demo.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    @ConditionalOnProperty(name = "app.init-db", havingValue = "true", matchIfMissing = true)
    ApplicationRunner initApplicationRunner(UserRepository userRepository) {
        return args -> {
            log.info("init ApplicationRunner.............");
            // Create default roles in db if they don't exist
            if (!roleRepository.existsById(Role.USER.name())) {
                roleRepository.save(com.example.demo.entity.Role.builder()
                        .name(Role.USER.name())
                        .description("User role")
                        .build());
            }
            if (!roleRepository.existsById(Role.ADMIN.name())) {
                roleRepository.save(com.example.demo.entity.Role.builder()
                        .name(Role.ADMIN.name())
                        .description("Admin role")
                        .build());
            }

            if (userRepository.findByUserName("admin").isEmpty()) {
                com.example.demo.entity.Role adminRole = roleRepository
                        .findById(Role.ADMIN.name())
                        .orElseThrow(() -> new RuntimeException("Admin role not found"));

                var roles = new HashSet<com.example.demo.entity.Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .userName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin has been created with default password: admin, please change it");
            }
        };
    }
}
