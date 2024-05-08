package com.dezzy.springsecurity.exception;

import com.dezzy.springsecurity.data.enums.ErrorCodes;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.dezzy.springsecurity.data.enums.ErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiException {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiResponse> handleException(LockedException exp){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ApiResponse.builder()
                                .errorCode(ACCOUNT_LOCKED.getCode())
                                .errorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse> handleException(DisabledException exp){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ApiResponse.builder()
                                .errorCode(ACCOUNT_DISABLED.getCode())
                                .errorDescription(ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleException(BadCredentialsException exp){
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ApiResponse.builder()
                                .errorCode(BAD_CREDENTIALS.getCode())
                                .errorDescription(BAD_CREDENTIALS.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ApiResponse> handleException(MessagingException exp){
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleException(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(err -> {
                    var errorMessages = err.getDefaultMessage();
                    errors.add(errorMessages);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ApiResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exp){
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.builder()
                                .errorDescription("Internal server error, working on it!!")
                                .error(exp.getMessage())
                                .build()
                );
    }
}
