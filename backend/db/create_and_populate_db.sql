CREATE DATABASE  IF NOT EXISTS `logistic_company` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `logistic_company`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: logistic_company
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(45) NOT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (6,'Create123123'),(1,'DHL'),(2,'Discordia'),(5,'Express'),(4,'komp'),(3,'Speedy');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier`
--

DROP TABLE IF EXISTS `courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier` (
                           `id` int NOT NULL,
                           PRIMARY KEY (`id`),
                           CONSTRAINT `fk_courier_employee1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier`
--

LOCK TABLES `courier` WRITE;
/*!40000 ALTER TABLE `courier` DISABLE KEYS */;
INSERT INTO `courier` VALUES (5),(6),(43),(44),(45),(46),(49),(50);
/*!40000 ALTER TABLE `courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
                            `id` int NOT NULL,
                            `company_id` int NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `fk_employee_company1_idx` (`company_id`) /*!80000 INVISIBLE */,
                            CONSTRAINT `fk_employee_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `fk_employee_user1` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,1),(2,1),(5,6),(6,6),(8,1),(9,1),(15,1),(16,1),(19,1),(20,1),(21,2),(22,2),(23,2),(24,2),(25,2),(26,3),(27,3),(28,3),(29,3),(30,4),(31,4),(32,4),(33,4),(35,4),(36,5),(38,5),(39,5),(40,5),(41,5),(42,5),(43,3),(44,3),(45,4),(46,4),(48,5),(49,1),(50,1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office`
--

DROP TABLE IF EXISTS `office`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `office` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `address` varchar(45) NOT NULL,
                          `company_id` int NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `address_company` (`address`,`company_id`),
                          KEY `fk_office_company1_idx` (`company_id`),
                          CONSTRAINT `fk_office_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office`
--

