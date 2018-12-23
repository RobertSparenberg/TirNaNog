CREATE DATABASE `TirNaNog`;

CREATE USER 'TirNaNog'@'localhost' IDENTIFIED BY 'YellowTweezerCurling';
GRANT ALL PRIVILEGES ON `TirNaNog`.* TO 'TirNaNog'@'localhost';

USE `TirNaNog`;

CREATE TABLE `module_config` (
    `name` VARCHAR(255) NOT NULL,
    `ip` VARCHAR(50) NOT NULL,
    `last_message_timestamp` BIGINT NOT NULL,
    `hardware_interface_only` TINYINT NOT NULL,
    PRIMARY KEY (`name`)
);

CREATE TABLE `capabilities` (
    `name` VARCHAR(255) NOT NULL,
    `module_config` VARCHAR(255) NOT NULL,
    `consumer` TINYINT NOT NULL,
    `producer` TINYINT NOT NULL,
    PRIMARY KEY (`name`),
    FOREIGN KEY (`module_config`) REFERENCES `module_config`(`name`)
);

INSERT INTO `module_config` (`name`, `ip`, `last_message_timestamp`, `hardware_interface_only`) VALUES ('New TirNaNog Device', 'localhost', 0, 0);