package com.rouvsen.springsecutiryjwttoken.dto;

public record AuthRequest(
        String username, String password
) {
}
