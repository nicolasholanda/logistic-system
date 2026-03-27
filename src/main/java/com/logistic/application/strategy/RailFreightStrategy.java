package com.logistic.application.strategy;

import com.logistic.domain.model.TransportPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RailFreightStrategy implements FreightStrategy {

    private static final BigDecimal SURCHARGE = new BigDecimal("5.00");

    @Override
    public BigDecimal calculate(BigDecimal weightKg, BigDecimal volumeCubicMeters, TransportPrice price) {
        BigDecimal weightCost = weightKg.multiply(price.getPricePerKg());
        BigDecimal volumeCost = volumeCubicMeters.multiply(price.getPricePerCubicMeter());
        return weightCost.add(volumeCost).add(SURCHARGE);
    }
}
