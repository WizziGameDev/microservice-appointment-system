SELECT * FROM availabilitys;

SELECT * FROM doctors;

-- Data untuk 10 dokter
INSERT INTO doctors (
    slug,
    name,
    email,
    phone_number,
    address,
    gender,
    birth_date,
    license_number,
    experience_years,
    specialization,
    created_at,
    updated_at,
    deleted_at
)
VALUES
    ('dr-john-doe', 'Dr. John Doe', 'dr.johndoe@example.com', 1234567890, '123 Main St, Anytown, USA', 'Male', 950745600000, 'LIC1234567890', 10, 'General Practitioner', 1682047792000, NULL, 0),
    ('dr-jane-smith', 'Dr. Jane Smith', 'dr.janesmith@example.com', 2345678901, '456 Oak St, Anytown, USA', 'Female', 945324000000, 'LIC2345678901', 8, 'Cardiologist', 1682047793000, NULL, 0),
    ('dr-mark-johnson', 'Dr. Mark Johnson', 'dr.markjohnson@example.com', 3456789012, '789 Pine St, Anytown, USA', 'Male', 936684000000, 'LIC3456789012', 12, 'Orthopedic Surgeon', 1682047794000, NULL, 0),
    ('dr-emily-davis', 'Dr. Emily Davis', 'dr.emilydavis@example.com', 4567890123, '321 Birch St, Anytown, USA', 'Female', 911273600000, 'LIC4567890123', 6, 'Pediatrician', 1682047795000, NULL, 0),
    ('dr-david-martinez', 'Dr. David Martinez', 'dr.davidmartinez@example.com', 5678901234, '654 Maple St, Anytown, USA', 'Male', 905964000000, 'LIC5678901234', 15, 'Dermatologist', 1682047796000, NULL, 0),
    ('dr-lisa-lee', 'Dr. Lisa Lee', 'dr.lisalee@example.com', 6789012345, '987 Cedar St, Anytown, USA', 'Female', 929406400000, 'LIC6789012345', 9, 'Obstetrician', 1682047797000, NULL, 0),
    ('dr-james-brown', 'Dr. James Brown', 'dr.jamesbrown@example.com', 7890123456, '741 Elm St, Anytown, USA', 'Male', 922764000000, 'LIC7890123456', 7, 'Neurologist', 1682047798000, NULL, 0),
    ('dr-nina-white', 'Dr. Nina White', 'dr.ninawhite@example.com', 8901234567, '852 Ash St, Anytown, USA', 'Female', 936475200000, 'LIC8901234567', 5, 'Gastroenterologist', 1682047799000, NULL, 0),
    ('dr-robert-green', 'Dr. Robert Green', 'dr.robertgreen@example.com', 9012345678, '963 Willow St, Anytown, USA', 'Male', 920550400000, 'LIC9012345678', 20, 'Radiologist', 1682047800000, NULL, 0),
    ('dr-susan-hall', 'Dr. Susan Hall', 'dr.susanhall@example.com', 1023456789, '159 Fir St, Anytown, USA', 'Female', 915256000000, 'LIC1023456789', 11, 'Psychiatrist', 1682047801000, NULL, 0);

-- Availability untuk dokter 1 hingga 10
INSERT INTO availabilitys (
    doctor_id,
    day_of_week,
    start_time,
    end_time,
    created_at,
    updated_at,
    deleted_at
)
VALUES
    -- Dr. John Doe (Doctor ID = 1)
    (1, 'Monday', 1682047792000, 1682051392000, 1682047792000, NULL, 0),
    (1, 'Wednesday', 1682134192000, 1682137792000, 1682047792000, NULL, 0),

    -- Dr. Jane Smith (Doctor ID = 2)
    (2, 'Tuesday', 1682134192000, 1682137792000, 1682047793000, NULL, 0),
    (2, 'Friday', 1682214192000, 1682217792000, 1682047793000, NULL, 0),

    -- Dr. Mark Johnson (Doctor ID = 3)
    (3, 'Monday', 1682047792000, 1682051392000, 1682047794000, NULL, 0),
    (3, 'Thursday', 1682220592000, 1682224192000, 1682047794000, NULL, 0),

    -- Dr. Emily Davis (Doctor ID = 4)
    (4, 'Monday', 1682047792000, 1682051392000, 1682047795000, NULL, 0),
    (4, 'Tuesday', 1682134192000, 1682137792000, 1682047795000, NULL, 0),

    -- Dr. David Martinez (Doctor ID = 5)
    (5, 'Wednesday', 1682134192000, 1682137792000, 1682047796000, NULL, 0),
    (5, 'Friday', 1682214192000, 1682217792000, 1682047796000, NULL, 0),

    -- Dr. Lisa Lee (Doctor ID = 6)
    (6, 'Tuesday', 1682134192000, 1682137792000, 1682047797000, NULL, 0),
    (6, 'Thursday', 1682220592000, 1682224192000, 1682047797000, NULL, 0),

    -- Dr. James Brown (Doctor ID = 7)
    (7, 'Monday', 1682047792000, 1682051392000, 1682047798000, NULL, 0),
    (7, 'Friday', 1682214192000, 1682217792000, 1682047798000, NULL, 0),

    -- Dr. Nina White (Doctor ID = 8)
    (8, 'Wednesday', 1682134192000, 1682137792000, 1682047799000, NULL, 0),
    (8, 'Saturday', 1682297392000, 1682300992000, 1682047799000, NULL, 0),

    -- Dr. Robert Green (Doctor ID = 9)
    (9, 'Monday', 1682047792000, 1682051392000, 1682047800000, NULL, 0),
    (9, 'Thursday', 1682220592000, 1682224192000, 1682047800000, NULL, 0),

    -- Dr. Susan Hall (Doctor ID = 10)
    (10, 'Tuesday', 1682134192000, 1682137792000, 1682047801000, NULL, 0),
    (10, 'Friday', 1682214192000, 1682217792000, 1682047801000, NULL, 0);


SELECT
    d.id,
    d.address,
    d.birth_date,
    d.created_at,
    d.deleted_at,
    d.email,
    d.experience_years,
    d.gender,
    d.license_number,
    d.name,
    d.phone_number,
    d.slug,
    d.specialization,
    d.updated_at,
    a.doctor_id,
    a.id AS availability_id,
    a.created_at AS availability_created_at,
    a.day_of_week,
    a.deleted_at AS availability_deleted_at,
    a.end_time,
    a.start_time,
    a.updated_at AS availability_updated_at
FROM
    doctors d
        INNER JOIN
    availabilitys a
    ON d.id = a.doctor_id
WHERE
    d.deleted_at = 0;
