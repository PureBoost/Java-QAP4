-- Create the database
CREATE DATABASE qap4;

-- Create the Patient table
CREATE TABLE patient (
    patient_id SERIAL PRIMARY KEY,
    patient_first_name VARCHAR(100) NOT NULL,
    patient_last_name VARCHAR(100) NOT NULL,
    patient_dob INT NOT NULL
);

-- Test:
-- INSERT INTO patient (patient_first_name, patient_last_name, patient_dob) VALUES ('John', 'Doe', 1990);
