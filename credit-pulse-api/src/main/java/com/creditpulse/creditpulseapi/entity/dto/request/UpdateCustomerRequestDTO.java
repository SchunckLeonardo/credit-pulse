package com.creditpulse.creditpulseapi.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCustomerRequestDTO(
        @Size(max = 150, message = "Full name must be at most 150 characters")
        @JsonProperty("full_name")
        String fullName,

        @Size(max = 20, message = "Phone number must be at most 20 characters")
        String phone,

        @Past(message = "Birth date must be in the past")
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        @Size(max = 100, message = "Occupation must be at most 100 characters")
        String profession,

        @Size(max = 30, message = "Employment type must be at most 30 characters")
        @JsonProperty("employment_type")
        String employmentType
) {
}
