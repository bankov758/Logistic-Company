-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: logistic_company
-- ------------------------------------------------------
-- Server version	8.0.36

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'DHL'),(2,'Discordia'),(5,'Express'),(4,'komp'),(3,'Speedy');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_has_client`
--

DROP TABLE IF EXISTS `company_has_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_has_client` (
  `company_id` int NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`company_id`,`client_id`),
  KEY `fk_company_has_user_user2_idx` (`client_id`),
  KEY `fk_company_has_user_company2_idx` (`company_id`),
  CONSTRAINT `fk_company_has_user_company2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_company_has_user_user2` FOREIGN KEY (`client_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_has_client`
--

LOCK TABLES `company_has_client` WRITE;
/*!40000 ALTER TABLE `company_has_client` DISABLE KEYS */;
/*!40000 ALTER TABLE `company_has_client` ENABLE KEYS */;
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
  CONSTRAINT `fk_courier_employee1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier`
--

LOCK TABLES `courier` WRITE;
/*!40000 ALTER TABLE `courier` DISABLE KEYS */;
INSERT INTO `courier` VALUES (2),(43),(44),(45),(46),(47),(48),(49),(50);
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
  CONSTRAINT `fk_employee_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_employee_user1` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,1),(2,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,2),(22,2),(23,2),(24,2),(25,2),(26,3),(27,3),(28,3),(29,3),(30,4),(31,4),(32,4),(33,4),(34,4),(35,4),(36,5),(37,5),(38,5),(39,5),(40,5),(41,5),(42,5),(43,3),(44,3),(45,4),(46,4),(47,5),(48,5),(49,1),(50,1);
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
  CONSTRAINT `fk_office_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office`
--

LOCK TABLES `office` WRITE;
/*!40000 ALTER TABLE `office` DISABLE KEYS */;
INSERT INTO `office` VALUES (5,'blok 108 zh.k Drujba 1',1),(3,'bul Iskarsko shose',1),(11,'bul James Bourchier',2),(14,'bul Maria Luiza',3),(16,'bul Ovcha kupel 55A',4),(15,'bul Ovcha kupel 7',3),(4,'bul Tsar Osvoboditel',1),(13,'bul Tsarigradsko shose 133',3),(12,'bul Vitosha 89B',3),(8,'Busmantsi 34',2),(7,'Krasno selo 12',2),(1,'lovech',1),(6,'Manastirski livadi 7',1),(27,'ul 3ti Mart',5),(10,'ul George Washington',2),(9,'ul Montevideo 25',2),(2,'ul Tsarigradsko shose',1),(28,'ul Vasil Levski',5),(25,'zh.k Boyana',5),(24,'zh.k Dragalevski Hills',5),(19,'zh.k Lulin 9 - 115 ',4),(17,'zh.k Mladost 1 ',4),(18,'zh.k Mladost 3 blok 115 ',4),(20,'zh.k Moderno predgradie ',4),(21,'zh.k Musagenitsa ',4),(22,'zh.k Simeonovo ul Asen Petrov ',5),(23,'zh.k Simeonovo ul Vazrazhdane ',5),(26,'zh.k Sveta Troitsa',5);
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
  CONSTRAINT `fk_office_employee_employee1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_office_employee_office1` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office_employee`
--

LOCK TABLES `office_employee` WRITE;
/*!40000 ALTER TABLE `office_employee` DISABLE KEYS */;
INSERT INTO `office_employee` VALUES (1,1),(18,1),(20,2),(16,3),(17,4),(15,5),(19,6),(23,7),(22,8),(25,9),(24,10),(21,11),(29,12),(28,13),(26,14),(27,15),(30,16),(32,17),(33,18),(31,19),(34,20),(35,21),(40,22),(41,23),(39,24),(38,25),(42,26),(36,27),(37,28);
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
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  `is_sent_from_office` tinyint NOT NULL,
  `is_received_in_office` tinyint NOT NULL,
  `office_employee_id` int NOT NULL,
  `price` float NOT NULL,
  `sent_date` datetime NOT NULL,
  `received_date` datetime DEFAULT NULL,
  `courier_id` int NOT NULL,
  `company_id` int NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
