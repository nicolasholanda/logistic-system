package com.logistic.application.service;

import com.logistic.application.strategy.FreightStrategy;
import com.logistic.application.strategy.FreightStrategyResolver;
import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.FreightQuote;
import com.logistic.domain.model.TransportPrice;
import com.logistic.domain.repository.FreightQuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FreightService {

    private final FreightStrategyResolver strategyResolver;
    private final TransportPriceService transportPriceService;
    private final FreightQuoteRepository freightQuoteRepository;

    @Transactional
    public FreightQuote calculate(TransportType transportType, BigDecimal weightKg, BigDecimal volumeCubicMeters) {
        TransportPrice price = transportPriceService.findByTransportType(transportType);
        FreightStrategy strategy = strategyResolver.resolve(transportType);
        BigDecimal totalPrice = strategy.calculate(weightKg, volumeCubicMeters, price);

        FreightQuote quote = FreightQuote.builder()
                .transportType(transportType)
                .weightKg(weightKg)
                .volumeCubicMeters(volumeCubicMeters)
                .totalPrice(totalPrice)
                .build();

        return freightQuoteRepository.save(quote);
    }
}
