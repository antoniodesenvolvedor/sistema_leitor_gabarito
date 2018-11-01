CREATE DATABASE  IF NOT EXISTS `leitor_gabarito` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `leitor_gabarito`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: leitor_gabarito
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aluno`
--

DROP TABLE IF EXISTS `aluno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `aluno` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_turma` int(11) DEFAULT NULL,
  `nome` varchar(20) NOT NULL,
  `sobrenome` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_turma` (`codigo_turma`),
  CONSTRAINT `aluno_ibfk_1` FOREIGN KEY (`codigo_turma`) REFERENCES `turma` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aluno`
--

LOCK TABLES `aluno` WRITE;
/*!40000 ALTER TABLE `aluno` DISABLE KEYS */;
INSERT INTO `aluno` VALUES (1,14,'ANTONIO','BATISTA'),(2,13,'SIMEI','BATISTA'),(3,14,'LEONARDO','PEREIRA');
/*!40000 ALTER TABLE `aluno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `correcao`
--

DROP TABLE IF EXISTS `correcao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `correcao` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_aluno` int(11) NOT NULL,
  `codigo_prova` int(11) NOT NULL,
  `data_correcao` date NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_prova` (`codigo_prova`),
  KEY `correcao_fk_1` (`codigo_aluno`),
  CONSTRAINT `correcao_fk_1` FOREIGN KEY (`codigo_aluno`) REFERENCES `aluno` (`codigo`),
  CONSTRAINT `correcao_ibfk_2` FOREIGN KEY (`codigo_prova`) REFERENCES `prova` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `correcao`
--

LOCK TABLES `correcao` WRITE;
/*!40000 ALTER TABLE `correcao` DISABLE KEYS */;
INSERT INTO `correcao` VALUES (35,1,10,'2018-11-01'),(37,3,10,'2018-11-01'),(38,2,10,'2018-11-01');
/*!40000 ALTER TABLE `correcao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prova`
--

DROP TABLE IF EXISTS `prova`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `prova` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `disciplina` varchar(50) NOT NULL,
  `data_criacao` date NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prova`
--

