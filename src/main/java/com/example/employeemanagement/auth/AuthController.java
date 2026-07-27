package com.example.employeemanagement.auth;

import com.example.employeemanagement.security.JwtService;
import com.example.employeemanagement.user.AppUser;
import com.example.employeemanagement.user.AppUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/v1/auth")
public class AuthController {
    private final AppUserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
    public AuthController(AppUserRepository users, PasswordEncoder encoder, JwtService jwt) { this.users=users; this.encoder=encoder; this.jwt=jwt; }
    @PostMapping("/login") @Operation(summary="Authenticate and receive a JWT")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        AppUser user = users.findByUsername(request.username()).filter(u -> u.isEnabled() && encoder.matches(request.password(), u.getPasswordHash())).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        return ResponseEntity.ok(new LoginResponse(jwt.generate(user), "Bearer", user.getRole().name()));
    }
    public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
    public record LoginResponse(String accessToken, String tokenType, String role) {}
}
