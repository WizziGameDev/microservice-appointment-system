CREATE TABLE doctors (
    id SERIAL PRIMARY KEY,
    slug VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    email VARCHAR(255),
    phone_number BIGINT,
    address TEXT,
    gender VARCHAR(50),
    birth_date BIGINT,
    license_number VARCHAR(255),
    experience_years INT,
    specialization VARCHAR(255),
    created_at BIGINT,
    updated_at BIGINT,
    deleted_at BIGINT
);
