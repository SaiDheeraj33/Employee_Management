package com.example.employeemanagement.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/v1/users") @SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final AppUserRepository users; private final PasswordEncoder encoder;
    public UserController(AppUserRepository users, PasswordEncoder encoder) { this.users=users; this.encoder=encoder; }
    @GetMapping public List<UserResponse> all() { return users.findAll().stream().map(UserResponse::from).toList(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public UserResponse create(@Valid @RequestBody UserRequest request) {
        if (users.findByUsername(request.username()).isPresent()) throw new IllegalArgumentException("Username already exists");
        return UserResponse.from(users.save(new AppUser(request.username(), encoder.encode(request.password()), request.role())));
    }
    public record UserRequest(@NotBlank String username, @NotBlank String password, @NotNull Role role) {}
    public record UserResponse(UUID id, String username, Role role, boolean enabled) { static UserResponse from(AppUser u) { return new UserResponse(u.getId(),u.getUsername(),u.getRole(),u.isEnabled()); } }
}
