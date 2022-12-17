/*
 * Developed by - mGunawardhana
 * Contact email - mrgunawardhana27368@gmail.com
 * what's app - 071 - 9043372
 */

DROP DATABASE IF EXISTS company;
CREATE DATABASE IF NOT EXISTS company;
SHOW DATABASES;
USE company;


DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer
(
    id      VARCHAR(6),
    `name`  VARCHAR(50),
    address VARCHAR(20),
    contact  VARCHAR (12),
    CONSTRAINT PRIMARY KEY (id)
);

DROP TABLE IF EXISTS item;
CREATE TABLE IF NOT EXISTS item
(
    itemId    VARCHAR(6),
    itemName  VARCHAR(50),
    qty       VARCHAR(20),
    unitPrice double DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (itemId)
);


