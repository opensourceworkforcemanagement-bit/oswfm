-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_number        VARCHAR(50) NOT NULL UNIQUE,
    customer_id         UUID NOT NULL,
    status              VARCHAR(50) NOT NULL DEFAULT 'CART',
    total_amount        NUMERIC(12, 2),
    currency            VARCHAR(3) NOT NULL DEFAULT 'USD',

    -- Payment tracking
    payment_reference   VARCHAR(255),
    payment_provider    VARCHAR(50),

    -- Broadcast tracking
    broadcast_id        UUID,
    camera_id           UUID,
    broadcast_started_at TIMESTAMP,
    broadcast_ended_at   TIMESTAMP,
    broadcast_duration_seconds INT,

    -- Customer feedback
    customer_rating     SMALLINT CHECK (customer_rating BETWEEN 1 AND 5),
    customer_comment    TEXT,
    rated_at            TIMESTAMP,

    -- Lifecycle
    created_at          TIMESTAMP NOT NULL DEFAULT now(),
    updated_at          TIMESTAMP NOT NULL DEFAULT now(),
    cancelled_at        TIMESTAMP,
    cancellation_reason TEXT
);

-- Order items table
CREATE TABLE IF NOT EXISTS order_items (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id        UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id      UUID NOT NULL,
    product_name    VARCHAR(255) NOT NULL,
    quantity        INT NOT NULL DEFAULT 1,
    unit_price      NUMERIC(12, 2) NOT NULL,
    total_price     NUMERIC(12, 2) NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT now()
);

-- Order status history / audit trail
CREATE TABLE IF NOT EXISTS order_status_history (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id    UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    from_status VARCHAR(50),
    to_status   VARCHAR(50) NOT NULL,
    reason      TEXT,
    changed_by  VARCHAR(255),
    changed_at  TIMESTAMP NOT NULL DEFAULT now()
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_orders_customer_id ON orders(customer_id);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status);
CREATE INDEX IF NOT EXISTS idx_orders_order_number ON orders(order_number);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_order_status_history_order_id ON order_status_history(order_id);
