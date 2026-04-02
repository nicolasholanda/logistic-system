package com.logistic.web.dto;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.FreightQuote;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FreightCalculationResponse(
        Long id,
        TransportType transportType,
        BigDecimal weightKg,
        BigDecimal volumeCubicMeters,
        BigDecimal totalPrice,
        BigDecimal pricePerKgSnapshot,
        BigDecimal pricePerCubicMeterSnapshot,
        LocalDateTime createdAt
) {
    public static FreightCalculationResponse from(FreightQuote quote) {
        return new FreightCalculationResponse(
                quote.getId(),
                quote.getTransportType(),
                quote.getWeightKg(),
                quote.getVolumeCubicMeters(),
                quote.getTotalPrice(),
                quote.getPricePerKgSnapshot(),
                quote.getPricePerCubicMeterSnapshot(),
                quote.getCreatedAt()
        );
    }
}
