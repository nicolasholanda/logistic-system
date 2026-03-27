package com.logistic.application.strategy;

import com.logistic.domain.model.TransportPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BoatFreightStrategy implements FreightStrategy {

    @Override
    public BigDecimal calculate(BigDecimal weightKg, BigDecimal volumeCubicMeters, TransportPrice price) {
        BigDecimal weightCost = weightKg.multiply(price.getPricePerKg());
        BigDecimal volumeCost = volumeCubicMeters.multiply(price.getPricePerCubicMeter()).multiply(new BigDecimal("1.20"));
        return weightCost.add(volumeCost);
    }
}
