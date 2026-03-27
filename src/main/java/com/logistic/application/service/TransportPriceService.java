package com.logistic.application.service;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.TransportPrice;
import com.logistic.domain.repository.TransportPriceRepository;
import com.logistic.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportPriceService {

    private final TransportPriceRepository repository;

    @Transactional(readOnly = true)
    public List<TransportPrice> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public TransportPrice findByTransportType(TransportType transportType) {
        return repository.findByTransportType(transportType)
                .orElseThrow(() -> new ResourceNotFoundException("Price not found for transport type: " + transportType));
    }

    @Transactional
    public TransportPrice update(TransportType transportType, TransportPrice updated) {
        TransportPrice existing = findByTransportType(transportType);
        existing.setPricePerKg(updated.getPricePerKg());
        existing.setPricePerCubicMeter(updated.getPricePerCubicMeter());
        return repository.save(existing);
    }
}
