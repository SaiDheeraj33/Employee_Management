package com.example.employeemanagement.employee;

import com.example.employeemanagement.department.Department;
import com.example.employeemanagement.department.DepartmentRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/v1/employees") @SecurityRequirement(name = "bearerAuth")
public class EmployeeController {
    private final EmployeeRepository employees; private final DepartmentRepository departments;
    public EmployeeController(EmployeeRepository employees, DepartmentRepository departments) { this.employees=employees; this.departments=departments; }
    @GetMapping @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')") public List<EmployeeResponse> all() { return employees.findAll().stream().map(EmployeeResponse::from).toList(); }
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')") public EmployeeResponse one(@PathVariable UUID id) { return EmployeeResponse.from(employee(id)); }
    @PostMapping @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')") public EmployeeResponse create(@Valid @RequestBody EmployeeRequest r) { if(employees.existsByEmployeeCode(r.employeeCode()) || employees.existsByEmail(r.email())) throw new IllegalArgumentException("Employee code or email already exists"); return EmployeeResponse.from(employees.save(new Employee(r.employeeCode(),r.firstName(),r.lastName(),r.email(),r.phone(),r.jobTitle(),r.hireDate(),department(r.departmentId()),r.status()))); }
    @PutMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')") public EmployeeResponse update(@PathVariable UUID id, @Valid @RequestBody EmployeeRequest r) { Employee e=employee(id); e.update(r.firstName(),r.lastName(),r.email(),r.phone(),r.jobTitle(),r.hireDate(),department(r.departmentId()),r.status()); return EmployeeResponse.from(employees.save(e)); }
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public void deactivate(@PathVariable UUID id) { Employee e=employee(id); e.deactivate(); employees.save(e); }
    private Employee employee(UUID id) { return employees.findById(id).orElseThrow(()->new NoSuchElementException("Employee not found")); }
    private Department department(UUID id) { return departments.findById(id).orElseThrow(()->new NoSuchElementException("Department not found")); }
    public record EmployeeRequest(@NotBlank String employeeCode, @NotBlank String firstName, @NotBlank String lastName, @Email @NotBlank String email, String phone, @NotBlank String jobTitle, @NotNull LocalDate hireDate, @NotNull UUID departmentId, @NotNull EmploymentStatus status) {}
    public record EmployeeResponse(UUID id, String employeeCode, String firstName, String lastName, String email, String phone, String jobTitle, LocalDate hireDate, EmploymentStatus status, UUID departmentId, String departmentName) { static EmployeeResponse from(Employee e) { return new EmployeeResponse(e.getId(),e.getEmployeeCode(),e.getFirstName(),e.getLastName(),e.getEmail(),e.getPhone(),e.getJobTitle(),e.getHireDate(),e.getStatus(),e.getDepartment().getId(),e.getDepartment().getName()); } }
}
