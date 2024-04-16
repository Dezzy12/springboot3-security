package com.dezzy.springsecurity.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank
    private String firstname;
    @NotEmpty(message = "Lastname is mandatory")
    @NotBlank
    private String lastname;
    @Email
    @NotEmpty(message = "Email is mandatory")
    @NotBlank
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotBlank
    @Size(min = 8, message = "Password must be a minimum of 8 characters")
    private String password;
}
