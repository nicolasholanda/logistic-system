package com.logistic.application.strategy;

import com.logistic.domain.model.TransportPrice;

import java.math.BigDecimal;

public interface FreightStrategy {

    BigDecimal calculate(BigDecimal weightKg, BigDecimal volumeCubicMeters, TransportPrice price);
}
