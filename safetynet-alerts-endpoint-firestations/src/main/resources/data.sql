DROP TABLE IF EXISTS firestations;

CREATE TABLE firestations (
    id INT (250) PRIMARY KEY NOT NULL,
    address VARCHAR(250) NOT NULL,
    station INT (250) NOT NULL
);

DROP TABLE IF EXISTS medicalrecords;
DROP TABLE IF EXISTS persons;