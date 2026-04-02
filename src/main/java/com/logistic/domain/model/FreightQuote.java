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
@Table(name = "freight_quotes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreightQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    private TransportType transportType;

    @Column(name = "weight_kg", nullable = false, precision = 10, scale = 3)
    private BigDecimal weightKg;

    @Column(name = "volume_cubic_meters", nullable = false, precision = 10, scale = 3)
    private BigDecimal volumeCubicMeters;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 4)
    private BigDecimal totalPrice;

    @Column(name = "price_per_kg_snapshot", precision = 10, scale = 4)
    private BigDecimal pricePerKgSnapshot;

    @Column(name = "price_per_cubic_meter_snapshot", precision = 10, scale = 4)
    private BigDecimal pricePerCubicMeterSnapshot;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
