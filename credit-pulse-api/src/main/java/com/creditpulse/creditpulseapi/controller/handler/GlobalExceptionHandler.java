package com.creditpulse.creditpulseapi.controller.handler;

import com.creditpulse.creditpulseapi.entity.dto.ApiErrorDTO;
import com.creditpulse.creditpulseapi.entity.exception.CustomerAlreadyExistsWithCpfOrEmailException;
import com.creditpulse.creditpulseapi.entity.exception.CustomerMustBeGreaterAgeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsWithCpfOrEmailException.class)
    ResponseEntity<ApiErrorDTO> handleCustomerAlreadyExistsWithCpfOrEmailException(CustomerAlreadyExistsWithCpfOrEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiErrorDTO(e.getMessage(), e.getCode())
        );
    }

    @ExceptionHandler(CustomerMustBeGreaterAgeException.class)
    ResponseEntity<ApiErrorDTO> handleCustomerMustBeGreaterAge(CustomerMustBeGreaterAgeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(e.getMessage(), e.getCode())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(errors.getFirst().getDefaultMessage(), "method.argument.not.valid")
        );
    }

}
