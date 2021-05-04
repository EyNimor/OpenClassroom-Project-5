DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS fireStations;
DROP TABLE IF EXISTS medicalRecords;

CREATE TABLE persons (
    id VARCHAR (250) NOT NULL,
    first_Name VARCHAR(250) NOT NULL,
    last_Name VARCHAR(250) NOT NULL,
    address VARCHAR(250) NOT NULL,
    city VARCHAR(250) NOT NULL,
    zip VARCHAR(250) NOT NULL,
    phone VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL
);

CREATE TABLE fireStations (
    address VARCHAR(250) NOT NULL,
    station VARCHAR(250) NOT NULL
);

CREATE TABLE medicalRecords ( 
    firstName VARCHAR(250) NOT NULL,
    lastName VARCHAR(250) NOT NULL,
    birthdate VARCHAR(250) NOT NULL,
    medications VARCHAR(250) NOT NULL,
    allergies VARCHAR(250) NOT NULL
);