SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS stores(
    id int PRIMARY KEY auto_increment,
    storeName VARCHAR,
    retailType VARCHAR,
    yearEstablished INT,
    contact VARCHAR,
    contactInfo VARCHAR
);
