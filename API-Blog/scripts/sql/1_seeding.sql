-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: blog
-- ------------------------------------------------------
-- Server version	8.0.25-0ubuntu0.20.04.1

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `count_view` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `last_updated_at` datetime(6) DEFAULT NULL,
  `published_at` datetime(6) DEFAULT NULL,
  `scope` tinyint NOT NULL DEFAULT '0',
  `slug` varchar(255) NOT NULL,
  `status` tinyint NOT NULL DEFAULT '0',
  `thumbnail` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `transliterated` varchar(255) NOT NULL,
  `type` int DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lc76j4bqg2jrk06np18eve5yj` (`slug`),
  KEY `IDXaxp71ccxxbow51kwcjc8vcoxk` (`status`),
  KEY `FKbc2qerk3l47javnl2yvn51uoi` (`user_id`),
  KEY `FKy5kkohbk00g0w88fi05k2hcw` (`category_id`),
  CONSTRAINT `FKbc2qerk3l47javnl2yvn51uoi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKy5kkohbk00g0w88fi05k2hcw` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'Bai viet PHP 9',0,'2021-09-03 16:20:45.715096',NULL,'2021-09-03 17:04:55.887184','2021-09-03 17:04:55.886051',0,'c9cf5702-0a24-40d5-a753-fde7ff05d863',3,'http://localhost:3000/images/thumbnail.jpg','Bai viet so 1 cua #user 1','bai-viet-so-1-cua-user-1',0,3,1),(2,'Bai viet PHP 9',0,'2021-09-03 16:43:04.325635',NULL,'2021-09-03 17:04:25.149596','2021-09-03 17:04:25.147422',0,'b93a8135-7921-4970-8f5e-0a5b50ef0e52',3,'http://localhost:3000/images/thumbnail.jpg','Bai viet so 2 cua #user 1','bai-viet-so-2-cua-user-1',0,3,1),(3,'Bai viet PHP 9',0,'2021-09-03 16:50:47.271107',NULL,'2021-09-03 17:04:32.214778','2021-09-03 17:04:32.213871',0,'3af1c4ac-b244-42dc-ad7e-ef6d1f8f9bc5',3,'http://localhost:3000/images/thumbnail.jpg','Bai viet so 3 cua #user 1','bai-viet-so-3-cua-user-1',0,3,1),(5,'Bai viet bao gom cac bai sau:',0,'2021-09-03 17:10:00.476007',NULL,'2021-09-03 22:27:08.333868','2021-09-03 22:27:08.269651',0,'c41a998a-75fb-43e4-8eb1-c6fb415e9213',3,'http://localhost:3000/images/thumbnail.jpg','Series Lap trinh cua #User 1','series-lap-trinh-cua-user-1',1,3,1);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_participant`
--

