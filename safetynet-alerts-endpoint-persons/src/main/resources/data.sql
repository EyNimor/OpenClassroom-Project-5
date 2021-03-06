DROP TABLE IF EXISTS persons;

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

DROP TABLE IF EXISTS MEDICALRECORDS;
DROP TABLE IF EXISTS FIRESTATIONS;