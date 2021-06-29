DROP TABLE IF EXISTS medicalrecords;

CREATE TABLE medicalrecords (
    id INT (250) PRIMARY KEY NOT NULL,
    first_Name VARCHAR(250) NOT NULL,
    last_Name VARCHAR(250) NOT NULL,
    birthdate VARCHAR(250) NOT NULL,
    medications VARCHAR(500) NOT NULL,
    allergies VARCHAR(500) NOT NULL
);

DROP TABLE IF EXISTS firestations;
DROP TABLE IF EXISTS persons;