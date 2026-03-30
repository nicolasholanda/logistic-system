package com.logistic.application.strategy;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.TransportPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TruckFreightStrategyTest {

    private TruckFreightStrategy strategy;
    private TransportPrice price;

    @BeforeEach
    void setUp() {
        strategy = new TruckFreightStrategy();
        price = TransportPrice.builder()
                .transportType(TransportType.TRUCK)
                .pricePerKg(new BigDecimal("0.0850"))
                .pricePerCubicMeter(new BigDecimal("45.0000"))
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCalculateCorrectly() {
        BigDecimal result = strategy.calculate(
                new BigDecimal("100"),
                new BigDecimal("2"),
                price
        );
        // weightCost = 100 * 0.0850 * 1.30 = 11.05
        // volumeCost = 2 * 45.0000 = 90.00
        // total = 101.05
        assertThat(result).isEqualByComparingTo("101.05");
    }

    @Test
    void shouldWeighWeightMoreThanVolume() {
        BigDecimal highWeight = strategy.calculate(
                new BigDecimal("10000"),
                new BigDecimal("0.001"),
                price
        );
        BigDecimal highVolume = strategy.calculate(
                new BigDecimal("0.001"),
                new BigDecimal("10000"),
                price
        );
        assertThat(highWeight).isGreaterThan(highVolume);
    }

    @Test
    void shouldReturnZeroForZeroInputs() {
        BigDecimal result = strategy.calculate(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                price
        );
        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
