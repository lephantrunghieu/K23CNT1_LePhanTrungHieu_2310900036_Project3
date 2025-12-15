drop database IF EXISTS webbanmt;
create database webbanmt;
use webbanmt;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: webbanmt
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `cthd`
--

DROP TABLE IF EXISTS `cthd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cthd` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sohd` int DEFAULT NULL,
  `maytinh` int DEFAULT NULL,
  `soluong` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cthd_hoadon` (`sohd`),
  KEY `fk_cthd_maytinh` (`maytinh`),
  CONSTRAINT `fk_cthd_hoadon` FOREIGN KEY (`sohd`) REFERENCES `hoadon` (`sohd`),
  CONSTRAINT `fk_cthd_maytinh` FOREIGN KEY (`maytinh`) REFERENCES `maytinh` (`mamt`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cthd`
--

LOCK TABLES `cthd` WRITE;
/*!40000 ALTER TABLE `cthd` DISABLE KEYS */;
/*!40000 ALTER TABLE `cthd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hang`
--

DROP TABLE IF EXISTS `hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hang` (
  `mahang` int NOT NULL AUTO_INCREMENT,
  `tenhang` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `duongdan` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`mahang`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hang`
--

LOCK TABLES `hang` WRITE;
/*!40000 ALTER TABLE `hang` DISABLE KEYS */;
INSERT INTO `hang` VALUES (1,'Asus','asus'),(2,'Lenovo','lenovo'),(3,'Dell','dell');
/*!40000 ALTER TABLE `hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoadon`
--

DROP TABLE IF EXISTS `hoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoadon` (
  `sohd` int NOT NULL AUTO_INCREMENT,
  `khachhang` int DEFAULT NULL,
  `ngaydat` datetime(6) DEFAULT NULL,
  `trangthai` int DEFAULT NULL,
  PRIMARY KEY (`sohd`),
  KEY `fk_hoadon_khachhang` (`khachhang`),
  CONSTRAINT `fk_hoadon_khachhang` FOREIGN KEY (`khachhang`) REFERENCES `khachhang` (`makh`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoadon`
--

LOCK TABLES `hoadon` WRITE;
/*!40000 ALTER TABLE `hoadon` DISABLE KEYS */;
/*!40000 ALTER TABLE `hoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang` (
  `makh` int NOT NULL AUTO_INCREMENT,
  `tenkh` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `diachi` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `sdt` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `taikhoan` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`makh`),
  KEY `fk_khachhang_taikhoan` (`taikhoan`),
  CONSTRAINT `fk_khachhang_taikhoan` FOREIGN KEY (`taikhoan`) REFERENCES `taikhoan` (`taikhoan`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang`
--

LOCK TABLES `khachhang` WRITE;
/*!40000 ALTER TABLE `khachhang` DISABLE KEYS */;
/*!40000 ALTER TABLE `khachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maytinh`
--

DROP TABLE IF EXISTS `maytinh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maytinh` (
  `mamt` int NOT NULL AUTO_INCREMENT,
  `tenmt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mota` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `gia` decimal(18,0) DEFAULT NULL,
  `hinhanh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `hang` int DEFAULT NULL,
  PRIMARY KEY (`mamt`),
  KEY `fk_maytinh_hang` (`hang`),
  CONSTRAINT `fk_maytinh_hang` FOREIGN KEY (`hang`) REFERENCES `hang` (`mahang`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maytinh`
--

LOCK TABLES `maytinh` WRITE;
/*!40000 ALTER TABLE `maytinh` DISABLE KEYS */;
INSERT INTO `maytinh` VALUES (1,'Laptop Asus Zenbook 14 OLED UX3405MA','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',26690000,'/images/maytinh/057f221a-9eca-42c9-9182-001f5eb15b4b.jpg',1),(2,'Laptop Asus Vivobook 14 OLED A1405VA','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',16590000,'/images/maytinh/ccb528c9-6155-4325-9e94-e6c9867e524f.jpg',1),(3,'Laptop Asus Vivobook S 14 Flip TP3402VA','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',22390000,'/images/maytinh/f8f4c044-8cdb-456c-ac42-bec39dab89ac.jpg',1),(4,'Laptop Lenovo IdeaPad Slim 3 14IAH8','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',13990000,'/images/maytinh/950ea53c-9ee9-4ef8-8d2d-8df725a74abe.jpg',2),(5,'Laptop Lenovo IdeaPad Slim 5 14IRL8','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',17990000,'/images/maytinh/6656c0e2-ac9a-4a04-b870-3874c666d9a1.png',2),(6,'Laptop Dell Vostro 3530 80GG92','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',11690000,'/images/maytinh/dce5aa35-dd03-4dc7-9e92-64fba046cc1b.jpg',3),(7,'Laptop Dell Inspiron T7430 N7430I58W1','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',20990000,'/images/maytinh/a2805e2f-105e-430f-b351-39873e1bbd9f.jpg',3),(8,'Laptop Dell XPS 13 9315','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',31490000,'/images/maytinh/df10457d-bf84-4b4e-ab03-e203c97c70da.jpg',3),(9,'Laptop Dell XPS 13 9315','NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc',28690000,'/images/maytinh/bf6ce1fd-7c8b-4883-9939-3f2fef09f534.jpg',3);
/*!40000 ALTER TABLE `maytinh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slide`
--

DROP TABLE IF EXISTS `slide`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slide` (
  `maslide` int NOT NULL AUTO_INCREMENT,
  `tieude` varchar(255) DEFAULT NULL,
  `hinhanh` text,
  `lienket` varchar(255) DEFAULT NULL,
  `trangthai` bit(1) DEFAULT NULL,
  PRIMARY KEY (`maslide`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slide`
--

LOCK TABLES `slide` WRITE;
/*!40000 ALTER TABLE `slide` DISABLE KEYS */;
INSERT INTO `slide` VALUES (1,'Đẳng cấp tạo nên sự khác biệt','/uploads/1765207936950-slide-1.png','/category/asus',_binary ''),(2,'Nhập học ngay - Quà liền tay','/uploads/1765207974552-slide-2.png','/category/lenovo',_binary '');
/*!40000 ALTER TABLE `slide` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taikhoan`
--

DROP TABLE IF EXISTS `taikhoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taikhoan` (
  `taikhoan` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `matkhau` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `quyen` bit(1) DEFAULT NULL,
  PRIMARY KEY (`taikhoan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taikhoan`
--

LOCK TABLES `taikhoan` WRITE;
/*!40000 ALTER TABLE `taikhoan` DISABLE KEYS */;
INSERT INTO `taikhoan` VALUES ('admin','admin',_binary ''),('test','test',_binary '\0');
/*!40000 ALTER TABLE `taikhoan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-13  0:21:11
