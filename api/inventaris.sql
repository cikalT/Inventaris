-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 11, 2019 at 08:19 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventaris`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCount` ()  NO SQL
SELECT
	(SELECT COUNT(*)
	FROM tb_petugas WHERE IDLevel_='2'
	) AS JumlahOperator_,
    (SELECT COUNT(*)
	FROM tb_pegawai
	) AS JumlahPegawai_,
	(SELECT COUNT(*)
	FROM tb_peminjaman
	) AS JumlahPinjaman_
	FROM DUAL$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getInvent` ()  NO SQL
SELECT tb_inventaris.IDInvent_, tb_inventaris.NamaInvent_, tb_inventaris.KondisiInvent_, tb_inventaris.KetInvent_, tb_inventaris.Jumlah_, tb_inventaris.IDJenis_, tb_inventaris.IDRuang_, tb_inventaris.TglRegis_, tb_inventaris.KodeInvent_, tb_jenis.NamaJenis_, tb_ruang.NamaRuang_ FROM ((tb_inventaris
INNER JOIN tb_jenis ON tb_inventaris.IDJenis_ = tb_jenis.IDJenis_)
INNER JOIN tb_ruang ON tb_inventaris.IDRuang_ = tb_ruang.IDRuang_)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getJenis` ()  NO SQL
SELECT * FROM tb_jenis$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getOperator` ()  NO SQL
SELECT * FROM tb_petugas WHERE IDLevel_='2'$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getPegawai` ()  NO SQL
SELECT * FROM tb_pegawai$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReport` ()  NO SQL
SELECT tb_peminjaman.IDPeminjaman_, tb_peminjaman.IDInvent_, tb_peminjaman.IDPegawai_, tb_peminjaman.Jumlah_, tb_peminjaman.TglPinjam_, tb_peminjaman.TglKembali_, tb_peminjaman.StatusPinjam_, tb_peminjaman.KodePinjaman_, tb_inventaris.NamaInvent_, tb_pegawai.NamaPegawai_ FROM ((tb_peminjaman
INNER JOIN tb_inventaris ON tb_peminjaman.IDInvent_ = tb_inventaris.IDInvent_)
INNER JOIN tb_pegawai ON tb_peminjaman.IDPegawai_ = tb_pegawai.IDPegawai_) ORDER BY tb_peminjaman.TglPinjam_ ASC$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getRuang` ()  NO SQL
SELECT * FROM tb_ruang$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_detail_pinjam`
--

CREATE TABLE `tb_detail_pinjam` (
  `IDDetailPinjam_` int(11) NOT NULL,
  `IDPeminjaman_` int(11) NOT NULL,
  `IDInvent_` int(11) NOT NULL,
  `Jumlah_` int(3) NOT NULL,
  `Tgl_` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_detail_pinjam`
--

INSERT INTO `tb_detail_pinjam` (`IDDetailPinjam_`, `IDPeminjaman_`, `IDInvent_`, `Jumlah_`, `Tgl_`) VALUES
(1, 1, 1, 1, '2019-04-07'),
(3, 3, 2, 2, '2019-04-10'),
(5, 5, 5, 1, '2019-04-11'),
(6, 6, 2, 2, '2019-04-11'),
(8, 8, 2, 1, '2019-04-11'),
(9, 9, 2, 1, '2019-04-11'),
(10, 10, 2, 1, '2019-04-11'),
(11, 11, 1, 1, '2019-04-11'),
(12, 12, 2, 1, '2019-04-11');

-- --------------------------------------------------------

--
-- Table structure for table `tb_inventaris`
--

