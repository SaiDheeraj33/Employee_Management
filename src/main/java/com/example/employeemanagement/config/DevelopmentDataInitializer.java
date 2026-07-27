package com.example.employeemanagement.config;

import com.example.employeemanagement.user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DevelopmentDataInitializer {
    @Bean CommandLineRunner createAdmin(AppUserRepository users, PasswordEncoder encoder) { return args -> { if (users.findByUsername("admin").isEmpty()) users.save(new AppUser("admin", encoder.encode("Admin@123"), Role.ADMIN)); }; }
}
