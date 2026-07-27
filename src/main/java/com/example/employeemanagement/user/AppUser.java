package com.example.employeemanagement.user;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "app_users")
public class AppUser {
    @Id private UUID id = UUID.randomUUID();
    @Column(nullable = false, unique = true) private String username;
    @Column(name = "password_hash", nullable = false) private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Role role;
    @Column(nullable = false) private boolean enabled = true;
    @Column(name = "created_at", nullable = false) private Instant createdAt = Instant.now();
    protected AppUser() {}
    public AppUser(String username, String passwordHash, Role role) { this.username = username; this.passwordHash = passwordHash; this.role = role; }
    public UUID getId() { return id; } public String getUsername() { return username; } public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; } public boolean isEnabled() { return enabled; }
}
