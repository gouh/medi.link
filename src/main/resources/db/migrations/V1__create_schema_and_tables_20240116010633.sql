CREATE TABLE IF NOT EXISTS patient
(
    patient_id    UUID PRIMARY KEY  DEFAULT (UUID()),
    name          VARCHAR(100),
    date_of_birth DATE,
    gender        ENUM ('M','F'),
    contact_info  VARCHAR(150),
    created_at    DATETIME NOT NULL DEFAULT NOW(),
    updated_at    DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS doctor
(
    doctor_id    UUID PRIMARY KEY  DEFAULT (UUID()),
    name         VARCHAR(100),
    specialty    VARCHAR(255),
    contact_info VARCHAR(150),
    created_at   DATETIME NOT NULL DEFAULT NOW(),
    updated_at   DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS appointment
(
    appointment_id      UUID PRIMARY KEY  DEFAULT (UUID()),
    patient_id          UUID,
    doctor_id           UUID,
    date_time           DATETIME,
    consultation_reason VARCHAR(200),
    created_at          DATETIME NOT NULL DEFAULT NOW(),
    updated_at          DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (patient_id) REFERENCES patient (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id)
);

CREATE TABLE IF NOT EXISTS medical_history
(
    history_id         UUID PRIMARY KEY  DEFAULT (UUID()),
    patient_id         UUID UNIQUE,
    disease_history    TEXT,
    allergies          TEXT,
    previous_surgeries TEXT,
    created_at         DATETIME NOT NULL DEFAULT NOW(),
    updated_at         DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (patient_id) REFERENCES patient (patient_id)
);

CREATE TABLE IF NOT EXISTS prescription
(
    prescription_id       UUID PRIMARY KEY  DEFAULT (UUID()),
    patient_id            UUID,
    doctor_id             UUID,
    prescribed_medication VARCHAR(100),
    dosage                VARCHAR(100),
    duration              VARCHAR(100),
    created_at            DATETIME NOT NULL DEFAULT NOW(),
    updated_at            DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (patient_id) REFERENCES patient (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id)
);
