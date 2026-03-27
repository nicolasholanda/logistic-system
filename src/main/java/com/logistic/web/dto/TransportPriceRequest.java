package com.logistic.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransportPriceRequest(

        @NotNull
        @DecimalMin("0.0001")
        BigDecimal pricePerKg,

        @NotNull
        @DecimalMin("0.0001")
        BigDecimal pricePerCubicMeter
) {}
