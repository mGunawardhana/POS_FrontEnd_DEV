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
    id      VARCHAR(10),
    `name`  VARCHAR(50),
    address VARCHAR(20),
    contact VARCHAR(12),
    CONSTRAINT PRIMARY KEY (id)
);

INSERT INTO customer
VALUES ('C00-001', 'Sunimal', 'Galle', 071-9076652),
       ('C00-002', 'Samarapala', 'Galle', 071-9073652),
       ('C00-003', 'Kasun', 'Matara', 071-9023652),
       ('C00-004', 'Rahul', 'Habanthota', 071-9665267),
       ('C00-005', 'Amarathunga', 'Hapugala', 071-576652);


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
    orderId  VARCHAR(10),
    iCode    VARCHAR(10),
    itemName VARCHAR(10),
    price    double DEFAULT 0.00,
    Qty      INT,
    total    double,

    CONSTRAINT PRIMARY KEY (orderId, iCode),
    CONSTRAINT FOREIGN KEY (iCode) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE `orders`;


DROP TABLE IF EXISTS `orderDetails`;
CREATE TABLE IF NOT EXISTS `orderDetails`
(
    oID      VARCHAR(10),
    iCode    VARCHAR(10),
    date     DATE,
    cID      VARCHAR(10),
    oQty     double,
    Tot      double,
    discount double,

    CONSTRAINT PRIMARY KEY (oID, iCode),
    CONSTRAINT FOREIGN KEY (oID) REFERENCES `orders` (orderId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (iCode) REFERENCES item (itemId) ON DELETE CASCADE ON UPDATE CASCADE

);
SHOW TABLES;
DESCRIBE `orderDetails`;