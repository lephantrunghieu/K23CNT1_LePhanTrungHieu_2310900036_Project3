-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 04, 2025 at 08:51 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `webbanmt`
--

-- --------------------------------------------------------

--
-- Table structure for table `cthd`
--

CREATE TABLE `cthd` (
  `id` int(11) NOT NULL,
  `sohd` int(11) DEFAULT NULL,
  `maytinh` int(11) DEFAULT NULL,
  `soluong` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `giohang`
--

CREATE TABLE `giohang` (
  `magiohang` int(11) NOT NULL,
  `maytinh` int(11) DEFAULT NULL,
  `khachhang` int(11) DEFAULT NULL,
  `soluong` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hang`
--

CREATE TABLE `hang` (
  `mahang` int(11) NOT NULL,
  `tenhang` varchar(100) DEFAULT NULL,
  `duongdan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hang`
--

INSERT INTO `hang` (`mahang`, `tenhang`, `duongdan`) VALUES
(1, 'Asus', 'asus'),
(2, 'Lenovo', 'lenovo'),
(3, 'Dell', 'dell');

--
-- Triggers `hang`
--
DELIMITER $$
CREATE TRIGGER `tg_del_hang` BEFORE DELETE ON `hang` FOR EACH ROW BEGIN
	declare id int;
    declare count int default 0;
    set id = OLD.mahang;
    set count = (select count(*) from maytinh where hang = id);
    while count > 0
    do
		delete from maytinh where hang = id;
        set count = (select count(*) from maytinh where hang = id);
    end while;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

CREATE TABLE `hoadon` (
  `sohd` int(11) NOT NULL,
  `khachhang` int(11) DEFAULT NULL,
  `ngaydat` date DEFAULT NULL,
  `trangthai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `hoadon`
--
DELIMITER $$
CREATE TRIGGER `tg_del_hoadon` BEFORE DELETE ON `hoadon` FOR EACH ROW BEGIN
	declare id int;
    declare count int default 0;
    set id = OLD.sohd;
    set count = (select count(*) from cthd where sohd = id);
    while count > 0
    do
		delete from cthd where sohd = id;
        set count = (select count(*) from cthd where sohd = id);
    end while;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `makh` int(11) NOT NULL,
  `tenkh` varchar(100) DEFAULT NULL,
  `diachi` text DEFAULT NULL,
  `sdt` varchar(12) DEFAULT NULL,
  `taikhoan` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `khachhang`
--
DELIMITER $$
CREATE TRIGGER `tg_del_khachhang` BEFORE DELETE ON `khachhang` FOR EACH ROW BEGIN
	declare id int;
    declare count int default 0;
    set id = OLD.makh;
    set count = (select count(*) from hoadon where khachhang = id);
    while count > 0
    do
		delete from hoadon where khachhang = id;
        set count = (select count(*) from hoadon where khachhang = id);
    end while;
    set count = (select count(*) from lienhe where khachhang = id);
    while count > 0
    do
		delete from lienhe where khachhang = id;
        set count = (select count(*) from lienhe where khachhang = id);
    end while;
    set count = (select count(*) from giohang where khachhang = id);
    while count > 0
    do
		delete from giohang where khachhang = id;
        set count = (select count(*) from giohang where khachhang = id);
    end while;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `lienhe`
--

CREATE TABLE `lienhe` (
  `malienhe` int(11) NOT NULL,
  `tieude` varchar(100) DEFAULT NULL,
  `noidung` text DEFAULT NULL,
  `trangthai` int(11) DEFAULT NULL,
  `khachhang` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `maytinh`
--

CREATE TABLE `maytinh` (
  `mamt` int(11) NOT NULL,
  `tenmt` varchar(100) DEFAULT NULL,
  `mota` text DEFAULT NULL,
  `gia` decimal(18,0) DEFAULT NULL,
  `hinhanh` text DEFAULT NULL,
  `hang` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `maytinh`
--

INSERT INTO `maytinh` (`mamt`, `tenmt`, `mota`, `gia`, `hinhanh`, `hang`) VALUES
(1, 'Laptop Asus Zenbook 14 OLED UX3405MA', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 26690000, '/maytinh/maytinh-2024-03-28-20-29-03.jpg', 1),
(2, 'Laptop Asus Vivobook 14 OLED A1405VA', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 16590000, '/maytinh/maytinh-2024-03-28-20-29-42.jpg', 1),
(3, 'Laptop Asus Vivobook S 14 Flip TP3402VA', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 22390000, '/maytinh/maytinh-2024-03-28-20-30-26.jpg', 1),
(4, 'Laptop Lenovo IdeaPad Slim 3 14IAH8', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 13990000, '/maytinh/maytinh-2024-03-28-20-31-16.jpg', 2),
(5, 'Laptop Lenovo IdeaPad Slim 5 14IRL8', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 17990000, '/maytinh/maytinh-2024-03-28-20-31-39.jpg', 2),
(6, 'Laptop Dell Vostro 3530 80GG92', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 11690000, '/maytinh/maytinh-2024-03-28-20-32-27.jpg', 3),
(7, 'Laptop Dell Inspiron T7430 N7430I58W1', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 20990000, '/maytinh/maytinh-2024-03-28-20-32-59.jpg', 3),
(8, 'Laptop Dell XPS 13 9315', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 31490000, '/maytinh/maytinh-2024-03-28-20-33-22.jpg', 3),
(9, 'Laptop Dell XPS 13 9315', 'NPP: DGW\r\nCPU: Intel® Core™ Ultra 5 125H (3.60GHz up to 4.50GHz, 18MB Cache)\r\nRam: 16GB LPDDR5X 7467MHz on board\r\nỔ cứng: 512GB M.2 NVMe™ PCIe® 4.0 SSD\r\nVGA: Intel® Arc™ Graphics\r\nDisplay: 14.0inch 3K (2880 x 1800) Lumina OLED 16:10, 0.2ms, 120Hz, 400nits, 600nits HDR peak brightness, 100% DCI-P3 color gamut, 1,000,000:1, VESA CERTIFIED Display HDR True Black 600, TÜV Rheinland-certified\r\nPin: 4Cell 75WHrs, 2S2P\r\nColor: Bạc\r\nWeight: 1.20 kg\r\nTouchpad, support NumberPad, Backlit Chiclet Keyboard\r\nOS: Windows 11 Home bản quyền\r\nXuất xứ: Trung Quốc', 28690000, '/maytinh/maytinh-2024-03-28-20-33-58.jpg', 3);

--
-- Triggers `maytinh`
--
DELIMITER $$
CREATE TRIGGER `tg_del_maytinh` BEFORE DELETE ON `maytinh` FOR EACH ROW BEGIN
	declare id int;
    declare count int default 0;
    set id = OLD.mamt;
    set count = (select count(*) from cthd where maytinh = id);
    while count > 0
    do
		delete from cthd where maytinh = id;
        set count = (select count(*) from cthd where maytinh = id);
    end while;
    set count = (select count(*) from giohang where maytinh = id);
    while count > 0
    do
		delete from giohang where maytinh = id;
        set count = (select count(*) from giohang where maytinh = id);
    end while;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `taikhoan` varchar(100) NOT NULL,
  `matkhau` varchar(100) DEFAULT NULL,
  `quyen` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`taikhoan`, `matkhau`, `quyen`) VALUES
('admin', 'admin', b'1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cthd`
--
ALTER TABLE `cthd`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cthd_hoadon` (`sohd`),
  ADD KEY `fk_cthd_maytinh` (`maytinh`);

--
-- Indexes for table `giohang`
--
ALTER TABLE `giohang`
  ADD PRIMARY KEY (`magiohang`),
  ADD KEY `fk_giohang_maytinh` (`maytinh`),
  ADD KEY `fk_giohang_khachhang` (`khachhang`);

--
-- Indexes for table `hang`
--
ALTER TABLE `hang`
  ADD PRIMARY KEY (`mahang`);

--
-- Indexes for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`sohd`),
  ADD KEY `fk_hoadon_khachhang` (`khachhang`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`makh`),
  ADD KEY `fk_khachhang_taikhoan` (`taikhoan`);

--
-- Indexes for table `lienhe`
--
ALTER TABLE `lienhe`
  ADD PRIMARY KEY (`malienhe`),
  ADD KEY `fk_lienhe_khachhang` (`khachhang`);

--
-- Indexes for table `maytinh`
--
ALTER TABLE `maytinh`
  ADD PRIMARY KEY (`mamt`),
  ADD KEY `fk_maytinh_hang` (`hang`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`taikhoan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cthd`
--
ALTER TABLE `cthd`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `giohang`
--
ALTER TABLE `giohang`
  MODIFY `magiohang` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hang`
--
ALTER TABLE `hang`
  MODIFY `mahang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `sohd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `makh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `lienhe`
--
ALTER TABLE `lienhe`
  MODIFY `malienhe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `maytinh`
--
ALTER TABLE `maytinh`
  MODIFY `mamt` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cthd`
--
ALTER TABLE `cthd`
  ADD CONSTRAINT `fk_cthd_hoadon` FOREIGN KEY (`sohd`) REFERENCES `hoadon` (`sohd`),
  ADD CONSTRAINT `fk_cthd_maytinh` FOREIGN KEY (`maytinh`) REFERENCES `maytinh` (`mamt`);

--
-- Constraints for table `giohang`
--
ALTER TABLE `giohang`
  ADD CONSTRAINT `fk_giohang_khachhang` FOREIGN KEY (`khachhang`) REFERENCES `khachhang` (`makh`),
  ADD CONSTRAINT `fk_giohang_maytinh` FOREIGN KEY (`maytinh`) REFERENCES `maytinh` (`mamt`);

--
-- Constraints for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `fk_hoadon_khachhang` FOREIGN KEY (`khachhang`) REFERENCES `khachhang` (`makh`);

--
-- Constraints for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD CONSTRAINT `fk_khachhang_taikhoan` FOREIGN KEY (`taikhoan`) REFERENCES `taikhoan` (`taikhoan`);

--
-- Constraints for table `lienhe`
--
ALTER TABLE `lienhe`
  ADD CONSTRAINT `fk_lienhe_khachhang` FOREIGN KEY (`khachhang`) REFERENCES `khachhang` (`makh`);

--
-- Constraints for table `maytinh`
--
ALTER TABLE `maytinh`
  ADD CONSTRAINT `fk_maytinh_hang` FOREIGN KEY (`hang`) REFERENCES `hang` (`mahang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
