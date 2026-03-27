package com.logistic.application.strategy;

import com.logistic.domain.model.TransportPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TruckFreightStrategy implements FreightStrategy {

    @Override
    public BigDecimal calculate(BigDecimal weightKg, BigDecimal volumeCubicMeters, TransportPrice price) {
        BigDecimal weightCost = weightKg.multiply(price.getPricePerKg()).multiply(new BigDecimal("1.30"));
        BigDecimal volumeCost = volumeCubicMeters.multiply(price.getPricePerCubicMeter());
        return weightCost.add(volumeCost);
    }
}
