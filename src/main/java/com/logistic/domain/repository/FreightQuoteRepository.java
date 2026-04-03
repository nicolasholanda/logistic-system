package com.logistic.domain.repository;

import com.logistic.domain.enums.TransportType;
import com.logistic.domain.model.FreightQuote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreightQuoteRepository extends JpaRepository<FreightQuote, Long> {

    Page<FreightQuote> findByTransportType(TransportType transportType, Pageable pageable);
}
