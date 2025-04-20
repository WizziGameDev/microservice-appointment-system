ALTER TABLE availabilitys
    ADD CONSTRAINT fk_availability_doctor
        FOREIGN KEY (doctor_id)
            REFERENCES doctors(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;