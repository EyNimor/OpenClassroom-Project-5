DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS medicalrecords;
DROP TABLE IF EXISTS firestations;

CREATE TABLE persons (
    id INT (250) PRIMARY KEY NOT NULL,
    first_Name VARCHAR(250) NOT NULL,
    last_Name VARCHAR(250) NOT NULL,
    address VARCHAR(250) NOT NULL,
    city VARCHAR(250) NOT NULL,
    zip VARCHAR(250) NOT NULL,
    phone VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL
);

CREATE TABLE firestations (
    id INT (250) PRIMARY KEY NOT NULL,
    address VARCHAR(250) NOT NULL,
    station INT (250) NOT NULL
);

CREATE TABLE medicalrecords (
    id INT (250) PRIMARY KEY NOT NULL,
    first_Name VARCHAR(250) NOT NULL,
    last_Name VARCHAR(250) NOT NULL,
    birthdate VARCHAR(250) NOT NULL,
    medications VARCHAR(500) NOT NULL,
    allergies VARCHAR(500) NOT NULL
);