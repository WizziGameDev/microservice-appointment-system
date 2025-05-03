CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    slug VARCHAR(30),
    name_appointment VARCHAR(100),
    patient_slug VARCHAR(255) NOT NULL,
    doctor_slug VARCHAR(255) NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_time BIGINT NOT NULL,
    end_time BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at BIGINT,
    updated_at BIGINT,
    deleted_at BIGINT
);
