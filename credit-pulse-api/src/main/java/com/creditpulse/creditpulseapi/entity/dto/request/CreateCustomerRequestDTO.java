package com.creditpulse.creditpulseapi.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCustomerRequestDTO(
        @NotBlank(message = "Full name is required")
        @Size(max = 150, message = "Full name must be at most 150 characters")
        @JsonProperty("full_name")
        String fullName,

        @NotBlank(message = "CPF is required")
        @Pattern(regexp = "\\d{11}", message = "CPF must be exactly 11 digits")
        String cpf,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 150, message = "Email must be at most 150 characters")
        String email,

        @NotBlank(message = "Phone is required")
        @Size(max = 20, message = "Phone number must be at most 20 characters")
        String phone,

        @NotNull(message = "Monthly income is required")
        @DecimalMin(value = "0.01", message = "Monthly income must be at least 0.01")
        @Positive(message = "Monthly income must be positive")
        @JsonProperty("monthly_income")
        BigDecimal monthlyIncome,

        @NotBlank(message = "Occupation is required")
        @Size(max = 100, message = "Occupation must be at most 100 characters")
        String profession,

        @Size(max = 30, message = "Employment type must be at most 30 characters")
        @JsonProperty("employment_type")
        String employmentType
) {
}
