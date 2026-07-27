package com.example.employeemanagement.employee;

import com.example.employeemanagement.department.Department;
import com.example.employeemanagement.user.AppUser;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity @Table(name = "employees")
public class Employee {
    @Id private UUID id = UUID.randomUUID();
    @Column(name = "employee_code", nullable = false, unique = true) private String employeeCode;
    @Column(name = "first_name", nullable = false) private String firstName;
    @Column(name = "last_name", nullable = false) private String lastName;
    @Column(nullable = false, unique = true) private String email;
    private String phone;
    @Column(name = "job_title", nullable = false) private String jobTitle;
    @Column(name = "hire_date", nullable = false) private LocalDate hireDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private EmploymentStatus status = EmploymentStatus.ACTIVE;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "department_id") private Department department;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") private AppUser user;
    @Column(name = "created_at", nullable = false) private Instant createdAt = Instant.now();
    @Column(name = "updated_at", nullable = false) private Instant updatedAt = Instant.now();
    protected Employee() {}
    public Employee(String code, String firstName, String lastName, String email, String phone, String title, LocalDate hireDate, Department department) { this.employeeCode=code; this.firstName=firstName; this.lastName=lastName; this.email=email; this.phone=phone; this.jobTitle=title; this.hireDate=hireDate; this.department=department; }
    public UUID getId(){return id;} public String getEmployeeCode(){return employeeCode;} public String getFirstName(){return firstName;} public String getLastName(){return lastName;} public String getEmail(){return email;} public String getPhone(){return phone;} public String getJobTitle(){return jobTitle;} public LocalDate getHireDate(){return hireDate;} public EmploymentStatus getStatus(){return status;} public Department getDepartment(){return department;}
    public void update(String firstName, String lastName, String email, String phone, String title, LocalDate hireDate, Department department, EmploymentStatus status) { this.firstName=firstName; this.lastName=lastName; this.email=email; this.phone=phone; this.jobTitle=title; this.hireDate=hireDate; this.department=department; this.status=status; this.updatedAt=Instant.now(); }
    public void deactivate() { this.status=EmploymentStatus.INACTIVE; this.updatedAt=Instant.now(); }
}