CREATE TABLE `tb_inventaris` (
  `IDInvent_` int(11) NOT NULL,
  `NamaInvent_` varchar(30) NOT NULL,
  `KondisiInvent_` tinyint(1) NOT NULL COMMENT '1=baru, 2=baik, 3=kurang baik',
  `KetInvent_` tinyint(1) NOT NULL COMMENT '1=barang inventaris, 2=barang habis pakai',
  `Jumlah_` int(3) NOT NULL,
  `IDJenis_` int(11) NOT NULL,
  `IDRuang_` int(11) NOT NULL,
  `TglRegis_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `KodeInvent_` varchar(8) NOT NULL,
  `IDPetugas_` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_inventaris`
--

INSERT INTO `tb_inventaris` (`IDInvent_`, `NamaInvent_`, `KondisiInvent_`, `KetInvent_`, `Jumlah_`, `IDJenis_`, `IDRuang_`, `TglRegis_`, `KodeInvent_`, `IDPetugas_`) VALUES
(1, 'Proyektor', 2, 1, 22, 2, 1, '2019-04-11 06:17:08', 'BG648254', 1),
(2, 'Gergaji', 2, 1, 4, 3, 9, '2019-04-11 06:16:46', 'BG957261', 1),
(5, 'Keyboard', 2, 1, 26, 2, 11, '2019-04-11 03:50:33', 'BG026874', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_jenis`
--

CREATE TABLE `tb_jenis` (
  `IDJenis_` int(11) NOT NULL,
  `NamaJenis_` varchar(20) NOT NULL,
  `KodeJenis_` varchar(8) NOT NULL,
  `KetJenis_` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_jenis`
--

INSERT INTO `tb_jenis` (`IDJenis_`, `NamaJenis_`, `KodeJenis_`, `KetJenis_`) VALUES
(1, 'Alat Tulis', 'JN185601', ''),
(2, 'Elektronik', 'JN185602', ''),
(3, 'Perkakas', 'JN185603', ''),
(4, 'Mebel', 'JN185604', ''),
(5, 'Buku', 'JN185605', ''),
(6, 'Mesin', 'JN185606', '');

-- --------------------------------------------------------

--
-- Table structure for table `tb_level`
--

CREATE TABLE `tb_level` (
  `IDLevel_` int(11) NOT NULL,
  `NamaLevel_` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_level`
--

INSERT INTO `tb_level` (`IDLevel_`, `NamaLevel_`) VALUES
(1, 'Admin'),
(2, 'Operator');

-- --------------------------------------------------------

--
-- Table structure for table `tb_pegawai`
--

CREATE TABLE `tb_pegawai` (
  `IDPegawai_` int(11) NOT NULL,
  `NamaPegawai_` varchar(30) NOT NULL,
  `Nip_` varchar(15) NOT NULL,
  `Alamat_` text NOT NULL,
  `Username_` varchar(20) NOT NULL,
  `Password_` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_pegawai`
--

INSERT INTO `tb_pegawai` (`IDPegawai_`, `NamaPegawai_`, `Nip_`, `Alamat_`, `Username_`, `Password_`) VALUES
(1, 'Genadi Dharma', '19936758391202', 'Sesetan', 'gen', 'gen'),
(2, 'Dhiva Tiradika', '', 'Renon', 'dhiva', 'dhiva'),
(3, 'Simayodika', '19738940573957', 'Antasura', 'sima', 'sima'),
(4, 'Risky Arya', '12345678901', 'Dalung', 'ris', 'ris'),
(5, 'Pradnya Wiguna', '', 'Cekomaria', 'prad', 'prad'),
(6, 'Ramdana', '', 'Denpasar', 'ram', 'ram');

-- --------------------------------------------------------

--
-- Table structure for table `tb_peminjaman`
--

CREATE TABLE `tb_peminjaman` (
  `IDPeminjaman_` int(11) NOT NULL,
  `IDInvent_` int(11) NOT NULL,
  `IDPegawai_` int(11) NOT NULL,
  `Jumlah_` int(3) NOT NULL,
  `TglPinjam_` date NOT NULL,
  `TglKembali_` date NOT NULL,
  `StatusPinjam_` tinyint(1) NOT NULL COMMENT '1=dipinjam, 2=dikembalikan, 3=terlambat',
  `KodePinjaman_` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_peminjaman`
--

INSERT INTO `tb_peminjaman` (`IDPeminjaman_`, `IDInvent_`, `IDPegawai_`, `Jumlah_`, `TglPinjam_`, `TglKembali_`, `StatusPinjam_`, `KodePinjaman_`) VALUES
(1, 1, 1, 1, '2019-04-07', '2019-04-09', 3, 'KD609482'),
(3, 2, 2, 2, '2019-04-10', '2019-04-10', 1, 'KD593147'),
(5, 5, 5, 1, '2019-04-11', '2019-04-11', 2, 'KD621074'),
(6, 2, 4, 2, '2019-04-11', '2019-04-12', 2, 'KD237419'),
(8, 2, 2, 1, '2019-04-11', '2019-04-11', 1, 'KD493071'),
(9, 2, 3, 1, '2019-04-11', '2019-04-11', 3, 'KD716953'),
(10, 2, 3, 1, '2019-04-11', '2019-04-12', 2, 'KD518647'),
(11, 1, 3, 1, '2019-04-11', '2019-04-10', 3, 'KD481705'),
(12, 2, 1, 1, '2019-04-11', '2019-04-12', 2, 'KD174053');

--
-- Triggers `tb_peminjaman`
--
DELIMITER $$
CREATE TRIGGER `kurangJumlah` AFTER INSERT ON `tb_peminjaman` FOR EACH ROW BEGIN

	DECLARE v_count INT DEFAULT 0;
    
    SELECT Jumlah_ FROM tb_inventaris WHERE tb_inventaris.IDInvent_ = NEW.IDInvent_ INTO v_count;
    
    IF v_count >= NEW.Jumlah_ THEN
    UPDATE tb_inventaris SET Jumlah_ = Jumlah_ - NEW.Jumlah_ WHERE 	tb_inventaris.IDInvent_ = NEW.IDInvent_;
    ELSE
    SIGNAL SQLSTATE'45000' SET MESSAGE_TEXT = 'Jumlah Yang dimasukkan melebihi stok';
    END IF;

END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tambahJumlah` AFTER UPDATE ON `tb_peminjaman` FOR EACH ROW BEGIN
	
    UPDATE tb_inventaris SET Jumlah_ = Jumlah_ + NEW.Jumlah_ WHERE IDInvent_ = NEW.IDInvent_;
    
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_petugas`
--

CREATE TABLE `tb_petugas` (
  `IDPetugas_` int(11) NOT NULL,
  `NamaPetugas_` varchar(30) NOT NULL,
  `IDLevel_` int(11) NOT NULL,
  `Username_` varchar(20) NOT NULL,
  `Password_` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_petugas`
--

INSERT INTO `tb_petugas` (`IDPetugas_`, `NamaPetugas_`, `IDLevel_`, `Username_`, `Password_`) VALUES
(1, 'Admin Inventaris', 1, 'admin', 'admin'),
(2, 'Cikal Taruna', 2, 'cikal', 'cikal'),
(3, 'Surya Buana', 2, 'sur', 'sur'),
(4, 'Alit Adiyana', 2, 'alit', 'alit'),
(5, 'Gustana Satiawan', 2, 'gus', 'gus');

-- --------------------------------------------------------

--
-- Table structure for table `tb_ruang`
--

CREATE TABLE `tb_ruang` (
  `IDRuang_` int(11) NOT NULL,
  `NamaRuang_` varchar(20) NOT NULL,
  `KodeRuang_` varchar(8) NOT NULL,
  `KetRuang_` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_ruang`
--

INSERT INTO `tb_ruang` (`IDRuang_`, `NamaRuang_`, `KodeRuang_`, `KetRuang_`) VALUES
(1, 'Ruang Guru', 'RG185601', ''),
(2, 'Ruang Osis', 'RG185602', ''),
(3, 'Tata Usaha', 'RG185603', ''),
(4, 'Koperasi', 'RG185604', ''),
(5, 'Gudang', 'RG185605', ''),
(6, 'Perpustakaan', 'RG185606', ''),
(7, 'Bengkel Mesin', 'RG185607', ''),
(8, 'Bengkel Sepeda Motor', 'RG185608', ''),
(9, 'Lab Bangunan', 'RG185609', ''),
(10, 'Lab Elektro', 'RG185610', ''),
(11, 'Lab RPL', 'RG185611', ''),
(12, 'Lab TKJ', 'RG185612', ''),
(13, 'Lab Multimedia', 'RG185613', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_detail_pinjam`
--
ALTER TABLE `tb_detail_pinjam`
  ADD PRIMARY KEY (`IDDetailPinjam_`),
  ADD KEY `IDPeminjaman_` (`IDPeminjaman_`),
  ADD KEY `IDInvent_` (`IDInvent_`);

--
-- Indexes for table `tb_inventaris`
--
ALTER TABLE `tb_inventaris`
  ADD PRIMARY KEY (`IDInvent_`),
  ADD KEY `IDJenis_` (`IDJenis_`),
  ADD KEY `IDRuang_` (`IDRuang_`),
  ADD KEY `IDPetugas_` (`IDPetugas_`);

--
-- Indexes for table `tb_jenis`
--
ALTER TABLE `tb_jenis`
  ADD PRIMARY KEY (`IDJenis_`);

--
-- Indexes for table `tb_level`
--
ALTER TABLE `tb_level`
  ADD PRIMARY KEY (`IDLevel_`);

--
-- Indexes for table `tb_pegawai`
--
ALTER TABLE `tb_pegawai`
  ADD PRIMARY KEY (`IDPegawai_`);

--
-- Indexes for table `tb_peminjaman`
--
ALTER TABLE `tb_peminjaman`
  ADD PRIMARY KEY (`IDPeminjaman_`),
  ADD KEY `IDInvent_` (`IDInvent_`),
  ADD KEY `IDPegawai_` (`IDPegawai_`);

--
-- Indexes for table `tb_petugas`
--
ALTER TABLE `tb_petugas`
  ADD PRIMARY KEY (`IDPetugas_`),
  ADD KEY `IDlevel_` (`IDLevel_`);

--
-- Indexes for table `tb_ruang`
--
ALTER TABLE `tb_ruang`
  ADD PRIMARY KEY (`IDRuang_`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_detail_pinjam`
--
ALTER TABLE `tb_detail_pinjam`
  MODIFY `IDDetailPinjam_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tb_inventaris`
--
ALTER TABLE `tb_inventaris`
  MODIFY `IDInvent_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tb_jenis`
--
ALTER TABLE `tb_jenis`
  MODIFY `IDJenis_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tb_level`
--
ALTER TABLE `tb_level`
  MODIFY `IDLevel_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_pegawai`
--
ALTER TABLE `tb_pegawai`
  MODIFY `IDPegawai_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tb_peminjaman`
--
ALTER TABLE `tb_peminjaman`
  MODIFY `IDPeminjaman_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tb_petugas`
--
ALTER TABLE `tb_petugas`
  MODIFY `IDPetugas_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tb_ruang`
--
ALTER TABLE `tb_ruang`
  MODIFY `IDRuang_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_detail_pinjam`
--
ALTER TABLE `tb_detail_pinjam`
  ADD CONSTRAINT `tb_detail_pinjam_ibfk_1` FOREIGN KEY (`IDPeminjaman_`) REFERENCES `tb_peminjaman` (`IDPeminjaman_`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_detail_pinjam_ibfk_2` FOREIGN KEY (`IDInvent_`) REFERENCES `tb_inventaris` (`IDInvent_`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_inventaris`
--
ALTER TABLE `tb_inventaris`
  ADD CONSTRAINT `tb_inventaris_ibfk_1` FOREIGN KEY (`IDRuang_`) REFERENCES `tb_ruang` (`IDRuang_`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_inventaris_ibfk_2` FOREIGN KEY (`IDJenis_`) REFERENCES `tb_jenis` (`IDJenis_`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_inventaris_ibfk_4` FOREIGN KEY (`IDPetugas_`) REFERENCES `tb_petugas` (`IDPetugas_`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_peminjaman`
--
ALTER TABLE `tb_peminjaman`
  ADD CONSTRAINT `tb_peminjaman_ibfk_1` FOREIGN KEY (`IDPegawai_`) REFERENCES `tb_pegawai` (`IDPegawai_`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_peminjaman_ibfk_2` FOREIGN KEY (`IDInvent_`) REFERENCES `tb_inventaris` (`IDInvent_`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_petugas`
--
ALTER TABLE `tb_petugas`
  ADD CONSTRAINT `tb_petugas_ibfk_1` FOREIGN KEY (`IDlevel_`) REFERENCES `tb_level` (`IDLevel_`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_petugas_ibfk_2` FOREIGN KEY (`IDLevel_`) REFERENCES `tb_level` (`IDLevel_`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
