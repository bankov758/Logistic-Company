-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema logistic_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema logistic_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `logistic_company` DEFAULT CHARACTER SET utf8 ;
USE `logistic_company` ;

-- -----------------------------------------------------
-- Table `logistic_company`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`company` (
                                                            `id` INT NOT NULL,
                                                            `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`user` (
                                                         `id` INT NOT NULL AUTO_INCREMENT,
                                                         `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `first_name` VARCHAR(45) NOT NULL,
    `last_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`role` (
                                                         `id` INT NOT NULL,
                                                         `role` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`shipment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`shipment` (
                                                             `id` INT NOT NULL,
                                                             `departure_address` VARCHAR(45) NOT NULL,
    `arrival_address` VARCHAR(45) NOT NULL,
    `weight` FLOAT NOT NULL,
    `sender_id` INT NOT NULL,
    `receiver_id` INT NOT NULL,
    `employee_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_shipment_user1_idx` (`sender_id` ASC) VISIBLE,
    INDEX `fk_shipment_user2_idx` (`receiver_id` ASC) VISIBLE,
    INDEX `fk_shipment_user3_idx` (`employee_id` ASC) VISIBLE,
    CONSTRAINT `fk_shipment_user1`
    FOREIGN KEY (`sender_id`)
    REFERENCES `logistic_company`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_shipment_user2`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `logistic_company`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_shipment_user3`
    FOREIGN KEY (`employee_id`)
    REFERENCES `logistic_company`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`company_has_employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`company_has_employee` (
                                                                         `company_id` INT NOT NULL,
                                                                         `employee_id` INT NOT NULL,
                                                                         PRIMARY KEY (`company_id`, `employee_id`),
    INDEX `fk_company_has_user_user1_idx` (`employee_id` ASC) VISIBLE,
    INDEX `fk_company_has_user_company1_idx` (`company_id` ASC) VISIBLE,
    CONSTRAINT `fk_company_has_user_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `logistic_company`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_company_has_user_user1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `logistic_company`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`company_has_client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`company_has_client` (
                                                                       `company_id` INT NOT NULL,
                                                                       `client_id` INT NOT NULL,
                                                                       PRIMARY KEY (`company_id`, `client_id`),
    INDEX `fk_company_has_user_user2_idx` (`client_id` ASC) VISIBLE,
    INDEX `fk_company_has_user_company2_idx` (`company_id` ASC) VISIBLE,
    CONSTRAINT `fk_company_has_user_company2`
    FOREIGN KEY (`company_id`)
    REFERENCES `logistic_company`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_company_has_user_user2`
    FOREIGN KEY (`client_id`)
    REFERENCES `logistic_company`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`office`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`office` (
                                                           `id` INT NOT NULL,
                                                           `address` VARCHAR(45) NOT NULL,
    `company_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_office_company1_idx` (`company_id` ASC) VISIBLE,
    UNIQUE INDEX `address_company` (`address` ASC, `company_id` ASC) VISIBLE,
    CONSTRAINT `fk_office_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `logistic_company`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`delivery`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`delivery` (
                                                             `id` INT NOT NULL,
                                                             `price` FLOAT NOT NULL,
                                                             `shipment_id` INT NOT NULL,
                                                             `date` DATE NULL,
                                                             `courier_id` INT NOT NULL,
                                                             PRIMARY KEY (`id`),
    INDEX `fk_delivery_shipment1_idx` (`shipment_id` ASC) VISIBLE,
    INDEX `fk_delivery_user1_idx` (`courier_id` ASC) VISIBLE,
    CONSTRAINT `fk_delivery_shipment1`
    FOREIGN KEY (`shipment_id`)
    REFERENCES `logistic_company`.`shipment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_delivery_user1`
    FOREIGN KEY (`courier_id`)
    REFERENCES `logistic_company`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`user_has_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`user_has_role` (
                                                                  `user_id` INT NOT NULL,
                                                                  `role_id` INT NOT NULL,
                                                                  PRIMARY KEY (`user_id`, `role_id`),
    INDEX `fk_user_has_role_role1_idx` (`role_id` ASC) VISIBLE,
    INDEX `fk_user_has_role_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_has_role_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `logistic_company`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_has_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `logistic_company`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_company`.`tarriffs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`tarriffs` (
                                                             `id` INT NOT NULL,
                                                             `price_per_kg` FLOAT NULL,
                                                             `office_discount` FLOAT NULL,
                                                             `company_id` INT NOT NULL,
                                                             PRIMARY KEY (`id`),
    INDEX `fk_tarriffs_company1_idx` (`company_id` ASC) VISIBLE,
    CONSTRAINT `fk_tarriffs_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `logistic_company`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
