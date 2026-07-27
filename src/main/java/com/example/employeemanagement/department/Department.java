package com.example.employeemanagement.department;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "departments")
public class Department {
    @Id private UUID id = UUID.randomUUID();
    @Column(nullable = false, unique = true) private String name;
    private String description;
    @Column(name = "created_at", nullable = false) private Instant createdAt = Instant.now();
    @Column(name = "updated_at", nullable = false) private Instant updatedAt = Instant.now();
    protected Department() {}
    public Department(String name, String description) { this.name = name; this.description = description; }
    public UUID getId() { return id; } public String getName() { return name; } public String getDescription() { return description; }
    public void update(String name, String description) { this.name = name; this.description = description; this.updatedAt = Instant.now(); }
}
