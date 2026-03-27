package com.logistic.domain.repository;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.TransportPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportPriceRepository extends JpaRepository<TransportPrice, Long> {

    Optional<TransportPrice> findByTransportType(TransportType transportType);
}
