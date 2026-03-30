package com.logistic.application.service;

import com.logistic.application.strategy.FreightStrategy;
import com.logistic.application.strategy.FreightStrategyResolver;
import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.FreightQuote;
import com.logistic.domain.model.TransportPrice;
import com.logistic.domain.repository.FreightQuoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FreightServiceTest {

    @Mock
    private FreightStrategyResolver strategyResolver;

    @Mock
    private TransportPriceService transportPriceService;

    @Mock
    private FreightQuoteRepository freightQuoteRepository;

    @Mock
    private FreightStrategy freightStrategy;

    @InjectMocks
    private FreightService freightService;

    @Test
    void shouldResolveStrategyAndPersistQuote() {
        TransportPrice price = TransportPrice.builder()
                .transportType(TransportType.TRUCK)
                .pricePerKg(new BigDecimal("0.0850"))
                .pricePerCubicMeter(new BigDecimal("45.0000"))
                .updatedAt(LocalDateTime.now())
                .build();

        BigDecimal weight = new BigDecimal("100");
        BigDecimal volume = new BigDecimal("2");
        BigDecimal expectedTotal = new BigDecimal("101.05");

        when(transportPriceService.findByTransportType(TransportType.TRUCK)).thenReturn(price);
        when(strategyResolver.resolve(TransportType.TRUCK)).thenReturn(freightStrategy);
        when(freightStrategy.calculate(weight, volume, price)).thenReturn(expectedTotal);
        when(freightQuoteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        FreightQuote result = freightService.calculate(TransportType.TRUCK, weight, volume);

        assertThat(result.getTransportType()).isEqualTo(TransportType.TRUCK);
        assertThat(result.getWeightKg()).isEqualTo(weight);
        assertThat(result.getVolumeCubicMeters()).isEqualTo(volume);
        assertThat(result.getTotalPrice()).isEqualByComparingTo(expectedTotal);
    }

    @Test
    void shouldSaveQuoteWithCorrectFields() {
        TransportPrice price = TransportPrice.builder()
                .transportType(TransportType.BOAT)
                .pricePerKg(new BigDecimal("0.0120"))
                .pricePerCubicMeter(new BigDecimal("15.0000"))
                .updatedAt(LocalDateTime.now())
                .build();

        BigDecimal weight = new BigDecimal("50");
        BigDecimal volume = new BigDecimal("3");
        BigDecimal total = new BigDecimal("55.00");

        when(transportPriceService.findByTransportType(TransportType.BOAT)).thenReturn(price);
        when(strategyResolver.resolve(TransportType.BOAT)).thenReturn(freightStrategy);
        when(freightStrategy.calculate(weight, volume, price)).thenReturn(total);
        when(freightQuoteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        freightService.calculate(TransportType.BOAT, weight, volume);

        ArgumentCaptor<FreightQuote> captor = ArgumentCaptor.forClass(FreightQuote.class);
        verify(freightQuoteRepository).save(captor.capture());

        FreightQuote saved = captor.getValue();
        assertThat(saved.getTransportType()).isEqualTo(TransportType.BOAT);
        assertThat(saved.getWeightKg()).isEqualTo(weight);
        assertThat(saved.getVolumeCubicMeters()).isEqualTo(volume);
        assertThat(saved.getTotalPrice()).isEqualByComparingTo(total);
    }

    @Test
    void shouldDelegateToResolvedStrategy() {
        when(transportPriceService.findByTransportType(any())).thenReturn(TransportPrice.builder()
                .transportType(TransportType.RAIL)
                .pricePerKg(BigDecimal.ONE)
                .pricePerCubicMeter(BigDecimal.ONE)
                .updatedAt(LocalDateTime.now())
                .build());
        when(strategyResolver.resolve(TransportType.RAIL)).thenReturn(freightStrategy);
        when(freightStrategy.calculate(any(), any(), any())).thenReturn(BigDecimal.TEN);
        when(freightQuoteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        freightService.calculate(TransportType.RAIL, BigDecimal.ONE, BigDecimal.ONE);

        verify(strategyResolver).resolve(TransportType.RAIL);
        verify(freightStrategy).calculate(any(), any(), any());
    }
}
