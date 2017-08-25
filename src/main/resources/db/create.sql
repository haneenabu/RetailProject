SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS stores(
    id int PRIMARY KEY auto_increment,
    storeName VARCHAR,
    retailType VARCHAR,
    yearEstablished INT,
    contact VARCHAR,
    contactInfo VARCHAR
);
CREATE TABLE IF NOT EXISTS blogs (
    id int PRIMARY KEY auto_increment,
    writtenBy VARCHAR,
    rating VARCHAR,
    storeId INTEGER
);
CREATE TABLE IF NOT EXISTS storetypes (
 id int PRIMARY KEY auto_increment,
 name VARCHAR
);