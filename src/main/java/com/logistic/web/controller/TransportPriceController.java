package com.logistic.web.controller;

import com.logistic.application.service.TransportPriceService;
import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.TransportPrice;
import com.logistic.web.dto.TransportPriceRequest;
import com.logistic.web.dto.TransportPriceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class TransportPriceController {

    private final TransportPriceService transportPriceService;

    @GetMapping
    public List<TransportPriceResponse> findAll() {
        return transportPriceService.findAll().stream()
                .map(TransportPriceResponse::from)
                .toList();
    }

    @GetMapping("/{type}")
    public TransportPriceResponse findByType(@PathVariable TransportType type) {
        return TransportPriceResponse.from(transportPriceService.findByTransportType(type));
    }

    @PutMapping("/{type}")
    public TransportPriceResponse update(@PathVariable TransportType type,
                                         @RequestBody @Valid TransportPriceRequest request) {
        TransportPrice updated = TransportPrice.builder()
                .pricePerKg(request.pricePerKg())
                .pricePerCubicMeter(request.pricePerCubicMeter())
                .build();
        return TransportPriceResponse.from(transportPriceService.update(type, updated));
    }
}
