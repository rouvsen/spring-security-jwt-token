package com.rouvsen.springsecutiryjwttoken.service;

import com.rouvsen.springsecutiryjwttoken.dto.CreateUserRequest;
import com.rouvsen.springsecutiryjwttoken.model.Role;
import com.rouvsen.springsecutiryjwttoken.model.User;
import com.rouvsen.springsecutiryjwttoken.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User create(CreateUserRequest request) {
        return userRepository.save(
                User.builder()
                        .name(request.name())
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .authorities(Set.of(Role.ROLE_USER))
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .isEnabled(true)
                        .build()
        );
    }
}
