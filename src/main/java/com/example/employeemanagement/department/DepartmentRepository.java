package com.example.employeemanagement.department;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DepartmentRepository extends JpaRepository<Department, UUID> { boolean existsByNameIgnoreCase(String name); }
