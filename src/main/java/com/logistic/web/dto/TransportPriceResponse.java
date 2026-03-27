package com.logistic.web.dto;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.TransportPrice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransportPriceResponse(
        Long id,
        TransportType transportType,
        BigDecimal pricePerKg,
        BigDecimal pricePerCubicMeter,
        LocalDateTime updatedAt
) {
    public static TransportPriceResponse from(TransportPrice price) {
        return new TransportPriceResponse(
                price.getId(),
                price.getTransportType(),
                price.getPricePerKg(),
                price.getPricePerCubicMeter(),
                price.getUpdatedAt()
        );
    }
}
