package com.example.employeemanagement.security;

import com.example.employeemanagement.user.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService; private final AppUserRepository users;
    public JwtAuthenticationFilter(JwtService jwtService, AppUserRepository users) { this.jwtService = jwtService; this.users = users; }
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) { chain.doFilter(request, response); return; }
        try {
            String username = jwtService.username(header.substring(7));
            users.findByUsername(username).filter(user -> user.isEnabled() && SecurityContextHolder.getContext().getAuthentication() == null).ifPresent(user -> {
                var auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, java.util.List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); SecurityContextHolder.getContext().setAuthentication(auth);
            });
        } catch (RuntimeException ignored) { }
        chain.doFilter(request, response);
    }
}
