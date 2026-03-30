package com.logistic.application.strategy;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.TransportPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BoatFreightStrategyTest {

    private BoatFreightStrategy strategy;
    private TransportPrice price;

    @BeforeEach
    void setUp() {
        strategy = new BoatFreightStrategy();
        price = TransportPrice.builder()
                .transportType(TransportType.BOAT)
                .pricePerKg(new BigDecimal("0.0120"))
                .pricePerCubicMeter(new BigDecimal("15.0000"))
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
        // weightCost = 100 * 0.0120 = 1.20
        // volumeCost = 2 * 15.0000 * 1.20 = 36.00
        // total = 37.20
        assertThat(result).isEqualByComparingTo("37.20");
    }

    @Test
    void shouldWeighVolumeMoreThanWeight() {
        BigDecimal highVolume = strategy.calculate(
                new BigDecimal("10"),
                new BigDecimal("10"),
                price
        );
        BigDecimal highWeight = strategy.calculate(
                new BigDecimal("10000"),
                new BigDecimal("0.001"),
                price
        );
        assertThat(highVolume).isGreaterThan(highWeight);
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
