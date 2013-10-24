-- MySQL dump 10.13  Distrib 5.6.14, for osx10.7 (x86_64)
--
-- Host: localhost    Database: Configs
-- ------------------------------------------------------
-- Server version	5.6.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ApplicationOverrides`
--

DROP TABLE IF EXISTS `ApplicationOverrides`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApplicationOverrides` (
  `name` varchar(45) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  `environment` varchar(45) NOT NULL DEFAULT '',
  `application` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`name`,`environment`,`application`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApplicationOverrides`
--

LOCK TABLES `ApplicationOverrides` WRITE;
/*!40000 ALTER TABLE `ApplicationOverrides` DISABLE KEYS */;
INSERT INTO `ApplicationOverrides` VALUES ('jarName','sampleSsa.jar','prod','ssa'),('maxCon','150','qa','ssa'),('serviceA','http://prod/sb','prod','sb'),('serviceA','http://prod/ssa','prod','ssa'),('timeoutMS','1','dev','ssa'),('timeoutMS','1000000','prod','ssa');
/*!40000 ALTER TABLE `ApplicationOverrides` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EnvironmentOverrides`
--

DROP TABLE IF EXISTS `EnvironmentOverrides`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EnvironmentOverrides` (
  `name` varchar(45) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  `environment` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`name`,`environment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EnvironmentOverrides`
--

LOCK TABLES `EnvironmentOverrides` WRITE;
/*!40000 ALTER TABLE `EnvironmentOverrides` DISABLE KEYS */;
INSERT INTO `EnvironmentOverrides` VALUES ('invalid','nevershow','nullEnv'),('maxCon','1','dev'),('maxCon','200','qa'),('numThreads','500','prod'),('serviceA','http://prod','prod'),('timeoutMS','10','dev'),('timeoutMS','300','qa');
/*!40000 ALTER TABLE `EnvironmentOverrides` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Global`
--

DROP TABLE IF EXISTS `Global`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Global` (
  `name` varchar(45) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Global`
--

LOCK TABLES `Global` WRITE;
/*!40000 ALTER TABLE `Global` DISABLE KEYS */;
INSERT INTO `Global` VALUES ('jarName','global.jar'),('maxCon','1000'),('numThreads','100'),('serviceA','http://global:880'),('serviceB','http://global:432'),('timeoutMS','100');
/*!40000 ALTER TABLE `Global` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `History`
--

DROP TABLE IF EXISTS `History`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `History` (
  `name` varchar(32) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  `environment` varchar(45) NOT NULL DEFAULT '',
  `application` varchar(45) NOT NULL DEFAULT '',
  `version` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`version`,`name`,`environment`,`application`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `History`
--

LOCK TABLES `History` WRITE;
/*!40000 ALTER TABLE `History` DISABLE KEYS */;
/*!40000 ALTER TABLE `History` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-10-24  9:06:49
