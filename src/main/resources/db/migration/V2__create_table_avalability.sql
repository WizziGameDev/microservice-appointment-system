CREATE TABLE availabilitys (
    id SERIAL PRIMARY KEY,
    doctor_id INT,
    day_of_week VARCHAR(50),
    start_time BIGINT,
    end_time BIGINT,
    created_at BIGINT,
    updated_at BIGINT
);