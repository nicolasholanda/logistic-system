package com.logistic.domain.model;

import com.logistic.domain.enums.TransportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transport_prices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false, unique = true)
    private TransportType transportType;

    @Column(name = "price_per_kg", nullable = false, precision = 10, scale = 4)
    private BigDecimal pricePerKg;

    @Column(name = "price_per_cubic_meter", nullable = false, precision = 10, scale = 4)
    private BigDecimal pricePerCubicMeter;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
