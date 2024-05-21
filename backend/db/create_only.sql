CREATE DATABASE IF NOT EXISTS `logistic_company` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION = 'N' */;
USE `logistic_company`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: logistic_company
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company`
(
    `id`   int         NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `courier`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier`
(
    `id` int NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_courier_employee1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee`
(
    `id`         int NOT NULL,
    `company_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_employee_company1_idx` (`company_id`) /*!80000 INVISIBLE */,
    CONSTRAINT `fk_employee_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_employee_user1` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `office`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `office`
(
    `id`         int         NOT NULL AUTO_INCREMENT,
    `address`    varchar(45) NOT NULL,
    `company_id` int         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `address_company` (`address`, `company_id`),
    KEY `fk_office_company1_idx` (`company_id`),
    CONSTRAINT `fk_office_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `office_employee`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `office_employee`
(
    `id`        int NOT NULL,
    `office_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_office_employee_office1_idx` (`office_id`),
    CONSTRAINT `fk_office_employee_employee1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_office_employee_office1` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment`
(
    `id`                    int         NOT NULL AUTO_INCREMENT,
    `departure_address`     varchar(45) NOT NULL,
    `arrival_address`       varchar(45) NOT NULL,
    `weight`                float       NOT NULL,
    `sender_id`             int      DEFAULT NULL,
    `receiver_id`           int      DEFAULT NULL,
    `is_sent_from_office`   tinyint     NOT NULL,
    `is_received_in_office` tinyint     NOT NULL,
    `office_employee_id`    int      DEFAULT NULL,
    `price`                 float       NOT NULL,
    `sent_date`             datetime    NOT NULL,
    `received_date`         datetime DEFAULT NULL,
    `courier_id`            int      DEFAULT NULL,
    `company_id`            int         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_shipment_user1_idx` (`sender_id`),
    KEY `fk_shipment_user2_idx` (`receiver_id`),
    KEY `fk_shipment_office_employee1_idx` (`office_employee_id`),
    KEY `fk_shipment_courier1_idx` (`courier_id`),
    KEY `fk_shipment_company1_idx` (`company_id`),
    CONSTRAINT `fk_shipment_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_shipment_courier1` FOREIGN KEY (`courier_id`) REFERENCES `courier` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_shipment_office_employee1` FOREIGN KEY (`office_employee_id`) REFERENCES `office_employee` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_shipment_user1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_shipment_user2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 48
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `tariff`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tariff`
(
    `id`              int   NOT NULL AUTO_INCREMENT,
    `price_per_kg`    float NOT NULL,
    `office_discount` float NOT NULL,
    `company_id`      int   NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_tariff_combination` (`price_per_kg`, `office_discount`, `company_id`),
    KEY `fk_tariffs_company_idx` (`company_id`) /*!80000 INVISIBLE */,
    CONSTRAINT `fk_tarriffs_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `username`   varchar(45)  NOT NULL,
    `password`   varchar(256) NOT NULL,
    `first_name` varchar(45)  NOT NULL,
    `last_name`  varchar(45)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 77
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `roles`   varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_User_roles_user1_idx` (`user_id`),
    CONSTRAINT `fk_User_roles_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 84
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2024-04-10 22:48:03
