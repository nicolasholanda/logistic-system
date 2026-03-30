package com.logistic.application.service;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.TransportPrice;
import com.logistic.domain.repository.TransportPriceRepository;
import com.logistic.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransportPriceServiceTest {

    @Mock
    private TransportPriceRepository repository;

    @InjectMocks
    private TransportPriceService service;

    @Test
    void shouldReturnAllPrices() {
        List<TransportPrice> prices = List.of(
                buildPrice(TransportType.BOAT, "0.0120", "15.0000"),
                buildPrice(TransportType.TRUCK, "0.0850", "45.0000"),
                buildPrice(TransportType.RAIL, "0.0350", "25.0000")
        );
        when(repository.findAll()).thenReturn(prices);

        List<TransportPrice> result = service.findAll();

        assertThat(result).hasSize(3);
        verify(repository).findAll();
    }

    @Test
    void shouldReturnPriceByTransportType() {
        TransportPrice price = buildPrice(TransportType.BOAT, "0.0120", "15.0000");
        when(repository.findByTransportType(TransportType.BOAT)).thenReturn(Optional.of(price));

        TransportPrice result = service.findByTransportType(TransportType.BOAT);

        assertThat(result.getTransportType()).isEqualTo(TransportType.BOAT);
    }

    @Test
    void shouldThrowWhenTransportTypeNotFound() {
        when(repository.findByTransportType(TransportType.RAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByTransportType(TransportType.RAIL))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("RAIL");
    }

    @Test
    void shouldUpdatePriceFields() {
        TransportPrice existing = buildPrice(TransportType.TRUCK, "0.0850", "45.0000");
        TransportPrice updated = TransportPrice.builder()
                .pricePerKg(new BigDecimal("0.1000"))
                .pricePerCubicMeter(new BigDecimal("50.0000"))
                .build();

        when(repository.findByTransportType(TransportType.TRUCK)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        TransportPrice result = service.update(TransportType.TRUCK, updated);

        assertThat(result.getPricePerKg()).isEqualByComparingTo("0.1000");
        assertThat(result.getPricePerCubicMeter()).isEqualByComparingTo("50.0000");

        ArgumentCaptor<TransportPrice> captor = ArgumentCaptor.forClass(TransportPrice.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTransportType()).isEqualTo(TransportType.TRUCK);
    }

    @Test
    void shouldThrowOnUpdateWhenTransportTypeNotFound() {
        when(repository.findByTransportType(TransportType.BOAT)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(TransportType.BOAT, TransportPrice.builder().build()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private TransportPrice buildPrice(TransportType type, String perKg, String perCubicMeter) {
        return TransportPrice.builder()
                .transportType(type)
                .pricePerKg(new BigDecimal(perKg))
                .pricePerCubicMeter(new BigDecimal(perCubicMeter))
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
