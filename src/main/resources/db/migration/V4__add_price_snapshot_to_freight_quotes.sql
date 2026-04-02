ALTER TABLE freight_quotes
    ADD COLUMN price_per_kg_snapshot         NUMERIC(10, 4),
    ADD COLUMN price_per_cubic_meter_snapshot NUMERIC(10, 4);
