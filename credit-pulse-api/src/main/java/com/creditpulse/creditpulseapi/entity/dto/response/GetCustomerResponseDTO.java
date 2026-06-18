package com.creditpulse.creditpulseapi.entity.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GetCustomerResponseDTO(
        UUID id,
        String name,
        String email,
        String cpf,
        String phone,
        String birthDate,
        BigDecimal monthlyIncome,
        String profession,
        String employmentType,
        String status,
        String blockedReason,
        String createdAt,
        String updatedAt
) {
}
