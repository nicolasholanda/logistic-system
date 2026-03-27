package com.logistic.web.controller;

import com.logistic.application.service.FreightService;
import com.logistic.web.dto.FreightCalculationRequest;
import com.logistic.web.dto.FreightCalculationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/freight")
@RequiredArgsConstructor
public class FreightController {

    private final FreightService freightService;

    @PostMapping("/calculate")
    @ResponseStatus(HttpStatus.CREATED)
    public FreightCalculationResponse calculate(@RequestBody @Valid FreightCalculationRequest request) {
        return FreightCalculationResponse.from(
                freightService.calculate(request.transportType(), request.weightKg(), request.volumeCubicMeters())
        );
    }
}
