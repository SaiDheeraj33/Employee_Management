package com.example.employeemanagement.department;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/v1/departments") @SecurityRequirement(name = "bearerAuth")
public class DepartmentController {
    private final DepartmentRepository departments;
    public DepartmentController(DepartmentRepository departments) { this.departments=departments; }
    @GetMapping @PreAuthorize("hasAnyRole('ADMIN','HR_MANAGER')") public List<DepartmentResponse> all() { return departments.findAll().stream().map(DepartmentResponse::from).toList(); }
    @PostMapping @PreAuthorize("hasRole('ADMIN')") public DepartmentResponse create(@Valid @RequestBody DepartmentRequest r) { if (departments.existsByNameIgnoreCase(r.name())) throw new IllegalArgumentException("Department name already exists"); return DepartmentResponse.from(departments.save(new Department(r.name(), r.description()))); }
    @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public DepartmentResponse update(@PathVariable UUID id, @Valid @RequestBody DepartmentRequest r) { Department d=departments.findById(id).orElseThrow(()->new NoSuchElementException("Department not found")); d.update(r.name(),r.description()); return DepartmentResponse.from(departments.save(d)); }
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public void delete(@PathVariable UUID id) { departments.delete(departments.findById(id).orElseThrow(()->new NoSuchElementException("Department not found"))); }
    public record DepartmentRequest(@NotBlank String name, String description) {}
    public record DepartmentResponse(UUID id, String name, String description) { static DepartmentResponse from(Department d) { return new DepartmentResponse(d.getId(),d.getName(),d.getDescription()); } }
}
