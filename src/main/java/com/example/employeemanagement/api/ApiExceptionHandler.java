package com.example.employeemanagement.api;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String, Object>> invalid(IllegalArgumentException ex) { return response(HttpStatus.BAD_REQUEST, ex.getMessage()); }
    @ExceptionHandler(java.util.NoSuchElementException.class)
    ResponseEntity<Map<String, Object>> missing(java.util.NoSuchElementException ex) { return response(HttpStatus.NOT_FOUND, ex.getMessage()); }
    private ResponseEntity<Map<String,Object>> response(HttpStatus status, String message) { return ResponseEntity.status(status).body(Map.of("timestamp", Instant.now().toString(), "status", status.value(), "message", message)); }
}
