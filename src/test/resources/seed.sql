DELETE FROM freight_quotes;
DELETE FROM transport_prices;

INSERT INTO transport_prices (transport_type, price_per_kg, price_per_cubic_meter, updated_at) VALUES
    ('BOAT',  0.0120, 15.0000, NOW()),
    ('TRUCK', 0.0850, 45.0000, NOW()),
    ('RAIL',  0.0350, 25.0000, NOW());
