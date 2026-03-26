CREATE TABLE freight_quotes (
    id                   BIGSERIAL PRIMARY KEY,
    transport_type       VARCHAR(10)    NOT NULL,
    weight_kg            NUMERIC(10, 3) NOT NULL,
    volume_cubic_meters  NUMERIC(10, 3) NOT NULL,
    total_price          NUMERIC(12, 4) NOT NULL,
    created_at           TIMESTAMP      NOT NULL
);