INSERT INTO `shipment` VALUES (1,'lovech','varna',0,1,2,0,0,1,150,'2024-02-11 17:12:38','2024-02-11 17:12:38',2,1),(3,'lovech','varna',0,1,2,0,0,1,100,'2024-02-11 17:12:38',NULL,2,1),(5,'blok 108 zh.k Drujba 1','bul Iskarsko shose',100,15,16,1,0,15,450,'2024-02-11 17:12:38',NULL,2,1),(6,'bul Iskarsko shose','bul Ovcha kupel 55A',100,15,16,1,0,15,450,'2024-02-12 09:30:00',NULL,2,1),(7,'bul Iskarsko shose','zh.k Musagenitsa',120,15,17,1,0,15,540,'2024-02-12 11:45:00',NULL,2,1),(8,'bul Tsar Osvoboditel','bul Vitosha 89B',150,16,17,1,0,16,675,'2024-02-12 10:00:00',NULL,43,1),(9,'bul Tsar Osvoboditel','zh.k Simeonovo ul Asen Petrov',180,16,18,1,0,16,810,'2024-02-12 12:15:00',NULL,43,1),(10,'bul Vitosha 89B','bul Maria Luiza',200,17,18,1,0,17,900,'2024-02-12 11:00:00',NULL,44,1),(11,'bul Vitosha 89B','zh.k Simeonovo ul Vazrazhdane',220,17,19,1,0,17,990,'2024-02-12 13:30:00',NULL,44,1),(12,'zh.k Lulin 9 - 115','zh.k Musagenitsa',130,20,21,1,0,18,585,'2024-02-12 09:45:00',NULL,46,1),(13,'zh.k Lulin 9 - 115','ul Vasil Levski',180,20,22,1,0,18,810,'2024-02-12 11:15:00',NULL,46,1),(14,'bul Tsar Osvoboditel','ul Montevideo 25',200,21,22,1,0,20,900,'2024-02-12 12:30:00',NULL,47,1),(15,'bul Tsar Osvoboditel','bul James Bourchier',160,21,23,1,0,20,720,'2024-02-12 14:45:00',NULL,47,1),(16,'bul Iskarsko shose','bul Tsarigradsko shose 133',130,16,17,1,0,16,585,'2024-02-12 09:30:00',NULL,48,1),(17,'bul Iskarsko shose','bul Tsarigradsko shose 133',130,16,17,1,0,16,585,'2024-02-12 09:30:00',NULL,43,1),(18,'bul Iskarsko shose','bul Ovcha kupel 55A',130,14,16,1,0,15,585,'2024-02-12 09:30:00',NULL,49,1),(19,'bul Iskarsko shose','bul Tsarigradsko shose 133',170,14,17,1,0,15,765,'2024-02-12 11:45:00',NULL,49,1),(20,'bul Tsarigradsko shose 133','bul Ovcha kupel 7',180,28,26,1,0,28,0,'2024-02-12 10:00:00',NULL,44,3),(21,'bul Tsarigradsko shose 133','bul Maria Luiza',190,28,27,1,0,28,0,'2024-02-12 12:15:00',NULL,44,3),(22,'zh.k Lulin 9 - 115','bul Ovcha kupel 55A',140,31,26,1,0,31,13860,'2024-02-12 09:30:00',NULL,48,4),(23,'zh.k Lulin 9 - 115','zh.k Musagenitsa',160,31,28,1,0,31,15840,'2024-02-12 11:45:00',NULL,48,4),(24,'bul Ovcha kupel 55A','zh.k Mladost 1',150,30,27,1,0,30,14850,'2024-02-12 09:30:00',NULL,47,4),(25,'bul Ovcha kupel 55A','bul Tsar Osvoboditel',170,30,28,1,0,30,16830,'2024-02-12 11:45:00',NULL,47,4),(26,'zh.k Mladost 3 blok 115','bul Tsarigradsko shose 133',180,33,27,1,0,33,17820,'2024-02-12 10:00:00',NULL,48,4),(27,'zh.k Mladost 3 blok 115','zh.k Lulin 9 - 115',190,33,28,1,0,33,18810,'2024-02-12 12:15:00',NULL,48,4),(28,'zh.k Mladost 1','bul Maria Luiza',140,32,26,1,0,32,13860,'2024-02-12 09:30:00',NULL,47,4),(29,'zh.k Mladost 1','bul Ovcha kupel 55A',160,32,27,1,0,32,15840,'2024-02-12 11:45:00',NULL,47,4);
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
  CONSTRAINT `fk_tarriffs_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
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
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'alex','123456789','alex','alexov'),(2,'ivo','123456789','ivo','ivov'),(4,'pesho','1234567689','pesho','peshov'),(5,'ivancho','1234567890','Ivan','Petrov'),(6,'mincho','1234567890','Mincho','Spasov'),(7,'nasko','1234567890','Atanas','Penchev'),(8,'milen','1234567890','milen','Bobaliev'),(9,'tuhlen@abv.bg','1234567890','Tuhlen','Tuhlenov'),(10,'krasko@abv.bg','1234567890','Krasimir','Panchev'),(11,'spas@abv.bg','1234567890','Spas','Spasov'),(12,'destroyer12@abv.bg','1234567890','Antonio','Hadzhiev'),(13,'creator000@abv.bg','1234567890','Ivailo','Rusenov'),(14,'vinland_saga@abv.bg','1234567890','Thorfinn','Bankov'),(15,'kasier','1234567689','milen','spasovski'),(16,'pepi','1234567689','petromira','kircheva'),(17,'godzi','1234567689','georgi','trayanov'),(18,'DPS','1234567689','Delyan','Peevski'),(19,'Lili','1234567689','Liliana','Peneva'),(20,'Tony','1234567689','Antoan','Parashkevov'),(21,'Toncho','1234567689','Tony','Tilev'),(22,'Bobby','1234567689','Boyan','Anastasov'),(23,'Krasi','1234567689','Krasimir','Kralev'),(24,'Gosho','1234567689','Georgi','Kralev'),(25,'Nena','1234567689','Nena','Nenova'),(26,'Rusen','1234567689','Rusen','Rusanov'),(27,'user123','password123','Ivan','Ivanov'),(28,'user456','securepass789','Petar','Petrov'),(29,'user789','1234567890','Maria','Ivanova'),(30,'user101','pass123456','Georgi','Georgiev'),(31,'user102','987654321','Stoyan','Stoyanov'),(32,'user103','mypass123','Milena','Georgieva'),(33,'user104','nikpass123','Nikolay','Nikolov'),(34,'user105','svet123456','Svetoslava','Stoyanova'),(35,'user106','dragopass123','Dragomir','Dragomirov'),(36,'user107','elenapass123','Elena','Petrova'),(37,'user108','vasilpass123','Vasil','Vasilev'),(38,'user109','boykopass123','Boyko','Boykov'),(39,'user110','dimitpass123','Dimitar','Dimitrov'),(40,'user111','asenpass123','Asen','Asenov'),(41,'user112','vazrazhdanepass123','Vazrazhdane','Vazrazhdanov'),(42,'user113','svetlapass123','Svetla','Svetlova'),(43,'user114','annapass123','Anna','Smith'),(44,'user115','johnpass123','John','Johnson'),(45,'user116','emmapass123','Emma','Davis'),(46,'user117','danielpass123','Daniel','Wilson'),(47,'user118','oliviapass123','Olivia','Martinez'),(48,'user119','williampass123','William','Anderson'),(49,'user120','sophiapass123','Sophia','Brown'),(50,'user121','jamespass123','James','Garcia');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User_roles`
--

