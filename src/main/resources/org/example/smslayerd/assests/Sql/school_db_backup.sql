-- MySQL dump 10.13  Distrib 9.3.0, for Linux (x86_64)
--
-- Host: localhost    Database: SMS
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Admin`
--

DROP TABLE IF EXISTS `Admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Admin` (
  `Admin_ID` varchar(10) NOT NULL,
  `User_name` varchar(10) NOT NULL,
  `Password` varchar(10) NOT NULL,
  `AdminType` varchar(255) NOT NULL,
  PRIMARY KEY (`Admin_ID`),
  UNIQUE KEY `User_name` (`User_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
INSERT INTO `Admin` VALUES ('A001','123','123','SuperAdmin'),('A002','Yasiru','000','SuperAdmin'),('A004','mgs','24','Admin'),('A006','Isuru','********','SuperAdmin'),('A007','df','********','SuperAdmin'),('A008','fg','12345','Admin'),('A009','sudeera','123','SuperAdmin'),('A011','sadun','roinroin','ADMIN');
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Attendance_Stu`
--

DROP TABLE IF EXISTS `Attendance_Stu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Attendance_Stu` (
  `Attend_ID` varchar(10) NOT NULL,
  `Date` date NOT NULL,
  `Admin_ID` varchar(10) DEFAULT NULL,
  `Student_ID` varchar(10) DEFAULT NULL,
  `Class_ID` varchar(20) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  PRIMARY KEY (`Attend_ID`),
  KEY `Admin_ID` (`Admin_ID`),
  KEY `Attendance_Stu_ibfk_2` (`Student_ID`),
  KEY `Teacher_ibfk_1` (`Class_ID`),
  CONSTRAINT `Attendance_Stu_ibfk_1` FOREIGN KEY (`Admin_ID`) REFERENCES `Admin` (`Admin_ID`),
  CONSTRAINT `Attendance_Stu_ibfk_2` FOREIGN KEY (`Student_ID`) REFERENCES `Student` (`Student_ID`) ON DELETE CASCADE,
  CONSTRAINT `Teacher_ibfk_1` FOREIGN KEY (`Class_ID`) REFERENCES `Class` (`Class_ID`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Attendance_Stu`
--

