/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : db_mahasiswa

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2024-03-19 21:32:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `mahasiswa`
-- ----------------------------
DROP TABLE IF EXISTS `mahasiswa`;
CREATE TABLE `mahasiswa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nim` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `jenis_kelamin` varchar(255) NOT NULL,
  `tempat_tinggal` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of mahasiswa
-- ----------------------------
INSERT INTO `mahasiswa` VALUES ('1', '2203999', 'Amelia Zalfa Julianti', 'Perempuan', 'Kost');
INSERT INTO `mahasiswa` VALUES ('2', '2202292', 'Muhammad Iqbal Fadhilah', 'Laki-laki', 'Rumah');
INSERT INTO `mahasiswa` VALUES ('3', '2202346', 'Muhammad Rifky Afandi', 'Laki-laki', 'Kost');
INSERT INTO `mahasiswa` VALUES ('4', '2210239', 'Muhammad Hanif Abdillah', 'Laki-laki', 'Asrama');
INSERT INTO `mahasiswa` VALUES ('5', '2202046', 'Nurainun', 'Perempuan', 'Kost');
INSERT INTO `mahasiswa` VALUES ('6', '2205101', 'Kelvin Julian Putra', 'Laki-laki', 'Rumah');
INSERT INTO `mahasiswa` VALUES ('7', '2200163', 'Rifanny Lysara Annastasya', 'Perempuan', 'Kost');
INSERT INTO `mahasiswa` VALUES ('8', '2202869', 'Revana Faliha Salma', 'Perempuan', 'Asrama');
INSERT INTO `mahasiswa` VALUES ('9', '2209489', 'Rakha Dhifiargo Hariadi', 'Laki-laki', 'Kost');
INSERT INTO `mahasiswa` VALUES ('10', '2203142', 'Roshan Syalwan Nurilham', 'Laki-laki', 'Rumah');
INSERT INTO `mahasiswa` VALUES ('11', '2200311', 'Raden Rahman Ismail', 'Laki-laki', 'Kost');
INSERT INTO `mahasiswa` VALUES ('12', '2200978', 'Ratu Syahirah Khairunnisa', 'Perempuan', 'Asrama');
INSERT INTO `mahasiswa` VALUES ('13', '2204509', 'Muhammad Fahreza Fauzan', 'Laki-laki', 'Rumah');
INSERT INTO `mahasiswa` VALUES ('14', '2205027', 'Muhammad Rizki Revandi', 'Laki-laki', 'Kost');
INSERT INTO `mahasiswa` VALUES ('15', '2203484', 'Arya Aydin Margono', 'Laki-laki', 'Asrama');
INSERT INTO `mahasiswa` VALUES ('16', '2200481', 'Marvel Ravindra Dioputra', 'Laki-laki', 'Rumah');
INSERT INTO `mahasiswa` VALUES ('17', '2209889', 'Muhammad Fadlul Hafiizh', 'Laki-laki', 'Kost');
INSERT INTO `mahasiswa` VALUES ('18', '2206697', 'Rifa Sania', 'Perempuan', 'Kost');
INSERT INTO `mahasiswa` VALUES ('19', '2207260', 'Imam Chalish Rafidhul Haque', 'Laki-laki', 'Rumah');
INSERT INTO `mahasiswa` VALUES ('20', '2204343', 'Meiva Labibah Putri', 'Perempuan', 'Kost');
