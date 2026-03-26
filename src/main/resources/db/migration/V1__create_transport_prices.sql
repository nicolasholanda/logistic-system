CREATE TABLE transport_prices (
    id                   BIGSERIAL PRIMARY KEY,
    transport_type       VARCHAR(10)    NOT NULL UNIQUE,
    price_per_kg         NUMERIC(10, 4) NOT NULL,
    price_per_cubic_meter NUMERIC(10, 4) NOT NULL,
    updated_at           TIMESTAMP      NOT NULL
);