LOCK TABLES `Attendance_Stu` WRITE;
/*!40000 ALTER TABLE `Attendance_Stu` DISABLE KEYS */;
INSERT INTO `Attendance_Stu` VALUES ('AS023','2025-08-10','A001','S004','C003',1),('AT021','2025-06-04','A001','S004','C003',1),('AT022','2025-06-06','A001','S004','C004',1),('AT023','2025-08-04','A001','S004','C004',1),('AT024','2025-08-13','A001','S004','C003',0),('AT025','2025-08-12','A001','S004','C005',1);
/*!40000 ALTER TABLE `Attendance_Stu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Attendance_Tea`
--

DROP TABLE IF EXISTS `Attendance_Tea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Attendance_Tea` (
  `Attend_ID` varchar(10) NOT NULL,
  `Date` date NOT NULL,
  `Admin_ID` varchar(10) DEFAULT NULL,
  `Teacher_ID` varchar(10) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  `Class_ID` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Attend_ID`),
  KEY `Admin_ID` (`Admin_ID`),
  KEY `Teacher_ID` (`Teacher_ID`),
  CONSTRAINT `Attendance_Tea_ibfk_1` FOREIGN KEY (`Admin_ID`) REFERENCES `Admin` (`Admin_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Attendance_Tea`
--

LOCK TABLES `Attendance_Tea` WRITE;
/*!40000 ALTER TABLE `Attendance_Tea` DISABLE KEYS */;
INSERT INTO `Attendance_Tea` VALUES ('AT006','2025-05-22','A001','T005',1,'C002'),('AT008','2025-05-22','A001','T005',1,'C012'),('AT009','2025-05-22','A001','T006',0,'C001'),('AT010','2025-05-22','A001','T006',1,'C003'),('AT011','2025-05-22','A001','T006',1,'C012'),('AT012','2025-05-22','A001','T005',1,'C009'),('AT013','2025-05-22','A001','T005',1,'C003'),('AT014','2025-05-22','A001','T005',0,'C024'),('AT015','2025-05-22','A001','T006',1,'C024'),('AT016','2025-05-22','A001','T006',1,'C011'),('AT017','2025-05-22','A001','T006',1,'C022'),('AT018','2025-05-27','A001','T003',1,'C008'),('AT019','2025-05-27','A001','T007',1,'C002'),('AT020','2025-05-27','A001','T008',1,'C002'),('AT022','2025-05-28','A001','T020',0,'C002'),('AT023','2025-05-28','A001','T004',0,'C002'),('AT024','2025-05-28','A001','T009',0,'C002'),('AT025','2025-05-28','A001','T010',0,'C009'),('AT026','2025-05-28','A001','T021',0,'C011'),('AT027','2025-05-28','A001','T022',0,'C012'),('AT030','2025-05-30','A001','T025',0,'C008'),('AT031','2025-05-30','A001','T026',0,'C003'),('AT032','2025-06-01','A001','T029',0,'C010'),('AT033','2025-06-01','A001','T030',0,'C001'),('AT036','2025-06-03','A001','T003',0,'C009'),('AT037','2025-06-03','A001','T007',0,'C008'),('AT038','2025-06-03','A001','T008',0,'C002'),('AT040','2025-06-04','A001','T020',0,'C008'),('AT041','2025-06-04','A001','T004',0,'C009'),('AT042','2025-06-04','A001','T009',0,'C008'),('AT043','2025-06-04','A001','T010',0,'C002'),('AT044','2025-06-04','A001','T021',0,'C002'),('AT045','2025-06-04','A001','T022',1,'C002'),('AT046','2025-06-05','A001','T005',0,'C021'),('AT047','2025-06-05','A001','T012',1,'C019'),('AT048','2025-06-05','A001','T018',0,'C019'),('AT049','2025-06-05','A001','T023',0,'C019'),('AT050','2025-06-05','A001','T013',0,'C020'),('AT051','2025-06-05','A001','T015',1,'C021'),('AT052','2025-06-05','A001','T006',1,'C016'),('AT053','2025-06-05','A001','T017',1,'C010'),('AT054','2025-06-05','A001','T019',1,'C024'),('AT055','2025-06-05','A001','T024',1,'C013'),('AT056','2025-08-12','A001','T003',0,'C002'),('AT057','2025-08-12','A001','T007',0,'C002'),('AT058','2025-08-12','A001','T008',0,'C002'),('AT060','2025-08-11','A001','T005',1,'C004'),('AT061','2025-08-13','A001','T004',0,'C003'),('AT062','2025-08-13','A001','T009',0,'C003'),('AT063','2025-08-13','A001','T010',0,'C003'),('AT064','2025-08-13','A001','T020',0,'C012'),('AT065','2025-08-13','A001','T021',0,'C013'),('AT066','2025-08-13','A001','T022',0,'C014');
/*!40000 ALTER TABLE `Attendance_Tea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Class`
--

DROP TABLE IF EXISTS `Class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Class` (
  `Class_ID` varchar(10) NOT NULL,
  `Grade` int NOT NULL,
  `Time_Table_ID` varchar(10) DEFAULT NULL,
  `Subject_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Class_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Class`
--

LOCK TABLES `Class` WRITE;
/*!40000 ALTER TABLE `Class` DISABLE KEYS */;
INSERT INTO `Class` VALUES ('C002',10,'T005','S004'),('C003',11,'T009','S005'),('C004',11,'T013','S007'),('C005',12,'T017','S010'),('C006',11,'T011','S001'),('C007',12,'T013','S005'),('C008',10,'T001','S001'),('C009',11,'T002','S014'),('C010',11,'T005','S001'),('C011',12,'T006','S007'),('C012',10,'T008','S002'),('C013',11,'T009','S004'),('C014',12,'T010','S010'),('C015',10,'T011','S011'),('C016',11,'T013','S020'),('C017',12,'T014','S017'),('C018',10,'T016','S007'),('C019',11,'T017','S019'),('C020',12,'T018','S008'),('C021',10,'T020','S013'),('C022',11,'T022','S005'),('C023',12,'T006','S008');
/*!40000 ALTER TABLE `Class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Exam`
--

DROP TABLE IF EXISTS `Exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Exam` (
  `Exam_ID` varchar(10) NOT NULL,
  `Exam_Date` date NOT NULL,
  `Marks` int DEFAULT NULL,
  `Subject_ID` varchar(10) DEFAULT NULL,
  `Teacher_ID` varchar(10) DEFAULT NULL,
  `Student_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Exam_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Exam`
--

LOCK TABLES `Exam` WRITE;
/*!40000 ALTER TABLE `Exam` DISABLE KEYS */;
INSERT INTO `Exam` VALUES ('EX003','2024-05-01',90,'S004','T002','S005'),('EX004','2024-05-01',88,'S004','T002','S005'),('EX005','2025-06-27',20,'S005','T002','S001'),('EX006','2025-06-21',50,'S002','T003','S001'),('EX007','2025-08-12',30,'S008','T008','S005');
/*!40000 ALTER TABLE `Exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Student`
--

DROP TABLE IF EXISTS `Student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Student` (
  `Student_ID` varchar(10) NOT NULL,
  `Tel_No` int NOT NULL,
  `Class_ID` varchar(10) DEFAULT NULL,
  `Name` varchar(20) NOT NULL,
  `Grade` int NOT NULL,
  `Address` varchar(20) NOT NULL,
  PRIMARY KEY (`Student_ID`),
  UNIQUE KEY `Tel_No` (`Tel_No`),
  KEY `fk_student_class` (`Class_ID`),
  CONSTRAINT `fk_student_class` FOREIGN KEY (`Class_ID`) REFERENCES `Class` (`Class_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Student`
--

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;
INSERT INTO `Student` VALUES ('S004',774567890,'C003','Tharindu Jayasuriya',12,'Matara'),('S005',775678901,'C002','Hasini Wijesinghe',11,'Negombo'),('S011',765907243,'C002','yasiru sahan',13,'panadura'),('S012',1234567890,'C010','sahan',5,'mahavilla');
/*!40000 ALTER TABLE `Student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subject`
--

DROP TABLE IF EXISTS `Subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Subject` (
  `Subject_ID` varchar(10) NOT NULL,
  `Name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Subject_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subject`
--

LOCK TABLES `Subject` WRITE;
/*!40000 ALTER TABLE `Subject` DISABLE KEYS */;
INSERT INTO `Subject` VALUES ('S001','Mathematics'),('S002','Science'),('S004','Histor'),('S005','Geography'),('S007','Ar'),('S008','Music'),('S010','Sinhala'),('S011','Tamil'),('S013','Christianity'),('S014','Physics'),('S016','Biology'),('S017','Commerce'),('S019','Drama'),('S020','hgh'),('S021','slt');
/*!40000 ALTER TABLE `Subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Teacher`
--

DROP TABLE IF EXISTS `Teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Teacher` (
  `Teacher_ID` varchar(10) NOT NULL,
  `Subject_ID` varchar(10) DEFAULT NULL,
  `Name` varchar(20) NOT NULL,
  `Class_ID` varchar(10) DEFAULT NULL,
  `Grades_Assigned` varchar(20) NOT NULL,
  PRIMARY KEY (`Teacher_ID`),
  KEY `Class_ID` (`Class_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Teacher`
--

LOCK TABLES `Teacher` WRITE;
/*!40000 ALTER TABLE `Teacher` DISABLE KEYS */;
INSERT INTO `Teacher` VALUES ('T003','SUB05','Mrs. Anoma','C002','11'),('T004','SUB12','Mr. Jude','C003','12'),('T005','S001','Yasiru','C006','12'),('T006','S001','sahan','C007','12'),('T007','S014','Ms. Harshani','C002','10'),('T008','S016','Mr. Weerakoon','C002','10'),('T009','S002','Mrs. Kamani','C003','11'),('T010','S004','Mr. Nimal','C003','11'),('T011','S010','Ms. Udeshika','C005','12'),('T012','S011','Mr. Aravinda','C006','1'),('T013','S020','Mrs. Pathmini','C004','11'),('T014','S017','Mr. Perera','C005','12'),('T015','S007','Ms. Chathurika','C004','11'),('T016','S019','Mr. Sanjaya','C005','12'),('T017','S008','Ms. Thilini','C007','12'),('T018','S013','Mr. Ranil','C006','11'),('T019','S005','Mrs. Hemamali','C007','12'),('T020','S002','C. Herath','C012','10'),('T021','S004','K. Senanayake','C013','11'),('T022','S010','R. Madushanka','C014','12'),('T023','S011','N. Silva','C015','10'),('T024','S020','I. Dissanayake','C016','11'),('T025','S017','T. Wijesinghe','C017','12'),('T026','S007','S. Gunawardena','C018','10'),('T027','S019','H. Rathnayake','C019','11'),('T028','S008','J. Fernando','C020','12'),('T029','S013','D. Wickramasinghe','C021','10'),('T030','S005','M. Perera','C022','11'),('T031','S004','yasiru','C024','7');
/*!40000 ALTER TABLE `Teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Time_Table`
--

DROP TABLE IF EXISTS `Time_Table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Time_Table` (
  `Time_Table_ID` varchar(10) NOT NULL,
  `Subject_ID` varchar(10) DEFAULT NULL,
  `Start_Time` varchar(10) DEFAULT NULL,
  `End_Time` varchar(10) DEFAULT NULL,
  `day_of_week` varchar(10) NOT NULL,
  PRIMARY KEY (`Time_Table_ID`),
  KEY `Subject_ID` (`Subject_ID`),
  CONSTRAINT `Time_Table_ibfk_1` FOREIGN KEY (`Subject_ID`) REFERENCES `Subject` (`Subject_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Time_Table`
--

LOCK TABLES `Time_Table` WRITE;
/*!40000 ALTER TABLE `Time_Table` DISABLE KEYS */;
INSERT INTO `Time_Table` VALUES ('','S004','9:00','10:00','Wednesday'),('T002','S014','09:45:00','11:15:00','Monday'),('T005','S001','08:30:00','10:00:00','Tuesday'),('T006','S016','10:15:00','11:45:00','Tuesday'),('T008','S002','08:00:00','09:30:00','Wednesday'),('T009','S004','10:00:00','11:30:00','Wednesday'),('T010','S010','13:00:00','14:30:00','Wednesday'),('T011','S011','9:00','10:30','Thursday'),('T013','S020','14:00:00','15:30:00','Thursday'),('T014','S017','08:30:00','10:00:00','Friday'),('T016','S007','12:00','1:00','Monday'),('T017','S019','09:00:00','10:30:00','Saturday'),('T018','S008','11:00:00','12:30:00','Saturday'),('T020','S013','09:00:00','10:30:00','Sunday'),('T022','S005','14:00:00','15:30:00','Sunday');
/*!40000 ALTER TABLE `Time_Table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-13  6:34:40
