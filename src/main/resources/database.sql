DROP SCHEMA IF EXISTS productosDB;
DROP USER IF EXISTS 'spq'@'localhost';

CREATE SCHEMA productosDB;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';

GRANT ALL ON productosDB.* TO 'spq'@'localhost';