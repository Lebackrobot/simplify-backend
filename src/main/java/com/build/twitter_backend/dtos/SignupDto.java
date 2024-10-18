package com.build.twitter_backend.dtos;
import jakarta.validation.constraints.NotBlank;

public record SignupDto(
        @NotBlank String username,
        @NotBlank String password) {
}
