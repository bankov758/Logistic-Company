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
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company`
    DISABLE KEYS */;
INSERT INTO `company`
VALUES (4, 'komp'),
       (3, 'Speedy'),
       (1, 'YYYY');
/*!40000 ALTER TABLE `company`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier`
--

DROP TABLE IF EXISTS `courier`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier`
(
    `id` int NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_courier_employee1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier`
--

LOCK TABLES `courier` WRITE;
/*!40000 ALTER TABLE `courier`
    DISABLE KEYS */;
INSERT INTO `courier`
VALUES (2);
/*!40000 ALTER TABLE `courier`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee`
(
    `id`         int NOT NULL,
    `company_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_employee_company1_idx` (`company_id`) /*!80000 INVISIBLE */,
    CONSTRAINT `fk_employee_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
    CONSTRAINT `fk_employee_user1` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee`
    DISABLE KEYS */;
INSERT INTO `employee`
VALUES (1, 1),
       (2, 1);
/*!40000 ALTER TABLE `employee`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office`
--

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
    CONSTRAINT `fk_office_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office`
--

LOCK TABLES `office` WRITE;
/*!40000 ALTER TABLE `office`
    DISABLE KEYS */;
INSERT INTO `office`
VALUES (1, 'lovech', 1);
/*!40000 ALTER TABLE `office`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office_employee`
--

DROP TABLE IF EXISTS `office_employee`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `office_employee`
(
    `id`        int NOT NULL,
    `office_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_office_employee_office1_idx` (`office_id`),
    CONSTRAINT `fk_office_employee_employee1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`),
    CONSTRAINT `fk_office_employee_office1` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office_employee`
--

LOCK TABLES `office_employee` WRITE;
/*!40000 ALTER TABLE `office_employee`
    DISABLE KEYS */;
INSERT INTO `office_employee`
VALUES (1, 1);
/*!40000 ALTER TABLE `office_employee`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment`
(
    `id`                    int         NOT NULL AUTO_INCREMENT,
    `departure_address`     varchar(45) NOT NULL,
    `arrival_address`       varchar(45) NOT NULL,
    `weight`                float       NOT NULL,
    `sender_id`             int         NOT NULL,
    `receiver_id`           int         NOT NULL,
    `is_sent_from_office`   tinyint     NOT NULL,
    `is_recieved_in_office` tinyint     NOT NULL,
    `office_employee_id`    int         NOT NULL,
    `price`                 float       NOT NULL,
    `sent_date`             datetime    NOT NULL,
    `received_date`         datetime DEFAULT NULL,
    `courier_id`            int         NOT NULL,
    `company_id`            int         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_shipment_user1_idx` (`sender_id`),
    KEY `fk_shipment_user2_idx` (`receiver_id`),
    KEY `fk_shipment_office_employee1_idx` (`office_employee_id`),
    KEY `fk_shipment_courier1_idx` (`courier_id`),
    KEY `fk_shipment_company1_idx` (`company_id`),
    CONSTRAINT `fk_shipment_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
    CONSTRAINT `fk_shipment_courier1` FOREIGN KEY (`courier_id`) REFERENCES `courier` (`id`),
    CONSTRAINT `fk_shipment_office_employee1` FOREIGN KEY (`office_employee_id`) REFERENCES `office_employee` (`id`),
    CONSTRAINT `fk_shipment_user1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_shipment_user2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment`
    DISABLE KEYS */;
INSERT INTO `shipment`
VALUES (1, 'lovech', 'varna', 0, 1, 2, 0, 0, 1, 150, '2024-02-11 17:12:38', '2024-02-11 17:12:38', 2, 1),
       (3, 'lovech', 'varna', 0, 1, 2, 0, 0, 1, 100, '2024-02-11 17:12:38', NULL, 2, 1);
/*!40000 ALTER TABLE `shipment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff`
--

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
    CONSTRAINT `fk_tarriffs_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `tariff` WRITE;
/*!40000 ALTER TABLE `tariff`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tariff`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `id`         int         NOT NULL AUTO_INCREMENT,
    `username`   varchar(45) NOT NULL,
    `password`   varchar(45) NOT NULL,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user`
VALUES (1, 'alex', '123456789', 'alex', 'alexov'),
       (2, 'ivo', '123456789', 'ivo', 'ivov'),
       (4, 'pesho', '1234567689', 'pesho', 'peshov');
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles`
(
    `user_id` int NOT NULL,
    `roles`   varchar(45) DEFAULT NULL,
    KEY `fk_user_roles_user1_idx` (`user_id`),
    CONSTRAINT `fk_user_roles_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles`
    DISABLE KEYS */;
INSERT INTO `user_roles`
VALUES (1, 'ADMIN'),
       (4, 'USER');
/*!40000 ALTER TABLE `user_roles`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2024-02-24 14:31:48
