CREATE TABLE IF NOT EXISTS patient
(
    patient_id    INT PRIMARY KEY,
    name          VARCHAR(255),
    date_of_birth DATE,
    gender        VARCHAR(50),
    contact_info  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS doctor
(
    doctor_id    INT PRIMARY KEY,
    name         VARCHAR(255),
    specialty    VARCHAR(255),
    contact_info VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS appointment
(
    appointment_id      INT PRIMARY KEY,
    date_time           DATETIME,
    consultation_reason VARCHAR(255),
    patient_id          INT,
    doctor_id           INT,
    FOREIGN KEY (patient_id) REFERENCES patient (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id)
);

CREATE TABLE IF NOT EXISTS medical_history
(
    history_id         INT PRIMARY KEY,
    patient_id         INT,
    disease_history    TEXT,
    allergies          TEXT,
    previous_surgeries TEXT,
    FOREIGN KEY (patient_id) REFERENCES patient (patient_id)
);

CREATE TABLE IF NOT EXISTS prescription
(
    prescription_id       INT PRIMARY KEY,
    patient_id            INT,
    doctor_id             INT,
    prescribed_medication VARCHAR(255),
    dosage                VARCHAR(255),
    duration              VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES patient (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id)
);
