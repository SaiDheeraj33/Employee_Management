package com.example.employeemanagement.employee;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @Override @EntityGraph(attributePaths = "department")
    List<Employee> findAll();
    @Override @EntityGraph(attributePaths = "department")
    Optional<Employee> findById(UUID id);
    boolean existsByEmployeeCode(String employeeCode);
    boolean existsByEmail(String email);
}
