DROP DATABASE IF EXISTS company;
CREATE DATABASE IF NOT EXISTS company;
SHOW DATABASES;
USE company;


DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer
(
    id      VARCHAR(10),
    `name`  VARCHAR(50),
    address VARCHAR(20),
    contact VARCHAR(12),
    CONSTRAINT PRIMARY KEY (id)
);

INSERT INTO customer
VALUES ('C00-001', 'Sunimal', 'Galle', 071 - 9076652),
       ('C00-002', 'Samarapala', 'Galle', 071 - 9073652),
       ('C00-003', 'Kasun', 'Matara', 071 - 9023652),
       ('C00-004', 'Rahul', 'Habanthota', 071 - 9665267),
       ('C00-005', 'Amarathunga', 'Hapugala', 071 - 576652);


DROP TABLE IF EXISTS item;
CREATE TABLE IF NOT EXISTS item
(
    itemId    VARCHAR(10),
    itemName  VARCHAR(50),
    qty       VARCHAR(20),
    unitPrice double DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (itemId)
);

INSERT INTO item
VALUES ('I00-001', 'Kottu', 1000, 110.00),
       ('I00-002', 'Rolls', 1000, 140.00),
       ('I00-003', 'Burger', 1000, 80.00),
       ('I00-004', 'Patis', 1000, 120.00),
       ('I00-005', 'Chips', 1000, 110.00),
       ('I00-006', 'Milk Shake', 1000, 170.00),
       ('I00-007', 'Egg bun', 1000, 160.00);

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders`
(
    order_id         VARCHAR(10),
    order_date       DATE,
    customer_id      VARCHAR(10),
    customer_name    VARCHAR(20),
    customer_contact VARCHAR(12)
);

SHOW TABLES;
DESCRIBE `orders`;


DROP TABLE IF EXISTS `orderDetails`;
CREATE TABLE IF NOT EXISTS `orderDetails`
(
    order_id VARCHAR(10),
    code     VARCHAR(10),
    name     VARCHAR(20),
    price    double,
    quantity int,
    total    double
);
SHOW TABLES;
DESCRIBE `orderDetails`;