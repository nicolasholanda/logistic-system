package com.logistic.application.strategy;

import com.logistic.domain.enums.TransportType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FreightStrategyResolver {

    private final Map<TransportType, FreightStrategy> strategies;

    public FreightStrategyResolver(
            BoatFreightStrategy boatFreightStrategy,
            TruckFreightStrategy truckFreightStrategy,
            RailFreightStrategy railFreightStrategy) {
        strategies = Map.of(
                TransportType.BOAT, boatFreightStrategy,
                TransportType.TRUCK, truckFreightStrategy,
                TransportType.RAIL, railFreightStrategy
        );
    }

    public FreightStrategy resolve(TransportType transportType) {
        FreightStrategy strategy = strategies.get(transportType);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for transport type: " + transportType);
        }
        return strategy;
    }
}
