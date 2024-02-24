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
-- -----------------------------------------------------
-- Schema transport_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema transport_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `transport_company` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
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
    AUTO_INCREMENT = 5
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
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `logistic_company`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`employee`
(
    `id`         INT NOT NULL,
    `company_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_employee_company1_idx` (`company_id` ASC) INVISIBLE,
    CONSTRAINT `fk_employee_company1`
        FOREIGN KEY (`company_id`)
            REFERENCES `logistic_company`.`company` (`id`),
    CONSTRAINT `fk_employee_user1`
        FOREIGN KEY (`id`)
            REFERENCES `logistic_company`.`user` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


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
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb3;


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
            REFERENCES `logistic_company`.`employee` (`id`),
    CONSTRAINT `fk_office_employee_office1`
        FOREIGN KEY (`office_id`)
            REFERENCES `logistic_company`.`office` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


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
    `is_recieved_in_office` TINYINT     NOT NULL,
    `office_employee_id`    INT         NOT NULL,
    `price`                 FLOAT       NOT NULL,
    `sent_date`             DATETIME    NOT NULL,
    `received_date`         DATETIME    NULL DEFAULT NULL,
    `courier_id`            INT         NOT NULL,
    `company_id`            INT         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_shipment_user1_idx` (`sender_id` ASC) VISIBLE,
    INDEX `fk_shipment_user2_idx` (`receiver_id` ASC) VISIBLE,
    INDEX `fk_shipment_office_employee1_idx` (`office_employee_id` ASC) VISIBLE,
    INDEX `fk_shipment_courier1_idx` (`courier_id` ASC) VISIBLE,
    INDEX `fk_shipment_company1_idx` (`company_id` ASC) VISIBLE,
    CONSTRAINT `fk_shipment_company1`
        FOREIGN KEY (`company_id`)
            REFERENCES `logistic_company`.`company` (`id`),
    CONSTRAINT `fk_shipment_courier1`
        FOREIGN KEY (`courier_id`)
            REFERENCES `logistic_company`.`courier` (`id`),
    CONSTRAINT `fk_shipment_office_employee1`
        FOREIGN KEY (`office_employee_id`)
            REFERENCES `logistic_company`.`office_employee` (`id`),
    CONSTRAINT `fk_shipment_user1`
        FOREIGN KEY (`sender_id`)
            REFERENCES `logistic_company`.`user` (`id`),
    CONSTRAINT `fk_shipment_user2`
        FOREIGN KEY (`receiver_id`)
            REFERENCES `logistic_company`.`user` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
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
    UNIQUE INDEX `unique_tariff_combination` (`price_per_kg` ASC, `office_discount` ASC, `company_id` ASC) VISIBLE,
    INDEX `fk_tariffs_company_idx` (`company_id` ASC) INVISIBLE,
    CONSTRAINT `fk_tarriffs_company1`
        FOREIGN KEY (`company_id`)
            REFERENCES `logistic_company`.`company` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `logistic_company`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistic_company`.`user_roles`
(
    `user_id` INT         NOT NULL,
    `roles`   VARCHAR(45) NULL DEFAULT NULL,
    INDEX `fk_user_roles_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_roles_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `logistic_company`.`user` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

USE `transport_company`;

-- -----------------------------------------------------
-- Table `transport_company`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`person`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(20) NULL DEFAULT NULL,
    `last_name`  VARCHAR(20) NULL DEFAULT NULL,
    `ssn`        VARCHAR(10) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 12
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`client`
(
    `id` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FKr1e0j10i9v9i52l6tqfa69nj0`
        FOREIGN KEY (`id`)
            REFERENCES `transport_company`.`person` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`company`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UKniu8sfil2gxywcru9ah3r4ec5` (`name` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`employee`
(
    `salary`     FLOAT  NULL DEFAULT NULL,
    `id`         INT    NOT NULL,
    `company_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK5v50ed2bjh60n1gc7ifuxmgf4` (`company_id` ASC) VISIBLE,
    CONSTRAINT `FK5v50ed2bjh60n1gc7ifuxmgf4`
        FOREIGN KEY (`company_id`)
            REFERENCES `transport_company`.`company` (`id`),
    CONSTRAINT `FKt824vonkbw2cnqvtmscpuodj9`
        FOREIGN KEY (`id`)
            REFERENCES `transport_company`.`person` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`payload_qualification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`payload_qualification`
(
    `id`            INT         NOT NULL AUTO_INCREMENT,
    `qualification` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UKh5t7uu1xcm344dg1ashacikhn` (`qualification` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`employee_payload_qualification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`employee_payload_qualification`
(
    `employees_id`             INT NOT NULL,
    `payloadQualifications_id` INT NOT NULL,
    PRIMARY KEY (`employees_id`, `payloadQualifications_id`),
    INDEX `FKmk6i0cwh65uayptmq4r42ukei` (`payloadQualifications_id` ASC) VISIBLE,
    CONSTRAINT `FKknqpldlrc2w43y8mf7w92wufm`
        FOREIGN KEY (`employees_id`)
            REFERENCES `transport_company`.`employee` (`id`),
    CONSTRAINT `FKmk6i0cwh65uayptmq4r42ukei`
        FOREIGN KEY (`payloadQualifications_id`)
            REFERENCES `transport_company`.`payload_qualification` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`payload`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`payload`
(
    `id`                      INT          NOT NULL AUTO_INCREMENT,
    `unit`                    VARCHAR(255) NOT NULL,
    `unitValue`               FLOAT        NOT NULL,
    `payloadQualification_id` INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKswnl88op07oxwhv5ap8q4rl8l` (`payloadQualification_id` ASC) VISIBLE,
    CONSTRAINT `FKswnl88op07oxwhv5ap8q4rl8l`
        FOREIGN KEY (`payloadQualification_id`)
            REFERENCES `transport_company`.`payload_qualification` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`payload_unit2`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`payload_unit2`
(
    `Payload_id` INT          NOT NULL,
    `unit2`      VARCHAR(255) NULL DEFAULT NULL,
    INDEX `FKdpuyjkg4a6qtfa404fxnk33gj` (`Payload_id` ASC) VISIBLE,
    CONSTRAINT `FKdpuyjkg4a6qtfa404fxnk33gj`
        FOREIGN KEY (`Payload_id`)
            REFERENCES `transport_company`.`payload` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`vehicle`
(
    `id`                  INT          NOT NULL AUTO_INCREMENT,
    `capacity`            FLOAT        NULL DEFAULT NULL,
    `capacity_unit`       VARCHAR(255) NULL DEFAULT NULL,
    `registration_number` VARCHAR(255) NULL DEFAULT NULL,
    `type`                VARCHAR(255) NULL DEFAULT NULL,
    `company_id`          BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK6fo0502tpr111m29vqj0bhpa4` (`registration_number` ASC) VISIBLE,
    INDEX `FK8l9m1j8m30mdmdcbbt1c4trkd` (`company_id` ASC) VISIBLE,
    CONSTRAINT `FK8l9m1j8m30mdmdcbbt1c4trkd`
        FOREIGN KEY (`company_id`)
            REFERENCES `transport_company`.`company` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`transport_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`transport_order`
(
    `id`              BIGINT       NOT NULL AUTO_INCREMENT,
    `arrival_point`   VARCHAR(255) NOT NULL,
    `departure_point` VARCHAR(255) NOT NULL,
    `end_time`        DATETIME(6)  NOT NULL,
    `price`           FLOAT        NOT NULL,
    `start_time`      DATETIME(6)  NOT NULL,
    `company_id`      BIGINT       NOT NULL,
    `driver_id`       INT          NOT NULL,
    `payload_id`      INT          NOT NULL,
    `vehicle_id`      INT          NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_appsy25nqdnto214y2fcpaewm` (`payload_id` ASC) VISIBLE,
    INDEX `FK5haokb81kpr7aatprblkfcagf` (`company_id` ASC) VISIBLE,
    INDEX `FKtls866qmpvrw4sr9sxcd9463q` (`driver_id` ASC) VISIBLE,
    INDEX `FKouduuslfsc4fjl5w9gkd8ejs4` (`vehicle_id` ASC) VISIBLE,
    CONSTRAINT `FK5haokb81kpr7aatprblkfcagf`
        FOREIGN KEY (`company_id`)
            REFERENCES `transport_company`.`company` (`id`),
    CONSTRAINT `FKksvjkcl5fpkegaqh058yw1p0x`
        FOREIGN KEY (`payload_id`)
            REFERENCES `transport_company`.`payload` (`id`),
    CONSTRAINT `FKouduuslfsc4fjl5w9gkd8ejs4`
        FOREIGN KEY (`vehicle_id`)
            REFERENCES `transport_company`.`vehicle` (`id`),
    CONSTRAINT `FKtls866qmpvrw4sr9sxcd9463q`
        FOREIGN KEY (`driver_id`)
            REFERENCES `transport_company`.`employee` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`receipt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`receipt`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT,
    `client_id` INT    NOT NULL,
    `order_id`  BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UKscnawm1ludyq5oofaxtbtps88` (`client_id` ASC, `order_id` ASC) VISIBLE,
    INDEX `FKr3xggo18j61qhwuhlw5076ioq` (`order_id` ASC) VISIBLE,
    CONSTRAINT `FK7or099q4lc2oy04qbi6i9ykr0`
        FOREIGN KEY (`client_id`)
            REFERENCES `transport_company`.`client` (`id`),
    CONSTRAINT `FKr3xggo18j61qhwuhlw5076ioq`
        FOREIGN KEY (`order_id`)
            REFERENCES `transport_company`.`transport_order` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `transport_company`.`transport_order_client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transport_company`.`transport_order_client`
(
    `orders_id`  BIGINT NOT NULL,
    `clients_id` INT    NOT NULL,
    PRIMARY KEY (`orders_id`, `clients_id`),
    INDEX `FKbporuqoeksxm0ndny4a6lm631` (`clients_id` ASC) VISIBLE,
    CONSTRAINT `FKbporuqoeksxm0ndny4a6lm631`
        FOREIGN KEY (`clients_id`)
            REFERENCES `transport_company`.`client` (`id`),
    CONSTRAINT `FKcss78iww9wkxfacjeb6e3jw0j`
        FOREIGN KEY (`orders_id`)
            REFERENCES `transport_company`.`transport_order` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
