package com.build.twitter_backend.dtos;
import jakarta.validation.constraints.NotBlank;

public record SigninDto(
        @NotBlank String username,
        @NotBlank String password) {
}