LOCK TABLES `office` WRITE;
/*!40000 ALTER TABLE `office` DISABLE KEYS */;
INSERT INTO `office` VALUES (5,'blok 108 zh.k Drujba 1',1),(3,'bul Iskarsko shose',1),(11,'bul James Bourchier',2),(14,'bul Maria Luiza',3),(16,'bul Ovcha kupel 55A',4),(15,'bul Ovcha kupel 7',3),(4,'bul Tsar Osvoboditel',1),(13,'bul Tsarigradsko shose 133',3),(12,'bul Vitosha 89B',3),(8,'Busmantsi 34',2),(7,'Krasno selo 12',2),(6,'Manastirski livadi 7',1),(29,'Sofia2',6),(27,'ul 3ti Mart',5),(10,'ul George Washington',2),(9,'ul Montevideo 25',2),(1,'ul Septemvri 9999',1),(2,'ul Tsarigradsko shose',1),(28,'ul Vasil Levski',5),(25,'zh.k Boyana',5),(24,'zh.k Dragalevski Hills',5),(19,'zh.k Lulin 9 - 115 ',4),(17,'zh.k Mladost 1 ',4),(18,'zh.k Mladost 3 blok 115 ',4),(20,'zh.k Moderno predgradie ',4),(21,'zh.k Musagenitsa ',4),(22,'zh.k Simeonovo ul Asen Petrov ',5),(23,'zh.k Simeonovo ul Vazrazhdane ',5),(26,'zh.k Sveta Troitsa',5);
/*!40000 ALTER TABLE `office` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office_employee`
--

DROP TABLE IF EXISTS `office_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `office_employee` (
                                   `id` int NOT NULL,
                                   `office_id` int NOT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `fk_office_employee_office1_idx` (`office_id`),
                                   CONSTRAINT `fk_office_employee_employee1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`) ON DELETE CASCADE,
                                   CONSTRAINT `fk_office_employee_office1` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office_employee`
--

LOCK TABLES `office_employee` WRITE;
/*!40000 ALTER TABLE `office_employee` DISABLE KEYS */;
INSERT INTO `office_employee` VALUES (1,1),(20,2),(16,3),(8,6),(9,6),(19,6),(23,7),(22,8),(25,9),(24,10),(21,11),(29,12),(28,13),(26,14),(27,15),(30,16),(32,17),(33,18),(31,19),(35,21),(40,22),(41,23),(39,24),(38,25),(42,26),(36,27);
/*!40000 ALTER TABLE `office_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `departure_address` varchar(45) NOT NULL,
                            `arrival_address` varchar(45) NOT NULL,
                            `weight` float NOT NULL,
                            `sender_id` int DEFAULT NULL,
                            `receiver_id` int DEFAULT NULL,
                            `is_sent_from_office` tinyint NOT NULL,
                            `is_received_in_office` tinyint NOT NULL,
                            `office_employee_id` int DEFAULT NULL,
                            `price` float NOT NULL,
                            `sent_date` datetime NOT NULL,
                            `received_date` datetime DEFAULT NULL,
                            `courier_id` int DEFAULT NULL,
                            `company_id` int NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
INSERT INTO `shipment` VALUES (1,'lovech','varna',0,1,2,0,0,1,150,'2024-02-11 17:12:38','2024-02-11 17:12:38',NULL,1),(5,'blok 108 zh.k Drujba 1','bul Iskarsko shose',100,15,16,1,0,NULL,450,'2024-02-11 17:12:38',NULL,NULL,1),(6,'bul Iskarsko shose','bul Ovcha kupel 55A',100,15,16,1,0,NULL,450,'2024-02-12 09:30:00',NULL,NULL,1),(7,'bul Iskarsko shose','zh.k Musagenitsa',120,15,NULL,1,0,NULL,540,'2024-02-12 11:45:00',NULL,NULL,1),(8,'bul Tsar Osvoboditel','bul Vitosha 89B',150,16,NULL,1,0,16,675,'2024-02-12 10:00:00',NULL,43,1),(9,'bul Tsar Osvoboditel','zh.k Simeonovo ul Asen Petrov',180,16,NULL,1,0,16,810,'2024-02-12 12:15:00',NULL,43,1),(10,'bul Vitosha 89B','bul Maria Luiza',200,NULL,NULL,1,0,NULL,900,'2024-02-12 11:00:00',NULL,44,1),(11,'bul Vitosha 89B','zh.k Simeonovo ul Vazrazhdane',220,NULL,19,1,0,NULL,990,'2024-02-12 13:30:00',NULL,44,1),(12,'zh.k Lulin 9 - 115','zh.k Musagenitsa',130,20,21,1,0,NULL,585,'2024-02-12 09:45:00',NULL,46,1),(13,'zh.k Lulin 9 - 115','ul Vasil Levski',180,20,22,1,0,NULL,810,'2024-02-12 11:15:00',NULL,46,1),(14,'bul Tsar Osvoboditel','ul Montevideo 25',200,21,22,1,0,20,900,'2024-02-12 12:30:00',NULL,NULL,1),(15,'bul Tsar Osvoboditel','bul James Bourchier',160,21,23,1,0,20,720,'2024-02-12 14:45:00',NULL,NULL,1),(16,'bul Iskarsko shose','bul Tsarigradsko shose 133',130,16,NULL,1,0,16,585,'2024-02-12 09:30:00',NULL,NULL,1),(17,'bul Iskarsko shose','bul Tsarigradsko shose 133',130,16,NULL,1,0,16,585,'2024-02-12 09:30:00',NULL,43,1),(19,'bul Iskarsko shose','bul Tsarigradsko shose 133',170,NULL,NULL,1,0,NULL,765,'2024-02-12 11:45:00',NULL,NULL,1),(20,'bul Tsarigradsko shose 133','bul Ovcha kupel 7',180,28,26,1,0,28,0,'2024-02-12 10:00:00',NULL,44,3),(21,'bul Tsarigradsko shose 133','bul Maria Luiza',190,28,27,1,0,28,0,'2024-02-12 12:15:00',NULL,44,3),(22,'zh.k Lulin 9 - 115','bul Ovcha kupel 55A',140,31,26,1,0,31,13860,'2024-02-12 09:30:00',NULL,NULL,4),(23,'zh.k Lulin 9 - 115','zh.k Musagenitsa',160,31,28,1,0,31,15840,'2024-02-12 11:45:00',NULL,NULL,4),(24,'bul Ovcha kupel 55A','zh.k Mladost 1',150,30,27,1,0,30,14850,'2024-02-12 09:30:00',NULL,NULL,4),(25,'bul Ovcha kupel 55A','bul Tsar Osvoboditel',170,30,28,1,0,30,16830,'2024-02-12 11:45:00',NULL,NULL,4),(26,'zh.k Mladost 3 blok 115','bul Tsarigradsko shose 133',180,33,27,1,0,33,17820,'2024-02-12 10:00:00',NULL,NULL,4),(27,'zh.k Mladost 3 blok 115','zh.k Lulin 9 - 115',190,33,28,1,0,33,18810,'2024-02-12 12:15:00',NULL,NULL,4),(28,'zh.k Mladost 1','bul Maria Luiza',140,32,26,1,0,32,13860,'2024-02-12 09:30:00',NULL,NULL,4),(29,'zh.k Mladost 1','bul Ovcha kupel 55A',160,32,27,1,0,32,15840,'2024-02-12 11:45:00',NULL,NULL,4),(31,'XXXXX','YYYY',100,NULL,35,0,0,NULL,0,'2024-03-01 00:00:00',NULL,46,4),(32,'XXXXX','YYYY',100,NULL,NULL,0,0,20,0,'2024-03-01 00:00:00',NULL,NULL,1),(33,'XXXXX','YYYY',100,NULL,35,0,0,NULL,0,'2024-03-01 00:00:00',NULL,NULL,1),(34,'XXXXX','YYYY',100,NULL,35,0,0,42,100,'2024-03-01 00:00:00',NULL,NULL,5),(35,'XXXXX','YYYY',100,20,NULL,0,0,1,0,'2024-03-01 00:00:00',NULL,NULL,1),(36,'XXXXX','YYYY',100,1,8,0,0,1,0,'2024-03-01 00:00:00',NULL,NULL,1),(37,'XXXXX','YYYY',100,16,2,0,0,1,0,'2024-03-01 00:00:00',NULL,NULL,1),(38,'XXXXX','YYYY',100,NULL,11,0,0,1,0,'2024-03-01 00:00:00',NULL,NULL,1),(39,'XXXXX','YYYY',100,19,2,0,0,1,0,'2024-03-01 00:00:00',NULL,NULL,1),(40,'XXXXX','YYYY',100,15,15,0,0,1,0,'2024-03-01 00:00:00',NULL,NULL,1),(41,'bul Tsarigradsko shose 133','Narniq',180,1,2,0,0,1,0,'2024-02-12 00:00:00',NULL,NULL,1),(42,'Sofia','Pleven',40,2,5,0,0,1,0,'2024-03-23 00:00:00',NULL,49,1),(43,'Sofia','Pleven',40,1,1,0,0,1,0,'2024-03-05 00:00:00',NULL,50,1),(44,'Sofia','Pleven1',0,6,2,0,0,1,0,'2024-03-08 00:00:00',NULL,50,1),(45,'Sofia','Pleven1',0,6,2,0,0,1,0,'2024-03-08 00:00:00',NULL,50,1),(46,'Sofia','Pleven2',0,6,1,0,0,1,0,'2024-03-07 00:00:00',NULL,49,1),(47,'bul. Bulgaria 104','bul. Slivnica 94',0,19,20,0,0,1,0,'2024-03-12 00:00:00',NULL,49,1);
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff`
--

DROP TABLE IF EXISTS `tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tariff` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `price_per_kg` float NOT NULL,
                          `office_discount` float NOT NULL,
                          `company_id` int NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `unique_tariff_combination` (`price_per_kg`,`office_discount`,`company_id`),
                          KEY `fk_tariffs_company_idx` (`company_id`) /*!80000 INVISIBLE */,
                          CONSTRAINT `fk_tarriffs_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `tariff` WRITE;
/*!40000 ALTER TABLE `tariff` DISABLE KEYS */;
INSERT INTO `tariff` VALUES (1,5,10,1),(2,10,5,2),(3,50,100,3),(4,100,1,4);
/*!40000 ALTER TABLE `tariff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(45) NOT NULL,
                        `password` varchar(256) NOT NULL,
                        `first_name` varchar(45) NOT NULL,
                        `last_name` varchar(45) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'alex','123456789','alex','alexov'),(2,'ivo','123456789','ivo','ivov'),(5,'ivancho','123456789','Ivan','Petrov'),(6,'mincho','123456789','Mincho','Spasov'),(7,'nasko','123456789','Atanas','Penchev'),(8,'milen','123456789','milen','Bobaliev'),(9,'tuhlen@abv.bg','123456789','Tuhlen','Tuhlenov'),(10,'krasko@abv.bg','123456789','Krasimir','Panchev'),(11,'spas@abv.bg','123456789','Spas','Spasov'),(12,'destroyer12@abv.bg','123456789','Antonio','Hadzhiev'),(13,'creator000@abv.bg','123456789','Ivailo','Rusenov'),(15,'kasier','123456789','milen','spasovski'),(16,'pepi','123456789','petromira','kircheva'),(19,'Lili','123456789','Liliana','Peneva'),(20,'Tony','123456789','Antoan','Parashkevov'),(21,'Toncho','123456789','Tony','Tilev'),(22,'Bobby','123456789','Boyan','Anastasov'),(23,'Krasi','123456789','Krasimir','Kralev'),(24,'Gosho','123456789','Georgi','Kralev'),(25,'Nena','123456789','Nena','Nenova'),(26,'Rusen','123456789','Rusen','Rusanov'),(27,'user123','123456789','Ivan','Ivanov'),(28,'user456','123456789','Petar','Petrov'),(29,'user789','123456789','Maria','Ivanova'),(30,'user101','123456789','Georgi','Georgiev'),(31,'user102','123456789','Stoyan','Stoyanov'),(32,'user103','123456789','Milena','Georgieva'),(33,'user104','123456789','Nikolay','Nikolov'),(35,'user106','123456789','Dragomir','Dragomirov'),(36,'user107','123456789','Elena','Petrova'),(38,'user109','123456789','Boyko','Boykov'),(39,'user110','123456789','Dimitar','Dimitrov'),(40,'user111','123456789','Asen','Asenov'),(41,'user112','123456789','Vazrazhdane','Vazrazhdanov'),(42,'user113','123456789','Svetla','Svetlova'),(43,'user114','123456789','Anna','Smith'),(44,'user115','123456789','John','Johnson'),(45,'user116','123456789','Emma','Davis'),(46,'user117','123456789','Daniel','Wilson'),(48,'user119','123456789','William','Anderson'),(49,'user120','123456789','Sophia','Brown'),(50,'user121','123456789','James','Garcia'),(52,'qnko','123456789','ivan','ivanov'),(53,'beti','123456789','Eliza','Di'),(54,'asdasd','123123123','asdasd','asdasd'),(55,'asdasd1','asdasdasdasd','asdasd1','asdasd1'),(56,'asdasd12','asdasdasdasd','asdasd12','asdasd12'),(57,'asdasd124','asdasdasdasd','asdasd124','asdasd124'),(58,'beti123','123123123','beti123','beti123'),(59,'beti1234','123123123','beti1234','beti123'),(60,'beti12345','123123123','beti12345','beti12345'),(61,'toni123123','123123123','toni123123','toni123123'),(65,'toni1231234567','123123123','toni123123','toni123123'),(66,'ivo69','123456789','ivo','ivov'),(67,'antoan','123123123','Antoan','Parashkevov'),(70,'dinodino','123456789','dinodino','dinodino'),(76,'boko123','123456789','bokake','bokakov');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `user_id` int NOT NULL,
                              `roles` varchar(45) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              KEY `fk_User_roles_user1_idx` (`user_id`),
                              CONSTRAINT `fk_User_roles_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1,'ADMIN'),(3,5,'USER'),(4,6,'USER'),(5,7,'USER'),(6,8,'USER'),(7,9,'USER'),(8,10,'USER'),(9,11,'USER'),(10,12,'USER'),(11,13,'USER'),(13,7,'EMPLOYEE'),(14,9,'EMPLOYEE'),(15,11,'EMPLOYEE'),(16,12,'EMPLOYEE'),(18,15,'EMPLOYEE'),(19,16,'EMPLOYEE'),(22,19,'EMPLOYEE'),(23,20,'EMPLOYEE'),(24,21,'EMPLOYEE'),(25,22,'EMPLOYEE'),(26,23,'EMPLOYEE'),(27,24,'EMPLOYEE'),(28,25,'EMPLOYEE'),(29,26,'EMPLOYEE'),(30,27,'EMPLOYEE'),(31,28,'EMPLOYEE'),(32,29,'EMPLOYEE'),(33,30,'EMPLOYEE'),(34,31,'EMPLOYEE'),(35,32,'EMPLOYEE'),(36,33,'EMPLOYEE'),(38,35,'EMPLOYEE'),(39,36,'EMPLOYEE'),(41,38,'EMPLOYEE'),(42,39,'EMPLOYEE'),(43,40,'EMPLOYEE'),(44,41,'EMPLOYEE'),(45,42,'EMPLOYEE'),(46,43,'EMPLOYEE'),(47,44,'EMPLOYEE'),(48,45,'EMPLOYEE'),(49,46,'EMPLOYEE'),(51,48,'EMPLOYEE'),(52,49,'EMPLOYEE'),(53,50,'EMPLOYEE'),(54,8,'ADMIN'),(55,1,'EMPLOYEE'),(56,52,'USER'),(57,53,'USER'),(58,54,'USER'),(59,55,'USER'),(60,56,'USER'),(61,57,'USER'),(62,58,'USER'),(63,59,'USER'),(64,60,'USER'),(65,61,'USER'),(69,65,'USER'),(70,66,'USER'),(71,6,'EMPLOYEE'),(72,5,'EMPLOYEE'),(73,8,'EMPLOYEE'),(74,67,'USER'),(77,70,'USER'),(83,76,'USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-10 22:48:03
