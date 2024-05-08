package com.dezzy.springsecurity.controller;

import com.dezzy.springsecurity.dto.reponse.LoginResponse;
import com.dezzy.springsecurity.dto.request.LoginRequest;
import com.dezzy.springsecurity.dto.request.RegistrationRequest;
import com.dezzy.springsecurity.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestBody @Valid RegistrationRequest request
            ) throws MessagingException {
        service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
            ){
        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping("/activate-account")
    public void confirm (@RequestParam String token) throws MessagingException { service.activateAccount(token); }
}
