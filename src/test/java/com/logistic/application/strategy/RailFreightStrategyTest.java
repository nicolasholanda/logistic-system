package com.logistic.application.strategy;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.TransportPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RailFreightStrategyTest {

    private RailFreightStrategy strategy;
    private TransportPrice price;

    @BeforeEach
    void setUp() {
        strategy = new RailFreightStrategy();
        price = TransportPrice.builder()
                .transportType(TransportType.RAIL)
                .pricePerKg(new BigDecimal("0.0350"))
                .pricePerCubicMeter(new BigDecimal("25.0000"))
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
        // weightCost = 100 * 0.0350 = 3.50
        // volumeCost = 2 * 25.0000 = 50.00
        // surcharge = 5.00
        // total = 58.50
        assertThat(result).isEqualByComparingTo("58.50");
    }

    @Test
    void shouldAlwaysIncludeFlatSurcharge() {
        BigDecimal result = strategy.calculate(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                price
        );
        assertThat(result).isEqualByComparingTo("5.00");
    }

    @Test
    void shouldBeCheaperThanTruckForSameInputs() {
        TruckFreightStrategy truck = new TruckFreightStrategy();
        TransportPrice truckPrice = TransportPrice.builder()
                .transportType(TransportType.TRUCK)
                .pricePerKg(new BigDecimal("0.0850"))
                .pricePerCubicMeter(new BigDecimal("45.0000"))
                .updatedAt(LocalDateTime.now())
                .build();

        BigDecimal rail = strategy.calculate(new BigDecimal("100"), new BigDecimal("2"), price);
        BigDecimal truckResult = truck.calculate(new BigDecimal("100"), new BigDecimal("2"), truckPrice);

        assertThat(rail).isLessThan(truckResult);
    }
}
