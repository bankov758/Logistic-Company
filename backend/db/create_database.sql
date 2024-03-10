-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema logistic_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema logistic_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `logistic_company` DEFAULT CHARACTER SET utf8mb3;
USE `logistic_company`;

-- -----------------------------------------------------
-- Table `logistic_company`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`company`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `logistic_company`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`user`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `username`   VARCHAR(45) NOT NULL,
    `password`   VARCHAR(45) NOT NULL,
    `first_name` VARCHAR(45) NOT NULL,
    `last_name`  VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `logistic_company`.`company_has_client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`company_has_client`
(
    `company_id` INT NOT NULL,
    `client_id`  INT NOT NULL,
    PRIMARY KEY (`company_id`, `client_id`),
    INDEX `fk_company_has_user_user2_idx` (`client_id` ASC) VISIBLE,
    INDEX `fk_company_has_user_company2_idx` (`company_id` ASC) VISIBLE,
    CONSTRAINT `fk_company_has_user_company2`
        FOREIGN KEY (`company_id`)
            REFERENCES `logistic_company`.`company` (`id`),
    CONSTRAINT `fk_company_has_user_user2`
        FOREIGN KEY (`client_id`)
            REFERENCES `logistic_company`.`user` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `logistic_company`.`office`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`office`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `address`    VARCHAR(45) NOT NULL,
    `company_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `address_company` (`address` ASC, `company_id` ASC) VISIBLE,
    INDEX `fk_office_company1_idx` (`company_id` ASC) VISIBLE,
    CONSTRAINT `fk_office_company1`
        FOREIGN KEY (`company_id`)
            REFERENCES `logistic_company`.`company` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `logistic_company`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`employee`
(
    `id`         INT NOT NULL,
    `company_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_employee_company1_idx` (`company_id` ASC) VISIBLE,
    CONSTRAINT `fk_employee_user1`
        FOREIGN KEY (`id`)
            REFERENCES `logistic_company`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_employee_company1`
        FOREIGN KEY (`company_id`)
            REFERENCES `logistic_company`.`company` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`office_employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`office_employee`
(
    `id`        INT NOT NULL,
    `office_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_office_employee_office1_idx` (`office_id` ASC) VISIBLE,
    CONSTRAINT `fk_office_employee_employee1`
        FOREIGN KEY (`id`)
            REFERENCES `logistic_company`.`employee` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_office_employee_office1`
        FOREIGN KEY (`office_id`)
            REFERENCES `logistic_company`.`office` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`courier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`courier`
(
    `id` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_courier_employee1`
        FOREIGN KEY (`id`)
            REFERENCES `logistic_company`.`employee` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`shipment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`shipment`
(
    `id`                    INT         NOT NULL AUTO_INCREMENT,
    `departure_address`     VARCHAR(45) NOT NULL,
    `arrival_address`       VARCHAR(45) NOT NULL,
    `weight`                FLOAT       NOT NULL,
    `sender_id`             INT         NOT NULL,
    `receiver_id`           INT         NOT NULL,
    `is_sent_from_office`   TINYINT     NOT NULL,
    `is_received_in_office` TINYINT     NOT NULL,
    `office_employee_id`    INT         NOT NULL,
    `price`                 FLOAT       NOT NULL,
    `sent_date`             DATETIME    NOT NULL,
    `received_date`         DATETIME    NULL,
    `courier_id`            INT         NOT NULL,
    `company_id`            INT         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_shipment_user1_idx` (`sender_id` ASC) VISIBLE,
    INDEX `fk_shipment_user2_idx` (`receiver_id` ASC) VISIBLE,
    INDEX `fk_shipment_office_employee1_idx` (`office_employee_id` ASC) VISIBLE,
    INDEX `fk_shipment_courier1_idx` (`courier_id` ASC) VISIBLE,
    INDEX `fk_shipment_company1_idx` (`company_id` ASC) VISIBLE,
    CONSTRAINT `fk_shipment_user1`
        FOREIGN KEY (`sender_id`)
            REFERENCES `logistic_company`.`user` (`id`),
    CONSTRAINT `fk_shipment_user2`
        FOREIGN KEY (`receiver_id`)
            REFERENCES `logistic_company`.`user` (`id`),
    CONSTRAINT `fk_shipment_office_employee1`
        FOREIGN KEY (`office_employee_id`)
            REFERENCES `logistic_company`.`office_employee` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_shipment_courier1`
        FOREIGN KEY (`courier_id`)
            REFERENCES `logistic_company`.`courier` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_shipment_company1`
        FOREIGN KEY (`company_id`)
            REFERENCES `logistic_company`.`company` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `logistic_company`.`tariff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`tariff`
(
    `id`              INT   NOT NULL AUTO_INCREMENT,
    `price_per_kg`    FLOAT NOT NULL,
    `office_discount` FLOAT NOT NULL,
    `company_id`      INT   NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_tarriffs_company1_idx` (`company_id` ASC) VISIBLE,
    UNIQUE INDEX `unique_tariff_combination` (`price_per_kg` ASC, `office_discount` ASC, `company_id` ASC) VISIBLE,
    CONSTRAINT `fk_tarriffs_company1`
        FOREIGN KEY (`company_id`)
            REFERENCES `logistic_company`.`company` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `logistic_company`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`User_roles`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `user_id` INT         NOT NULL,
    `roles`   VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_user_roles_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_roles_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `logistic_company`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
