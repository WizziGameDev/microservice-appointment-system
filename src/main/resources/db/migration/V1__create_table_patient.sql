CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    slug VARCHAR(255),
    name VARCHAR(255),
    email VARCHAR(255),
    phone_number BIGINT,
    address VARCHAR(255),
    gender VARCHAR(50),
    birth_date BIGINT,
    created_at BIGINT,
    updated_at BIGINT,
    deleted_at BIGINT
);