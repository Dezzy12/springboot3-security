package com.dezzy.springsecurity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {
    @Email
    @NotEmpty(message = "Email is mandatory")
    @NotBlank
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotBlank
    @Size(min = 8, message = "Password must be a minimum of 8 characters")
    private String password;
}
