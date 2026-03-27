package com.logistic.domain.repository;

import com.logistic.domain.model.FreightQuote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreightQuoteRepository extends JpaRepository<FreightQuote, Long> {
}
