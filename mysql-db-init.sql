-- TABLES
CREATE TABLE if not exists student (
    stdNumber VARCHAR(10) PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    surname VARCHAR(32) NOT NULL,

    CONSTRAINT name_alpha CHECK(name REGEXP '^[A-Za-zğçşıĞÇŞİ]+$'),
    CONSTRAINT surname_alpha CHECK(surname REGEXP '^[A-Za-zğçşıĞÇŞİ]+$'),
    CONSTRAINT stdNumber_alphanumeric CHECK(stdNumber REGEXP '^[a-zA-Z0-9]+$'),

    CONSTRAINT name_length CHECK(LENGTH(name) >= 1),
    CONSTRAINT surname_length CHECK(LENGTH(surname) >= 1),
    CONSTRAINT stdNumber_length CHECK(LENGTH(stdNumber) = 10)
);

CREATE TABLE if not exists grade (
    code VARCHAR(6),
    stdNumber VARCHAR(10),
    value INT NOT NULL,

    PRIMARY KEY (code, stdNumber),
    FOREIGN KEY (stdNumber) REFERENCES student(stdNumber),

    CONSTRAINT code_validity CHECK(code REGEXP '^[A-Za-z]{2,3}[0-9]{3}$')
);


-- TRIGGERS
