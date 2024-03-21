package com.rouvsen.springsecutiryjwttoken.dto;

import com.rouvsen.springsecutiryjwttoken.model.Role;
import lombok.Builder;

import java.util.Set;


@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
) {
}
