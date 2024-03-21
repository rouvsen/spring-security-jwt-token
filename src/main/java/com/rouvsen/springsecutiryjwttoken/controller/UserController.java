package com.rouvsen.springsecutiryjwttoken.controller;

import com.rouvsen.springsecutiryjwttoken.dto.AuthRequest;
import com.rouvsen.springsecutiryjwttoken.dto.CreateUserRequest;
import com.rouvsen.springsecutiryjwttoken.model.User;
import com.rouvsen.springsecutiryjwttoken.service.JwtService;
import com.rouvsen.springsecutiryjwttoken.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Security JWT Token";
    }

    @PostMapping("/addUser")
    public User createUser(@RequestBody CreateUserRequest userRequest) {
        return userService.create(userRequest);
    }

    @GetMapping("/user")
    public String getUserString() {
        return "This end-point for only hasRole>USER";
    }

    @GetMapping("/admin")
    public String getAdminString() {
        return "This end-point for only hasRole>ADMIN";
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        }
        log.info("Authentication failed {},\nInvalid username or password > {}", authentication, authRequest.username());
        throw new BadCredentialsException("Invalid username or password, %s".formatted(authRequest.username()));
    }

}
