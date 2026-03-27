package com.logistic.web.dto;

import com.logistic.domain.enums.TransportType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FreightCalculationRequest(

        @NotNull
        TransportType transportType,

        @NotNull
        @DecimalMin("0.001")
        BigDecimal weightKg,

        @NotNull
        @DecimalMin("0.001")
        BigDecimal volumeCubicMeters
) {}
