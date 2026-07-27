package com.example.employeemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class EmployeeManagementApplication {
    public static void main(String[] args) {
        // PostgreSQL 18 does not accept the Windows legacy zone ID "Asia/Calcutta".
        // Use a portable, unambiguous application/database timezone instead.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }
}