DROP TABLE IF EXISTS `User_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `roles` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_User_roles_user1_idx` (`user_id`),
  CONSTRAINT `fk_User_roles_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User_roles`
--

LOCK TABLES `User_roles` WRITE;
/*!40000 ALTER TABLE `User_roles` DISABLE KEYS */;
INSERT INTO `User_roles` VALUES (1, 1, 'ADMIN'), (2, 4, 'USER'), (3, 5, 'USER'), (4, 6, 'USER'), (5, 7, 'USER'), (6, 8, 'USER'), (7, 9, 'USER'), (8, 10, 'USER'), (9, 11, 'USER'), (10, 12, 'USER'), (11, 13, 'USER'), (12, 14, 'USER'), (13, 7, 'EMPLOYEE'), (14, 9, 'EMPLOYEE'), (15, 11, 'EMPLOYEE'), (16, 12, 'EMPLOYEE'), (17, 14, 'EMPLOYEE'), (18, 15, 'EMPLOYEE'), (19, 16, 'EMPLOYEE'), (20, 17, 'EMPLOYEE'), (21, 18, 'EMPLOYEE'), (22, 19, 'EMPLOYEE'), (23, 20, 'EMPLOYEE'), (24, 21, 'EMPLOYEE'), (25, 22, 'EMPLOYEE'), (26, 23, 'EMPLOYEE'), (27, 24, 'EMPLOYEE'), (28, 25, 'EMPLOYEE'), (29, 26, 'EMPLOYEE'), (30, 27, 'EMPLOYEE'), (31, 28, 'EMPLOYEE'), (32, 29, 'EMPLOYEE'), (33, 30, 'EMPLOYEE'), (34, 31, 'EMPLOYEE'), (35, 32, 'EMPLOYEE'), (36, 33, 'EMPLOYEE'), (37, 34, 'EMPLOYEE'), (38, 35, 'EMPLOYEE'), (39, 36, 'EMPLOYEE'), (40, 37, 'EMPLOYEE'), (41, 38, 'EMPLOYEE'), (42, 39, 'EMPLOYEE'), (43, 40, 'EMPLOYEE'), (44, 41, 'EMPLOYEE'), (45, 42, 'EMPLOYEE'), (46, 43, 'EMPLOYEE'), (47, 44, 'EMPLOYEE'), (48, 45, 'EMPLOYEE'), (49, 46, 'EMPLOYEE'), (50, 47, 'EMPLOYEE'), (51, 48, 'EMPLOYEE'), (52, 49, 'EMPLOYEE'), (53, 50, 'EMPLOYEE');
/*!40000 ALTER TABLE `User_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-27 13:30:50
