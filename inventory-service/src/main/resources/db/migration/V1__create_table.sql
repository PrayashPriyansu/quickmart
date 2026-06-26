CREATE TABLE product_stock (
    id BINARY(16) PRIMARY KEY ,
    sku_id BINARY(16) NOT NULL ,
    store_id BINARY(16) NOT NULL,
    quantity_available INT NOT NULL DEFAULT 0,
    quantity_reserved INT NOT NULL DEFAULT 0,
    low_stock_threshold INT NOT NULL DEFAULT 10,
    version INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (sku_id, store_id)
);

CREATE TABLE stock_events (
    id BINARY(16) PRIMARY KEY,
    aggregate_id BINARY(16) NOT NULL ,
    version INT NOT NULL DEFAULT 0,
    event_type VARCHAR(100) NOT NULL,
    payload JSON NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(aggregate_id, version)
);