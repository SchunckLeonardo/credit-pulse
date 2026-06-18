package com.creditpulse.creditpulseapi.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name,
        String email,
        String cpf,
        String phone,
        LocalDate birthDate,
        BigDecimal monthlyIncome,
        String profession,
        String employmentType
) {
}