DROP TABLE IF EXISTS `article_participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_participant` (
  `article_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`article_id`,`user_id`),
  KEY `FK5v1e8f8ebjlkbqc11i8si4b8r` (`user_id`),
  CONSTRAINT `FK5v1e8f8ebjlkbqc11i8si4b8r` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrclgytk8oq68tpw263jcni66x` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_participant`
--

LOCK TABLES `article_participant` WRITE;
/*!40000 ALTER TABLE `article_participant` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_participant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_tag`
--

DROP TABLE IF EXISTS `article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_tag` (
  `article_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`article_id`,`tag_id`),
  KEY `FKesqp7s9jj2wumlnhssbme5ule` (`tag_id`),
  CONSTRAINT `FKenqeees0y8hkm7x1p1ittuuye` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `FKesqp7s9jj2wumlnhssbme5ule` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_tag`
--

LOCK TABLES `article_tag` WRITE;
/*!40000 ALTER TABLE `article_tag` DISABLE KEYS */;
INSERT INTO `article_tag` VALUES (1,1),(2,1),(1,2),(2,2),(3,2),(5,2),(3,3),(5,3);
/*!40000 ALTER TABLE `article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_vote`
--

DROP TABLE IF EXISTS `article_vote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_vote` (
  `article_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `vote` tinyint NOT NULL,
  PRIMARY KEY (`article_id`,`user_id`),
  KEY `FK3sbprg6pqgerriov5gqegatse` (`user_id`),
  CONSTRAINT `FK3sbprg6pqgerriov5gqegatse` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKp8bfgu7gea52k8sa6smpj4fni` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_vote`
--

LOCK TABLES `article_vote` WRITE;
/*!40000 ALTER TABLE `article_vote` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_vote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookmark_article`
--

DROP TABLE IF EXISTS `bookmark_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookmark_article` (
  `article_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `type` tinyint NOT NULL,
  PRIMARY KEY (`article_id`,`user_id`),
  KEY `FKit8s3fywvl835pnrpcjiqgjre` (`user_id`),
  CONSTRAINT `FKd7pv9amc1yk0fdqbnhfyl8ftj` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `FKit8s3fywvl835pnrpcjiqgjre` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmark_article`
--

LOCK TABLES `bookmark_article` WRITE;
/*!40000 ALTER TABLE `bookmark_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookmark_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`),
  UNIQUE KEY `UK_hqknmjh5423vchi4xkyhxlhg2` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,NULL,NULL,'Lap trinh','lap-trinh'),(2,NULL,NULL,'Chuyen ben le','chuyen-ben-le');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(5000) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `image_slug` varchar(255) DEFAULT NULL,
  `is_root` tinyint(1) DEFAULT '1',
  `updated_at` datetime(6) DEFAULT NULL,
  `article_id` bigint NOT NULL,
  `from_user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5yx0uphgjc6ik6hb82kkw501y` (`article_id`),
  KEY `FKaaa0k7eumd9xi3vu65l6aol9e` (`from_user_id`),
  CONSTRAINT `FK5yx0uphgjc6ik6hb82kkw501y` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `FKaaa0k7eumd9xi3vu65l6aol9e` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `comment_chk_1` CHECK (((`content` is not null) or (`image_slug` is not null)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follower`
--

DROP TABLE IF EXISTS `follower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follower` (
  `from_user_id` bigint NOT NULL,
  `to_user_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  PRIMARY KEY (`from_user_id`,`to_user_id`),
  KEY `FK8xgry3e14jqtkbyx0vwsnckj` (`to_user_id`),
  CONSTRAINT `FK3aswa7d1w1871kcghomicyfsu` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK8xgry3e14jqtkbyx0vwsnckj` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follower`
--

LOCK TABLES `follower` WRITE;
/*!40000 ALTER TABLE `follower` DISABLE KEYS */;
/*!40000 ALTER TABLE `follower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent_child_comment`
--

DROP TABLE IF EXISTS `parent_child_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parent_child_comment` (
  `level` tinyint unsigned NOT NULL,
  `child_comment_id` bigint NOT NULL,
  `parent_comment_id` bigint NOT NULL,
  PRIMARY KEY (`child_comment_id`,`parent_comment_id`),
  KEY `FKo3dol36vanre5xk1w131hgqun` (`parent_comment_id`),
  CONSTRAINT `FKdnv631ltkod18qtwc60c5lq9o` FOREIGN KEY (`child_comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `FKo3dol36vanre5xk1w131hgqun` FOREIGN KEY (`parent_comment_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent_child_comment`
--

LOCK TABLES `parent_child_comment` WRITE;
/*!40000 ALTER TABLE `parent_child_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `parent_child_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset_token`
--

DROP TABLE IF EXISTS `password_reset_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset_token` (
  `user_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `expire_at` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_g0guo4k8krgpwuagos61oc06j` (`token`),
  CONSTRAINT `FK5lwtbncug84d4ero33v3cfxvl` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset_token`
--

LOCK TABLES `password_reset_token` WRITE;
/*!40000 ALTER TABLE `password_reset_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_reset_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_EDITOR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `series_article`
--

DROP TABLE IF EXISTS `series_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `series_article` (
  `article_id` bigint NOT NULL,
  `series_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`article_id`,`series_id`),
  KEY `FKqywffhni1o6cpoyjhqsef9m6k` (`series_id`),
  CONSTRAINT `FKbx7owdsueib4u5hikevxlspri` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `FKqywffhni1o6cpoyjhqsef9m6k` FOREIGN KEY (`series_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `series_article`
--

LOCK TABLES `series_article` WRITE;
/*!40000 ALTER TABLE `series_article` DISABLE KEYS */;
INSERT INTO `series_article` VALUES (1,5,'2021-09-03 17:10:01.218762',0),(2,5,'2021-09-03 17:10:01.219003',0);
/*!40000 ALTER TABLE `series_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `last_edited_at` datetime(6) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `slug` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1wdpsed5kna2y38hnbgrnhi5b` (`name`),
  UNIQUE KEY `UK_1afk1y1o95l8oxxjxsqvelm3o` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'2021-09-03 16:01:05.613346',NULL,NULL,NULL,'Java','java'),(2,'2021-09-03 16:01:13.586604',NULL,NULL,NULL,'Python','python'),(3,'2021-09-03 16:01:19.054450',NULL,NULL,NULL,'Javascript','javascript'),(4,'2021-09-03 16:01:23.056993',NULL,NULL,NULL,'Lua','lua'),(5,'2021-09-03 16:01:26.758932',NULL,NULL,NULL,'PHP','php'),(6,'2021-09-03 16:01:41.682820',NULL,NULL,NULL,'C Sharp','c-sharp');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) NOT NULL,
  `biography` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `fullname` varchar(32) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `is_using_2fa` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `username` varchar(32) NOT NULL,
  `host_avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'default avatar url nek',NULL,NULL,'2021-09-02 23:42:14.999851','admin@gmail.com',_binary '','Admin','UNKNOWN',_binary '\0','$2a$10$1v46C6kmknPTxxhYc9Ia0eK9XP5sJd21im1kfLJjEfBGYm34FvWam',NULL,'admin',NULL),(2,'default avatar url nek',NULL,NULL,'2021-09-02 23:42:31.899227','editor@gmail.com',_binary '','Editor','UNKNOWN',_binary '\0','$2a$10$nmJPnfxSnnEkd5wzRWmSsuFekBqSFepDkllZh5chvb9yK/f8eh02e',NULL,'editor',NULL),(3,'default avatar url nek',NULL,NULL,'2021-09-02 23:42:55.233630','user1@gmail.com',_binary '','User 1','UNKNOWN',_binary '\0','$2a$10$edRV1OHCZXtV8B2r2uUEPe90ggvYS20z6ItKJS9if9jxuwmBFdhtu',NULL,'user1',NULL),(4,'default avatar url nek',NULL,NULL,'2021-09-02 23:46:52.327547','user2@gmail.com',_binary '','User 2','UNKNOWN',_binary '\0','$2a$10$uV6GUHuuCQlN..a53gITrOgeDKkbH2rSbQ5QkKVleO54e0mUkQSv2',NULL,'user2',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_image`
--

DROP TABLE IF EXISTS `user_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `host` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_iub8nmc2iv3xffuctjnb90i3y` (`slug`),
  KEY `FK5m3lhx7tcj9h9ju10xo4ruqcn` (`user_id`),
  CONSTRAINT `FK5m3lhx7tcj9h9ju10xo4ruqcn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_image`
--

LOCK TABLES `user_image` WRITE;
/*!40000 ALTER TABLE `user_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_token` (
  `user_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `expire_at` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_p678btf3r9yu6u8aevyb4ff0m` (`token`),
  CONSTRAINT `FKrdn0mss276m9jdobfhhn2qogw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token` DISABLE KEYS */;
INSERT INTO `verification_token` VALUES (1,'2021-09-02 23:42:15.376039','2021-10-04 05:42:15.376039','778ad35b-02b3-42bd-a4b8-9145edca6302'),(2,'2021-09-02 23:42:32.058423','2021-10-04 05:42:32.058423','88301fed-8a9f-461a-8185-5cd004b8b7c1'),(3,'2021-09-02 23:42:55.371147','2021-10-04 05:42:55.371147','2dc7b62f-b298-4c4f-a05e-bccbb541599f');
/*!40000 ALTER TABLE `verification_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-05  9:44:50
