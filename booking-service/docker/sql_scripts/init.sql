CREATE TABLE bookings(
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    item_id UUID NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