LOCK TABLES `prova` WRITE;
/*!40000 ALTER TABLE `prova` DISABLE KEYS */;
INSERT INTO `prova` VALUES (10,'disciplina teste','2018-11-01');
/*!40000 ALTER TABLE `prova` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questao_correcao`
--

DROP TABLE IF EXISTS `questao_correcao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `questao_correcao` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_correcao` int(11) NOT NULL,
  `resposta` char(1) DEFAULT NULL,
  `numero_questao` int(11) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_correcao` (`codigo_correcao`),
  CONSTRAINT `questao_correcao_ibfk_1` FOREIGN KEY (`codigo_correcao`) REFERENCES `correcao` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=1441 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questao_correcao`
--

LOCK TABLES `questao_correcao` WRITE;
/*!40000 ALTER TABLE `questao_correcao` DISABLE KEYS */;
INSERT INTO `questao_correcao` VALUES (1081,35,'',1),(1082,35,'',2),(1083,35,'',3),(1084,35,'',4),(1085,35,'E',5),(1086,35,'A',6),(1087,35,'B',7),(1088,35,'C',8),(1089,35,'D',9),(1090,35,'E',10),(1091,35,'A',11),(1092,35,'B',12),(1093,35,'C',13),(1094,35,'D',14),(1095,35,'E',15),(1096,35,'A',16),(1097,35,'B',17),(1098,35,'C',18),(1099,35,'D',19),(1100,35,'E',20),(1101,35,'A',21),(1102,35,'B',22),(1103,35,'C',23),(1104,35,'D',24),(1105,35,'E',25),(1106,35,'A',26),(1107,35,'B',27),(1108,35,'C',28),(1109,35,'D',29),(1110,35,'E',30),(1111,35,'A',31),(1112,35,'B',32),(1113,35,'C',33),(1114,35,'D',34),(1115,35,'E',35),(1116,35,'A',36),(1117,35,'B',37),(1118,35,'C',38),(1119,35,'D',39),(1120,35,'E',40),(1121,35,'A',41),(1122,35,'B',42),(1123,35,'C',43),(1124,35,'D',44),(1125,35,'E',45),(1126,35,'A',46),(1127,35,'B',47),(1128,35,'C',48),(1129,35,'D',49),(1130,35,'E',50),(1131,35,'A',51),(1132,35,'B',52),(1133,35,'C',53),(1134,35,'D',54),(1135,35,'E',55),(1136,35,'A',56),(1137,35,'B',57),(1138,35,'C',58),(1139,35,'D',59),(1140,35,'E',60),(1141,35,'A',61),(1142,35,'B',62),(1143,35,'C',63),(1144,35,'D',64),(1145,35,'E',65),(1146,35,'A',66),(1147,35,'B',67),(1148,35,'C',68),(1149,35,'D',69),(1150,35,'E',70),(1151,35,'A',71),(1152,35,'B',72),(1153,35,'C',73),(1154,35,'D',74),(1155,35,'E',75),(1156,35,'A',76),(1157,35,'B',77),(1158,35,'C',78),(1159,35,'D',79),(1160,35,'E',80),(1161,35,'A',81),(1162,35,'B',82),(1163,35,'C',83),(1164,35,'D',84),(1165,35,'E',85),(1166,35,'A',86),(1167,35,'B',87),(1168,35,'C',88),(1169,35,'D',89),(1170,35,'',90),(1261,37,'A',1),(1262,37,'B',2),(1263,37,'C',3),(1264,37,'D',4),(1265,37,'E',5),(1266,37,'A',6),(1267,37,'B',7),(1268,37,'C',8),(1269,37,'D',9),(1270,37,'E',10),(1271,37,'A',11),(1272,37,'B',12),(1273,37,'C',13),(1274,37,'D',14),(1275,37,'E',15),(1276,37,'A',16),(1277,37,'B',17),(1278,37,'C',18),(1279,37,'D',19),(1280,37,'E',20),(1281,37,'A',21),(1282,37,'B',22),(1283,37,'C',23),(1284,37,'D',24),(1285,37,'E',25),(1286,37,'A',26),(1287,37,'B',27),(1288,37,'C',28),(1289,37,'D',29),(1290,37,'E',30),(1291,37,'A',31),(1292,37,'B',32),(1293,37,'C',33),(1294,37,'D',34),(1295,37,'E',35),(1296,37,'A',36),(1297,37,'B',37),(1298,37,'C',38),(1299,37,'D',39),(1300,37,'E',40),(1301,37,'A',41),(1302,37,'B',42),(1303,37,'C',43),(1304,37,'D',44),(1305,37,'E',45),(1306,37,'A',46),(1307,37,'B',47),(1308,37,'C',48),(1309,37,'D',49),(1310,37,'E',50),(1311,37,'A',51),(1312,37,'B',52),(1313,37,'C',53),(1314,37,'D',54),(1315,37,'E',55),(1316,37,'A',56),(1317,37,'B',57),(1318,37,'C',58),(1319,37,'D',59),(1320,37,'E',60),(1321,37,'A',61),(1322,37,'B',62),(1323,37,'C',63),(1324,37,'',64),(1325,37,'E',65),(1326,37,'A',66),(1327,37,'B',67),(1328,37,'C',68),(1329,37,'D',69),(1330,37,'E',70),(1331,37,'A',71),(1332,37,'B',72),(1333,37,'C',73),(1334,37,'D',74),(1335,37,'E',75),(1336,37,'A',76),(1337,37,'B',77),(1338,37,'C',78),(1339,37,'D',79),(1340,37,'E',80),(1341,37,'A',81),(1342,37,'B',82),(1343,37,'C',83),(1344,37,'D',84),(1345,37,'E',85),(1346,37,'A',86),(1347,37,'B',87),(1348,37,'C',88),(1349,37,'D',89),(1350,37,'E',90),(1351,38,'',1),(1352,38,'',2),(1353,38,'',3),(1354,38,'',4),(1355,38,'E',5),(1356,38,'A',6),(1357,38,'B',7),(1358,38,'C',8),(1359,38,'D',9),(1360,38,'E',10),(1361,38,'A',11),(1362,38,'B',12),(1363,38,'C',13),(1364,38,'D',14),(1365,38,'E',15),(1366,38,'A',16),(1367,38,'B',17),(1368,38,'C',18),(1369,38,'D',19),(1370,38,'E',20),(1371,38,'A',21),(1372,38,'B',22),(1373,38,'C',23),(1374,38,'D',24),(1375,38,'E',25),(1376,38,'A',26),(1377,38,'B',27),(1378,38,'C',28),(1379,38,'D',29),(1380,38,'E',30),(1381,38,'A',31),(1382,38,'B',32),(1383,38,'C',33),(1384,38,'D',34),(1385,38,'E',35),(1386,38,'A',36),(1387,38,'B',37),(1388,38,'C',38),(1389,38,'D',39),(1390,38,'E',40),(1391,38,'A',41),(1392,38,'B',42),(1393,38,'C',43),(1394,38,'D',44),(1395,38,'E',45),(1396,38,'A',46),(1397,38,'B',47),(1398,38,'C',48),(1399,38,'D',49),(1400,38,'E',50),(1401,38,'A',51),(1402,38,'B',52),(1403,38,'C',53),(1404,38,'D',54),(1405,38,'E',55),(1406,38,'A',56),(1407,38,'B',57),(1408,38,'C',58),(1409,38,'D',59),(1410,38,'E',60),(1411,38,'A',61),(1412,38,'B',62),(1413,38,'C',63),(1414,38,'',64),(1415,38,'E',65),(1416,38,'A',66),(1417,38,'B',67),(1418,38,'C',68),(1419,38,'D',69),(1420,38,'E',70),(1421,38,'A',71),(1422,38,'B',72),(1423,38,'C',73),(1424,38,'D',74),(1425,38,'E',75),(1426,38,'A',76),(1427,38,'B',77),(1428,38,'C',78),(1429,38,'D',79),(1430,38,'E',80),(1431,38,'A',81),(1432,38,'B',82),(1433,38,'C',83),(1434,38,'D',84),(1435,38,'E',85),(1436,38,'A',86),(1437,38,'B',87),(1438,38,'C',88),(1439,38,'D',89),(1440,38,'',90);
/*!40000 ALTER TABLE `questao_correcao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questao_prova`
--

DROP TABLE IF EXISTS `questao_prova`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `questao_prova` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_prova` int(11) NOT NULL,
  `resposta` char(1) DEFAULT NULL,
  `numero_questao` int(11) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_prova` (`codigo_prova`),
  CONSTRAINT `questao_prova_ibfk_1` FOREIGN KEY (`codigo_prova`) REFERENCES `prova` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=873 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questao_prova`
--

LOCK TABLES `questao_prova` WRITE;
/*!40000 ALTER TABLE `questao_prova` DISABLE KEYS */;
INSERT INTO `questao_prova` VALUES (783,10,'A',1),(784,10,'B',2),(785,10,'C',3),(786,10,'D',4),(787,10,'E',5),(788,10,'A',6),(789,10,'B',7),(790,10,'C',8),(791,10,'D',9),(792,10,'E',10),(793,10,'A',11),(794,10,'B',12),(795,10,'C',13),(796,10,'D',14),(797,10,'E',15),(798,10,'A',16),(799,10,'B',17),(800,10,'C',18),(801,10,'D',19),(802,10,'E',20),(803,10,'A',21),(804,10,'B',22),(805,10,'C',23),(806,10,'D',24),(807,10,'E',25),(808,10,'A',26),(809,10,'B',27),(810,10,'C',28),(811,10,'D',29),(812,10,'E',30),(813,10,'A',31),(814,10,'B',32),(815,10,'C',33),(816,10,'D',34),(817,10,'E',35),(818,10,'A',36),(819,10,'B',37),(820,10,'C',38),(821,10,'D',39),(822,10,'E',40),(823,10,'A',41),(824,10,'B',42),(825,10,'C',43),(826,10,'D',44),(827,10,'E',45),(828,10,'A',46),(829,10,'B',47),(830,10,'C',48),(831,10,'D',49),(832,10,'E',50),(833,10,'A',51),(834,10,'B',52),(835,10,'C',53),(836,10,'D',54),(837,10,'E',55),(838,10,'A',56),(839,10,'B',57),(840,10,'C',58),(841,10,'D',59),(842,10,'E',60),(843,10,'A',61),(844,10,'B',62),(845,10,'C',63),(846,10,'',64),(847,10,'E',65),(848,10,'A',66),(849,10,'B',67),(850,10,'C',68),(851,10,'D',69),(852,10,'E',70),(853,10,'A',71),(854,10,'B',72),(855,10,'C',73),(856,10,'D',74),(857,10,'E',75),(858,10,'A',76),(859,10,'B',77),(860,10,'C',78),(861,10,'D',79),(862,10,'E',80),(863,10,'A',81),(864,10,'B',82),(865,10,'C',83),(866,10,'D',84),(867,10,'E',85),(868,10,'A',86),(869,10,'B',87),(870,10,'C',88),(871,10,'D',89),(872,10,'E',90);
/*!40000 ALTER TABLE `questao_prova` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_usuario`
--

DROP TABLE IF EXISTS `tb_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_usuario` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) DEFAULT NULL,
  `senha` varchar(30) NOT NULL,
  `login` varchar(30) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_usuario`
--

LOCK TABLES `tb_usuario` WRITE;
/*!40000 ALTER TABLE `tb_usuario` DISABLE KEYS */;
INSERT INTO `tb_usuario` VALUES (0,'pedro','12345','pedro');
/*!40000 ALTER TABLE `tb_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turma`
--

DROP TABLE IF EXISTS `turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `turma` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `descricao` (`descricao`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turma`
--

LOCK TABLES `turma` WRITE;
/*!40000 ALTER TABLE `turma` DISABLE KEYS */;
INSERT INTO `turma` VALUES (13,'ADM'),(14,'SISTEMAS');
/*!40000 ALTER TABLE `turma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'leitor_gabarito'
--
/*!50003 DROP PROCEDURE IF EXISTS `excluir_prova` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `excluir_prova`(in codigo_p int)
BEGIN
	DELETE FROM questao_correcao WHERE codigo_correcao IN (SELECT codigo FROM correcao WHERE codigo_prova = codigo_p );
    DELETE FROM correcao WHERE codigo_prova = codigo_p;
    DELETE FROM questao_prova WHERE codigo_prova = codigo_p;
    DELETE FROM prova WHERE codigo = codigo_p;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-01 17:43:32
