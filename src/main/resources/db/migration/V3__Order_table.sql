CREATE TABLE IF NOT EXISTS client_order (
    order_id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL REFERENCES Book(id),
    client_id BIGINT NOT NULL REFERENCES Client(id),
    client_name VARCHAR(255),
    order_date TIMESTAMP NOT NULL
);