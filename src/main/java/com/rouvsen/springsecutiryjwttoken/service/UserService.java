package com.rouvsen.springsecutiryjwttoken.service;

import com.rouvsen.springsecutiryjwttoken.dto.CreateUserRequest;
import com.rouvsen.springsecutiryjwttoken.model.User;
import com.rouvsen.springsecutiryjwttoken.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User create(CreateUserRequest request) {
        return userRepository.save(
                User.builder()
                        .name(request.name())
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .authorities(request.authorities())
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .isEnabled(true)
                        .build()
        );
    }
}
