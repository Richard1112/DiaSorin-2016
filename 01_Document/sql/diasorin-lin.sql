/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50618
Source Host           : localhost:3306
Source Database       : diasorin

Target Server Type    : MYSQL
Target Server Version : 50618
File Encoding         : 65001

Date: 2015-06-01 19:16:36
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_employee_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee_info`;
CREATE TABLE `t_employee_info` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `employeeNo` varchar(10) NOT NULL,
  `employeeNameCn` varchar(100) DEFAULT NULL,
  `employeeNameEn` varchar(100) DEFAULT NULL,
  `headPic` varchar(50) DEFAULT NULL,
  `sex` varchar(6) DEFAULT NULL,
  `levelCode` varchar(10) NOT NULL,
  `roleCode` varchar(10) NOT NULL,
  `deptCode` varchar(10) DEFAULT NULL,
  `serviceCostCenter` varchar(100) DEFAULT NULL,
  `mobilePhone` varchar(15) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `contactAddress` varchar(256) DEFAULT NULL,
  `liveLocation` varchar(100) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee_info
-- ----------------------------
INSERT INTO `t_employee_info` VALUES ('1', 'CNE00003', '施云光', 'Roy Shi', null, '0', 'L001', 'R004', 'CN3500', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('2', 'CNE00017', '桂忠', 'Jones', null, '0', 'L001', 'R004', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('3', 'CNE00012', '张君', 'Will', null, '0', 'L002', 'R007', 'CN3435', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('4', 'CNE00010', '史雯', 'Kris', null, '0', 'L002', 'R007', 'CN3500', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('5', 'CNE00091', '刘晓君', 'Liu Xiaojun', null, '0', 'L002', 'R008', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('6', 'CNE00020', '董路平', 'Jon', null, '0', 'L002', 'R008', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('7', 'CNE00019', '王磊', 'Leo', null, '0', 'L002', 'R008', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('8', 'CNE00022', '江阳', 'Jiang', null, '0', 'L001', 'R004', 'CN3435', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('9', 'CNE00016', '厉伽佳', 'Shirley Li', null, '0', 'L002', 'R007', 'CN3187', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('10', 'CNE00026', '沈小秀', 'Ella', null, '0', 'L002', 'R007', 'CN3372', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('11', 'CNE00025', '范凯钦', 'Kenny', null, '0', 'L001', 'R004', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('12', 'CNE00033', '国雷', 'GuoLei', null, '0', 'L001', 'R004', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('13', 'CNE00038', '陈波', 'Chen Bo', null, '0', 'L001', 'R004', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('14', 'CNE00037', '解满治', 'Tony', null, '0', 'L002', 'R008', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('15', 'CNE00039', '张建明', 'Jianmin Zhang', null, '0', 'L002', 'R008', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('16', 'CNE00043', '刘叶峰', 'Eric Liu', null, '0', 'L001', 'R004', 'CN3187', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('17', 'CNE00048', '朱丽', 'Zhu Li', null, '0', 'L002', 'R008', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('18', 'CNE00093', '奚鹰', 'Cici Xi', null, '0', 'L002', 'R007', 'CN3372', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('19', 'CNE00050', '肖少华', 'Mason Xiao', null, '0', 'L001', 'R004', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('20', 'CNE00052', '王成梅', 'Wang Chenmei', null, '0', 'L001', 'R003', 'CN3372', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('21', 'CNE00055', '胡博文', 'Hu Bowen', null, '0', 'L002', 'R008', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('22', 'CNE00056', '赵唯', 'Jerry Zhao', null, '0', 'L001', 'R004', 'CN3500', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('23', 'CNE00058', '陶见利', 'Jenny Tao', null, '0', 'L002', 'R008', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('24', 'CNE00059', '张洪荣', 'Lily Zhang', null, '0', 'L002', 'R007', 'CN3372', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('25', 'CNE00060', '郑菲', 'Faye Zheng', null, '0', 'L002', 'R007', 'CN3500', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('26', 'CNE00063', '孟心宇', 'Katherine Meng', null, '0', 'L002', 'R007', 'CN3372', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('27', 'CNE00061', '邸薇', 'Di Wei', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('28', 'CNE00065', '陈仙山', 'Chen Xianshan', null, '0', 'L002', 'R008', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('29', 'CNE10067', '杨文霞', 'Ivy Yang', null, '0', 'L002', 'R007', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('30', 'CNE00071', '程华丽', 'Cheng Huali', null, '0', 'L002', 'R007', 'CN3372', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('31', 'CNE00077', '刘星', 'Liu Xin', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('32', 'CNE00080', '李南', 'Li Nan', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('33', 'CNE00074', '霍伟', 'Huo Wei', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('34', 'CNE00076', '王志国', 'Wang Zhiguo', null, '0', 'L002', 'R008', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('35', 'CNE00079', '杨颖', 'Yang Yin', null, '0', 'L002', 'R008', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('36', 'CNE00078', '张琛', 'Zhang Chen', null, '0', 'L002', 'R008', 'CN3435', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('37', 'CNE00081', '徐晓娟', 'Jessica Xu', null, '0', 'L001', 'R004', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('38', 'CNE00082', '葛静怡', 'Jeanie Ge', null, '0', 'L002', 'R007', 'CN3435', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('39', 'CNE00088', '周包成', 'Zhou Baochen', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('40', 'CNE00089', '黄青云', 'Huang Qingyun', null, '0', 'L002', 'R007', 'CN3187', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('41', 'CNE00083', '胡伟斌', 'Hu WeiBin', null, '0', 'L002', 'R007', 'CN3435', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('42', 'CNE00086', '佟斯博', 'Tong Sibo', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('43', 'CNE00087', '王自全', 'Wang Ziquan', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('44', 'CNE00084', '孙占伟', 'Sun Zhanwei', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('45', 'CNE00046', '何建文', 'Jianwen He', null, '0', 'L001', 'R003', 'CN3514', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('46', 'CNE00094', '杨晨燕', 'Julia', null, '0', 'L001', 'R004', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('47', 'CNE00098', '胡颖萍', 'Hu Yinping', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('48', 'CNE00090', '刘虓', 'Liu Xiao', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('49', 'CNE00097', '周鸣宇', 'Zhou Mingyu', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('50', 'CNE00095', '张凯', 'Zhang Kai', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('51', 'CNE00096', '王玺晟', 'Wang Xisheng', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('52', 'CNE00092', '汪洋', 'Wang Yang', null, '0', 'L001', 'R004', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('53', 'CNE00099', '李强', 'Li Qiang', null, '0', 'L001', 'R004', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('54', 'CNE00102', '杨益伟', 'Yang Yiwei', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('55', 'CNE00100', 'Tomas Charamza', 'Tomas Charamza', null, '0', 'L001', 'R002', 'CN3500', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('56', 'CNE00103', 'Valentina Anro', 'Valentina Anro', null, '0', 'L001', 'R004', 'CN3435', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('57', 'CNE00001', 'Fabio Piazzalunga', 'Fabio Piazzalunga', null, '0', 'L001', 'R001', 'CN3614', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('58', 'CNE00021', 'Nikhil Bedekar', 'Nikhil Bedekar', null, '0', 'L001', 'R004', 'CN3440', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('59', 'CNE00023', 'Theodora Davy', 'Theodora Davy', null, '0', 'L001', 'R004', 'CN3445', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('60', 'CNE00044', 'Colman Hung', 'Colman Hung', null, '0', 'L001', 'R004', 'CN3814', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('61', 'CNE00053', 'Anna Khomenko', 'Anna Khomenko', null, '0', 'L002', 'R007', 'CN3914', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('62', 'CNE00068', '王兆赫', 'Wang Zhaohe', null, '0', 'L002', 'R007', 'CN3440', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('63', 'CNE00070', '黄晓颖', 'Huang Xiaoying', null, '0', 'L001', 'R004', 'CN3714', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('64', 'CNE00085', '朱健', 'Zhu Jian', null, '0', 'L002', 'R007', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('65', 'CNE00101', '张凯', 'Zhang Kai', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('66', 'CNE10001', 'Wang Yi', 'Wang Yi', null, '0', 'L002', 'R009', 'CN3500', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('67', 'CNE10008', 'Intern', 'Intern', null, '0', 'L002', 'R007', 'CN3435', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('68', 'CNE10009', 'Candidate', 'Candidate', null, '0', 'L002', 'R007', 'CN3714', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('69', 'CNE00104', '常岗', 'Chang Gang', null, '0', 'L002', 'R007', 'CN3020', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('70', 'CNE00105', '李杰', 'Jeffrey Li', null, '0', 'L002', 'R007', 'CN3425', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_info` VALUES ('71', 'DiaSorin', 'IT管理者', 'IT Manager', null, '0', 'L001', 'R010', 'CN3500', '', '', '', '', '', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');

-- ----------------------------
-- Table structure for `t_employee_login`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee_login`;
CREATE TABLE `t_employee_login` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `employeeNo` varchar(10) NOT NULL,
  `employeePassword` varchar(48) NOT NULL,
  `loginStatus` char(1) NOT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee_login
-- ----------------------------
INSERT INTO `t_employee_login` VALUES ('1', 'CNE00003', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('2', 'CNE00017', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('3', 'CNE00012', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('4', 'CNE00010', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('5', 'CNE00091', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('6', 'CNE00020', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('7', 'CNE00019', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('8', 'CNE00022', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('9', 'CNE00016', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('10', 'CNE00026', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('11', 'CNE00025', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('12', 'CNE00033', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('13', 'CNE00038', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('14', 'CNE00037', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('15', 'CNE00039', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('16', 'CNE00043', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('17', 'CNE00048', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('18', 'CNE00093', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('19', 'CNE00050', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('20', 'CNE00052', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('21', 'CNE00055', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('22', 'CNE00056', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('23', 'CNE00058', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('24', 'CNE00059', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('25', 'CNE00060', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('26', 'CNE00063', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('27', 'CNE00061', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('28', 'CNE00065', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('29', 'CNE10067', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('30', 'CNE00071', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('31', 'CNE00077', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('32', 'CNE00080', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('33', 'CNE00074', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('34', 'CNE00076', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('35', 'CNE00079', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('36', 'CNE00078', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('37', 'CNE00081', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('38', 'CNE00082', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('39', 'CNE00088', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('40', 'CNE00089', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('41', 'CNE00083', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('42', 'CNE00086', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('43', 'CNE00087', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('44', 'CNE00084', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('45', 'CNE00046', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('46', 'CNE00094', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('47', 'CNE00098', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('48', 'CNE00090', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('49', 'CNE00097', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('50', 'CNE00095', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('51', 'CNE00096', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('52', 'CNE00092', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('53', 'CNE00099', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('54', 'CNE00102', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('55', 'CNE00100', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('56', 'CNE00103', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('57', 'CNE00001', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('58', 'CNE00021', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('59', 'CNE00023', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('60', 'CNE00044', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('61', 'CNE00053', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('62', 'CNE00068', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('63', 'CNE00070', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('64', 'CNE00085', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('65', 'CNE00101', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('66', 'CNE10001', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('67', 'CNE10008', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('68', 'CNE10009', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('69', 'CNE00104', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('70', 'CNE00105', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-04-01 00:00:00', 'admin', 'basicData');
INSERT INTO `t_employee_login` VALUES ('71', 'DiaSorin', '123456', '0', '0', '2015-04-01 00:00:00', 'admin', '2015-06-01 19:16:13', 'admin', 'basicData');

-- ----------------------------
-- Table structure for `t_employee_login_history`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee_login_history`;
CREATE TABLE `t_employee_login_history` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `employeeNo` varchar(10) NOT NULL,
  `loginTimestamp` timestamp NULL DEFAULT NULL,
  `logoutTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee_login_history
-- ----------------------------

-- ----------------------------
-- Table structure for `t_expenses_application`
-- ----------------------------
DROP TABLE IF EXISTS `t_expenses_application`;
CREATE TABLE `t_expenses_application` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expensesAppNo` varchar(20) NOT NULL,
  `employeeNo` varchar(10) NOT NULL,
  `applicationDate` varchar(8) NOT NULL,
  `costCenterCode` varchar(10) NOT NULL,
  `travelLocalType` varchar(4) NOT NULL,
  `travelReason` varchar(100) DEFAULT NULL,
  `travelDateStart` varchar(8) DEFAULT NULL,
  `travelDateEnd` varchar(8) DEFAULT NULL,
  `travelAppNo` varchar(16) DEFAULT NULL,
  `expenseSum` decimal(12,2) DEFAULT NULL,
  `status` varchar(6) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_expenses_application
-- ----------------------------

-- ----------------------------
-- Table structure for `t_expenses_approve_rules`
-- ----------------------------
DROP TABLE IF EXISTS `t_expenses_approve_rules`;
CREATE TABLE `t_expenses_approve_rules` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `ruleId` varchar(10) NOT NULL,
  `nodeId` varchar(10) NOT NULL,
  `approveRoleCode` varchar(10) NOT NULL,
  `approveCondition` varchar(10) DEFAULT NULL,
  `approveExpenseType` varchar(4) DEFAULT NULL,
  `approveAmount` varchar(10) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_expenses_approve_rules
-- ----------------------------
INSERT INTO `t_expenses_approve_rules` VALUES ('1', 'EAR001', 'WFND01', 'R004', '0', 'S000', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_expenses_approve_rules` VALUES ('2', 'EAR002', 'WFND02', 'R004', '0', 'S000', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_expenses_approve_rules` VALUES ('3', 'EAR003', 'WFND03', 'R002', '0', 'S000', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_expenses_approve_rules` VALUES ('4', 'EAR004', 'WFND04', 'R001', '1', 'S000', '50000', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_expenses_approve_rules` VALUES ('5', 'EAR005', 'WFND04', 'R001', '1', 'S011', '5000', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_expenses_approve_rules` VALUES ('6', 'EAR006', 'WFND05', 'R005', '0', 'S000', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');

-- ----------------------------
-- Table structure for `t_expenses_details`
-- ----------------------------
DROP TABLE IF EXISTS `t_expenses_details`;
CREATE TABLE `t_expenses_details` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expensesDetailsNo` varchar(30) NOT NULL,
  `belongExpensesAppNo` varchar(25) NOT NULL,
  `expensesDateType` varchar(6) NOT NULL,
  `expensesDate` varchar(8) DEFAULT NULL,
  `expensesDateEnd` varchar(8) DEFAULT NULL,
  `travelLocation` varchar(100) DEFAULT NULL,
  `expensesItem` varchar(4) DEFAULT NULL,
  `kilometers` decimal(6, 2) DEFAULT NULL,
  `expensesAmount` decimal(12,2) DEFAULT NULL,
  `expensesComments` varchar(100) DEFAULT NULL,
  `rejectErrorFlg` char(1) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_expenses_details
-- ----------------------------

-- ----------------------------
-- Table structure for `t_expenses_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `t_expenses_parameter`;
CREATE TABLE `t_expenses_parameter` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `travelLocal` varchar(6) NOT NULL,
  `expenseCode` varchar(4) NOT NULL,
  `employeeLevelCode` varchar(4) NOT NULL,
  `allowExpensesUp` decimal(12,2) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_expenses_parameter
-- ----------------------------
INSERT INTO `t_expenses_parameter` VALUES ('1', '1', 'S017', 'L001', '1000.00');
INSERT INTO `t_expenses_parameter` VALUES ('2', '1', 'S017', 'L002', '600.00');

-- ----------------------------
-- Table structure for `t_expenses_purpose_sum`
-- ----------------------------
DROP TABLE IF EXISTS `t_expenses_purpose_sum`;
CREATE TABLE `t_expenses_purpose_sum` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expensesDetailsNo` varchar(25) NOT NULL,
  `belongExpensesAppNo` varchar(20) NOT NULL,
  `expensesPurpose` varchar(100) DEFAULT NULL,
  `expensesAmount` decimal(12,2) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_expenses_purpose_sum
-- ----------------------------

-- ----------------------------
-- Table structure for `t_no_expenses_app`
-- ----------------------------
DROP TABLE IF EXISTS `t_no_expenses_app`;
CREATE TABLE `t_no_expenses_app` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `currentYear` varchar(8) NOT NULL,
  `maxAppNumber` varchar(12) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_no_expenses_app
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_code`;
CREATE TABLE `t_sys_code` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `codeId` varchar(6) NOT NULL,
  `codeDetailId` varchar(6) NOT NULL,
  `codeName` varchar(100) NOT NULL,
  `codeDetailName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_code
-- ----------------------------
INSERT INTO `t_sys_code` VALUES ('1', 'COM001', '0', 'Sex', 'Male');
INSERT INTO `t_sys_code` VALUES ('2', 'COM001', '1', 'Sex', 'Female');
INSERT INTO `t_sys_code` VALUES ('3', 'COM002', '0', 'Sign Status', 'Sign Out');
INSERT INTO `t_sys_code` VALUES ('4', 'COM002', '1', 'Sign Status', 'Sign In');
INSERT INTO `t_sys_code` VALUES ('5', 'COM003', '0', 'Expenses Status', 'Temporal Save');
INSERT INTO `t_sys_code` VALUES ('6', 'COM003', '1', 'Expenses Status', 'Application');
INSERT INTO `t_sys_code` VALUES ('7', 'COM003', '2', 'Expenses Status', 'Pending');
INSERT INTO `t_sys_code` VALUES ('8', 'COM003', '3', 'Expenses Status', 'Reject');
INSERT INTO `t_sys_code` VALUES ('9', 'COM003', '4', 'Expenses Status', 'Approved');
INSERT INTO `t_sys_code` VALUES ('10', 'COM003', '5', 'Expenses Status', 'Finish');
INSERT INTO `t_sys_code` VALUES ('11', 'COM004', '1', 'Travel Local Type', 'Domestic');
INSERT INTO `t_sys_code` VALUES ('12', 'COM004', '2', 'Travel Local Type', 'Hong Kong, Macao And Taiwan');
INSERT INTO `t_sys_code` VALUES ('13', 'COM004', '3', 'Travel Local Type', 'Overseas other than Great China');
INSERT INTO `t_sys_code` VALUES ('14', 'COM005', '1', 'Workflow Process Status', 'Todo');
INSERT INTO `t_sys_code` VALUES ('15', 'COM005', '2', 'Workflow Process Status', 'Approved');
INSERT INTO `t_sys_code` VALUES ('16', 'COM005', '3', 'Workflow Process Status', 'Reject');

-- ----------------------------
-- Table structure for `t_sys_cost_center`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_cost_center`;
CREATE TABLE `t_sys_cost_center` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `costCenterCode` varchar(10) NOT NULL,
  `costCenterName` varchar(100) NOT NULL,
  `costCenterDisplayName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_cost_center
-- ----------------------------
INSERT INTO `t_sys_cost_center` VALUES ('1', 'CN3020', 'After Market Services', 'After Market Services');
INSERT INTO `t_sys_cost_center` VALUES ('2', 'CN3187', 'Logistic Costs', 'Logistic Costs');
INSERT INTO `t_sys_cost_center` VALUES ('3', 'CN3372', 'Regulatory', 'Regulatory');
INSERT INTO `t_sys_cost_center` VALUES ('4', 'CN3425', 'Sales Domestic', 'Sales Domestic');
INSERT INTO `t_sys_cost_center` VALUES ('5', 'CN3435', 'MKT Domestic', 'MKT Domestic');
INSERT INTO `t_sys_cost_center` VALUES ('6', 'CN3500', 'G&A', 'G&A');
INSERT INTO `t_sys_cost_center` VALUES ('7', 'CN3440', 'Sales Export', 'Sales Export');
INSERT INTO `t_sys_cost_center` VALUES ('8', 'CN3445', 'MKT Export', 'MKT Export');
INSERT INTO `t_sys_cost_center` VALUES ('9', 'CN3514', 'MKT Great China', 'MKT Great China');
INSERT INTO `t_sys_cost_center` VALUES ('10', 'CN3614', 'G&A', 'G&A');
INSERT INTO `t_sys_cost_center` VALUES ('11', 'CN3714', 'G&A', 'G&A');
INSERT INTO `t_sys_cost_center` VALUES ('12', 'CN3814', 'G&A', 'G&A');
INSERT INTO `t_sys_cost_center` VALUES ('13', 'CN3914', 'G&A', 'G&A');

-- ----------------------------
-- Table structure for `t_sys_cost_center_his`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_cost_center_his`;
CREATE TABLE `t_sys_cost_center_his` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `costCenterCode` varchar(10) NOT NULL,
  `costCenterName` varchar(100) NOT NULL,
  `costCenterDisplayName` varchar(100) NOT NULL,
  `operateFlg` char(1) DEFAULT NULL,
  `operater` varchar(40) DEFAULT NULL,
  `operateTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_cost_center_his
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_employee_authority`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_employee_authority`;
CREATE TABLE `t_sys_employee_authority` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `deptCode` varchar(10) NOT NULL,
  `roleCode` varchar(10) NOT NULL,
  `controlId` varchar(48) NOT NULL,
  `authority` char(1) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_employee_authority
-- ----------------------------
INSERT INTO `t_sys_employee_authority` VALUES ('1', 'CN3500', 'R010', 'SE_LG_MN_el', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('2', 'CN3500', 'R010', 'SE_LG_MN_al', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('3', 'CN3500', 'R010', 'SE_LG_MN_re_sap', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('4', 'CN3500', 'R010', 'SE_LG_MN_re_in', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('5', 'CN3500', 'R010', 'SE_LG_MN_ul', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('6', 'CN3500', 'R010', 'SE_LG_MN_rl', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('7', 'CN3500', 'R010', 'SE_LG_MN_al', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('8', 'CN3500', 'R010', 'SE_LG_MN_es', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('9', 'CN3500', 'R010', 'SE_LG_MN_ps', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('10', 'CN3500', 'R010', 'SE_LG_MN_ed', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('11', 'CN3500', 'R010', 'SE_EC_EP_LS_Search', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('12', 'CN3500', 'R010', 'SE_EC_EP_LS_NewClaim', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('13', 'CN3500', 'R010', 'SE_EC_EP_CA_AddDetails', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('14', 'CN3500', 'R010', 'SE_EC_EP_CA_ModifyDetails', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('15', 'CN3500', 'R010', 'SE_EC_EP_CA_DeleteDetails', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('16', 'CN3500', 'R010', 'SE_EC_EP_CA_Review', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('17', 'CN3500', 'R010', 'SE_EC_EP_CA_Submit', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('18', 'CN3500', 'R010', 'SE_EC_EP_CA_Save', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('19', 'CN3500', 'R010', 'SE_EC_EP_DE_AddItem', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('20', 'CN3500', 'R010', 'SE_EC_EP_DE_DeleteItem', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('21', 'CN3500', 'R010', 'SE_EC_EP_DE_Finish', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('22', 'CN3500', 'R010', 'SE_AC_AP_LS_Search', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('23', 'CN3500', 'R010', 'SE_AC_AP_LS_Details', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('24', 'CN3500', 'R010', 'SE_AC_AP_LS_Approve', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('25', 'CN3500', 'R010', 'SE_AC_AP_LS_Reject', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('26', 'CN3500', 'R010', 'SE_AC_AP_DE_Approve', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('27', 'CN3500', 'R010', 'SE_AC_AP_DE_Reject', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('28', 'CN3500', 'R010', 'SE_AC_AP_DE_Export', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('29', 'CN3500', 'R010', 'SE_RC_RE_LS_Search', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('30', 'CN3500', 'R010', 'SE_RC_RE_LS_Export', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('31', 'CN3500', 'R010', 'SE_RC_RE_LS_Details', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('32', 'CN3500', 'R010', 'SE_RC_RE_MO_Search', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('33', 'CN3500', 'R010', 'SE_RC_RE_MO_Export', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('34', 'CN3500', 'R010', 'SE_EM_PE_LS_Search', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('35', 'CN3500', 'R010', 'SE_EM_PE_LS_Add', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('36', 'CN3500', 'R010', 'SE_EM_PE_LS_Modify', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('37', 'CN3500', 'R010', 'SE_EM_PE_LS_Delete', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('38', 'CN3500', 'R010', 'SE_EM_PE_LS_ChangePw', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('39', 'CN3500', 'R010', 'SE_AM_RO_LS_Add', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('40', 'CN3500', 'R010', 'SE_AM_RO_LS_Modify', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('41', 'CN3500', 'R010', 'SE_AM_RO_LS_Delete', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('42', 'CN3500', 'R010', 'SE_SY_AM_AL_Add', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('43', 'CN3500', 'R010', 'SE_SY_AM_AL_Details', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('44', 'CN3500', 'R010', 'SE_SM_EP_MA_Save', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('45', 'CN3500', 'R010', 'SE_SM_PA_DE_LevelSave', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('46', 'CN3500', 'R010', 'SE_SM_PA_DE_ExpenseSave', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('47', 'CN3500', 'R010', 'SE_SM_PA_DE_CostCenterSave', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('48', 'CN3500', 'R010', 'SE_AC_AP_LS_Finish', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('49', 'CN3500', 'R010', 'SE_AC_AP_LS_UndoFinish', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('50', 'CN3500', 'R010', 'SE_AC_AP_DE_Finish', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('51', 'CN3500', 'R010', 'SE_AC_AP_DE_UndoFinish', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('52', 'CN3500', 'R010', 'SE_RC_RE_IN_Search', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('53', 'CN3500', 'R010', 'SE_RC_RE_IN_Export', '1');
INSERT INTO `t_sys_employee_authority` VALUES ('54', 'CN3500', 'R010', 'SE_RC_RE_IN_Details', '1');

-- ----------------------------
-- Table structure for `t_sys_employee_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_employee_level`;
CREATE TABLE `t_sys_employee_level` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `levelCode` varchar(4) NOT NULL,
  `levelName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_employee_level
-- ----------------------------
INSERT INTO `t_sys_employee_level` VALUES ('1', 'L001', 'Manager');
INSERT INTO `t_sys_employee_level` VALUES ('2', 'L002', 'Non-Manager');
INSERT INTO `t_sys_employee_level` VALUES ('3', 'L003', 'Level Three');
INSERT INTO `t_sys_employee_level` VALUES ('4', 'L004', 'Level Four');
INSERT INTO `t_sys_employee_level` VALUES ('5', 'L005', 'Level Five');

-- ----------------------------
-- Table structure for `t_sys_employee_level_his`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_employee_level_his`;
CREATE TABLE `t_sys_employee_level_his` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `levelCode` varchar(4) NOT NULL,
  `levelName` varchar(100) NOT NULL,
  `operateFlg` char(1) DEFAULT NULL,
  `operater` varchar(40) DEFAULT NULL,
  `operateTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_employee_level_his
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_employee_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_employee_role`;
CREATE TABLE `t_sys_employee_role` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `roleCode` varchar(4) NOT NULL,
  `roleName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_employee_role
-- ----------------------------
INSERT INTO `t_sys_employee_role` VALUES ('1', 'R001', 'VP');
INSERT INTO `t_sys_employee_role` VALUES ('2', 'R002', 'Country Manager');
INSERT INTO `t_sys_employee_role` VALUES ('3', 'R003', 'Director');
INSERT INTO `t_sys_employee_role` VALUES ('4', 'R004', 'Manager');
INSERT INTO `t_sys_employee_role` VALUES ('5', 'R005', 'Accountant');
INSERT INTO `t_sys_employee_role` VALUES ('6', 'R006', 'GM');
INSERT INTO `t_sys_employee_role` VALUES ('7', 'R007', 'Specialist');
INSERT INTO `t_sys_employee_role` VALUES ('8', 'R008', 'Supervisor');
INSERT INTO `t_sys_employee_role` VALUES ('9', 'R009', 'Board Member');
INSERT INTO `t_sys_employee_role` VALUES ('10', 'R010', 'System Manager');

-- ----------------------------
-- Table structure for `t_sys_employee_role_his`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_employee_role_his`;
CREATE TABLE `t_sys_employee_role_his` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `roleCode` varchar(4) NOT NULL,
  `roleName` varchar(100) NOT NULL,
  `operateFlg` char(1) DEFAULT NULL,
  `operater` varchar(40) DEFAULT NULL,
  `operateTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_employee_role_his
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_expenses`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_expenses`;
CREATE TABLE `t_sys_expenses` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expenseCode` varchar(4) NOT NULL,
  `expenseName` varchar(100) NOT NULL,
  `fatherExpenseCode` varchar(4) NOT NULL,
  `timeMethod` varchar(6) DEFAULT NULL,
  `computeMethod` varchar(6) DEFAULT NULL,
  `extendsFieldNm1` varchar(50) DEFAULT NULL,
  `extendsFieldCo1` varchar(50) DEFAULT NULL,
  `extendsFieldNm2` varchar(50) DEFAULT NULL,
  `extendsFieldCo2` varchar(50) DEFAULT NULL,
  `extendsFieldNm3` varchar(50) DEFAULT NULL,
  `extendsFieldCo3` varchar(50) DEFAULT NULL,
  `financeNo` varchar(10) DEFAULT NULL,
  `showOrderNo` int(3) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_expenses
-- ----------------------------
INSERT INTO `t_sys_expenses` VALUES ('1', 'S001', 'Taxi', 'S000', '1', '1', '', '', '', '', '', '', '593003', '10');
INSERT INTO `t_sys_expenses` VALUES ('2', 'S002', 'Internet', 'S000', '2', '1', '', '', '', '', '', '', '605003', '15');
INSERT INTO `t_sys_expenses` VALUES ('3', 'S003', 'Hotel', 'S000', '1', '1', '', '', '', '', '', '', '593014', '20');
INSERT INTO `t_sys_expenses` VALUES ('4', 'S005', 'Accomodation in hotel', 'S003', '2', '1', '', '', '', '', '', '', '', '30');
INSERT INTO `t_sys_expenses` VALUES ('5', 'S006', 'Other expense in hotel', 'S003', '1', '1', '', '', '', '', '', '', '', '35');
INSERT INTO `t_sys_expenses` VALUES ('6', 'S007', 'Meal', 'S000', '1', '1', '', '', '', '', '', '', '590207', '40');
INSERT INTO `t_sys_expenses` VALUES ('7', 'S008', 'Meal in biz trip', 'S007', '1', '1', '', '', '', '', '', '', '', '45');
INSERT INTO `t_sys_expenses` VALUES ('8', 'S009', 'OT meal', 'S007', '1', '1', '', '', '', '', '', '', '', '50');
INSERT INTO `t_sys_expenses` VALUES ('9', 'S010', 'Postage', 'S000', '1', '1', '', '', '', '', '', '', '605001', '55');
INSERT INTO `t_sys_expenses` VALUES ('10', 'S011', 'Entertainment', 'S000', '1', '1', '', '', '', '', '', '', '590005', '60');
INSERT INTO `t_sys_expenses` VALUES ('11', 'S012', 'Office supply', 'S000', '1', '1', '', '', '', '', '', '', '632000', '65');
INSERT INTO `t_sys_expenses` VALUES ('12', 'S013', 'Other', 'S000', '1', '1', '', '', '', '', '', '', '555010', '70');
INSERT INTO `t_sys_expenses` VALUES ('13', 'S014', 'Board allowance', 'S000', '3', '1', '', '', '', '', '', '', '593005', '75');
INSERT INTO `t_sys_expenses` VALUES ('14', 'S015', 'Car rental', 'S000', '1', '1', '', '', '', '', '', '', '593002', '80');
INSERT INTO `t_sys_expenses` VALUES ('15', 'S016', 'Car allowance', 'S000', '3', '1', '', '', '', '', '', '', '593200', '85');
INSERT INTO `t_sys_expenses` VALUES ('16', 'S017', 'Mobile', 'S000', '3', '1', '', '', '', '', '', '', '605005', '90');
INSERT INTO `t_sys_expenses` VALUES ('17', 'S018', 'House rental', 'S000', '3', '1', '', '', '', '', '', '', '548200', '95');
INSERT INTO `t_sys_expenses` VALUES ('18', 'S019', 'Meeting related', 'S000', '1', '1', '', '', '', '', '', '', '578000', '100');
INSERT INTO `t_sys_expenses` VALUES ('19', 'S020', 'Electricity', 'S000', '1', '1', '', '', '', '', '', '', '512000', '105');
INSERT INTO `t_sys_expenses` VALUES ('20', 'S021', 'Clinical testing', 'S000', '1', '1', '', '', '', '', '', '', '575000', '110');
INSERT INTO `t_sys_expenses` VALUES ('21', 'S022', 'Office phone', 'S000', '1', '1', '', '', '', '', '', '', '605000', '115');
INSERT INTO `t_sys_expenses` VALUES ('22', 'S023', 'Travel Expenses-Guest', 'S000', '1', '1', '', '', '', '', '', '', '590000', '120');
INSERT INTO `t_sys_expenses` VALUES ('23', 'S024', 'Air Tickets', 'S000', '1', '1', '', '', '', '', '', '', '593015', '125');
INSERT INTO `t_sys_expenses` VALUES ('24', 'S025', 'Private car for business', 'S000', '1', '21', 'Car rate', '20', '', '', '', '', '', '130');

-- ----------------------------
-- Table structure for `t_sys_expenses_his`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_expenses_his`;
CREATE TABLE `t_sys_expenses_his` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expenseCode` varchar(4) NOT NULL,
  `expenseName` varchar(100) NOT NULL,
  `fatherExpenseCode` varchar(4) NOT NULL,
  `timeMethod` varchar(6) DEFAULT NULL,
  `computeMethod` varchar(6) DEFAULT NULL,
  `extendsFieldNm1` varchar(50) DEFAULT NULL,
  `extendsFieldCo1` varchar(50) DEFAULT NULL,
  `extendsFieldNm2` varchar(50) DEFAULT NULL,
  `extendsFieldCo2` varchar(50) DEFAULT NULL,
  `extendsFieldNm3` varchar(50) DEFAULT NULL,
  `extendsFieldCo3` varchar(50) DEFAULT NULL,
  `financeNo` varchar(10) DEFAULT NULL,
  `showOrderNo` int(3) DEFAULT NULL,
  `operateFlg` char(1) DEFAULT NULL,
  `operater` varchar(40) DEFAULT NULL,
  `operateTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_expenses_his
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_module_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_module_info`;
CREATE TABLE `t_sys_module_info` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `controlId` varchar(48) NOT NULL,
  `fatherControlId` varchar(48) DEFAULT NULL,
  `controlDivision` char(1) NOT NULL,
  `controlName` varchar(100) NOT NULL,
  `methodId` varchar(48) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_module_info
-- ----------------------------
INSERT INTO `t_sys_module_info` VALUES ('1', 'SE_LG_MN_el', 'System', '2', 'Expenses_ExpensesList', 'SE_LG_MN_el');
INSERT INTO `t_sys_module_info` VALUES ('2', 'SE_LG_MN_al', 'System', '2', 'Approval_ApprovalList', 'SE_LG_MN_al');
INSERT INTO `t_sys_module_info` VALUES ('3', 'SE_LG_MN_re_sap', 'System', '2', 'Reports_SAP', 'SE_LG_MN_re_sap');
INSERT INTO `t_sys_module_info` VALUES ('4', 'SE_LG_MN_re_in', 'System', '2', 'Reports_Summary', 'SE_LG_MN_re_in');
INSERT INTO `t_sys_module_info` VALUES ('5', 'SE_LG_MN_ul', 'System', '2', 'User_UserList', 'SE_LG_MN_ul');
INSERT INTO `t_sys_module_info` VALUES ('6', 'SE_LG_MN_rl', 'System', '2', 'Role_RoleList', 'SE_LG_MN_rl');
INSERT INTO `t_sys_module_info` VALUES ('7', 'SE_LG_MN_al', 'System', '2', 'Authority_AuthorityList', 'SE_LG_MN_al');
INSERT INTO `t_sys_module_info` VALUES ('8', 'SE_LG_MN_es', 'System', '2', 'System_ExpensesSettings', 'SE_LG_MN_es');
INSERT INTO `t_sys_module_info` VALUES ('9', 'SE_LG_MN_ps', 'System', '2', 'System_SystemSettings', 'SE_LG_MN_ps');
INSERT INTO `t_sys_module_info` VALUES ('10', 'SE_LG_MN_ed', 'System', '2', 'Menu_EmployeeInfoEdit', 'SE_LG_MN_ed');
INSERT INTO `t_sys_module_info` VALUES ('11', 'SE_EC_EP_LS_Search', 'System', '2', 'ExpensesList_Search', 'SE_EC_EP_LS_Search');
INSERT INTO `t_sys_module_info` VALUES ('12', 'SE_EC_EP_LS_NewClaim', 'System', '2', 'ExpensesList_NewClaim', 'SE_EC_EP_LS_NewClaim');
INSERT INTO `t_sys_module_info` VALUES ('13', 'SE_EC_EP_CA_AddDetails', 'System', '2', 'ExpenseClaim_AddDetails', 'SE_EC_EP_CA_AddDetails');
INSERT INTO `t_sys_module_info` VALUES ('14', 'SE_EC_EP_CA_ModifyDetails', 'System', '2', 'ExpenseClaim_ModifyDetails', 'SE_EC_EP_CA_ModifyDetails');
INSERT INTO `t_sys_module_info` VALUES ('15', 'SE_EC_EP_CA_DeleteDetails', 'System', '2', 'ExpenseClaim_DeleteDetails', 'SE_EC_EP_CA_DeleteDetails');
INSERT INTO `t_sys_module_info` VALUES ('16', 'SE_EC_EP_CA_Review', 'System', '2', 'ExpenseClaim_Review', 'SE_EC_EP_CA_Review');
INSERT INTO `t_sys_module_info` VALUES ('17', 'SE_EC_EP_CA_Submit', 'System', '2', 'ExpenseClaim_Submit', 'SE_EC_EP_CA_Submit');
INSERT INTO `t_sys_module_info` VALUES ('18', 'SE_EC_EP_CA_Save', 'System', '2', 'ExpenseClaim_Save', 'SE_EC_EP_CA_Save');
INSERT INTO `t_sys_module_info` VALUES ('19', 'SE_EC_EP_DE_AddItem', 'System', '2', 'ExpenseDetails_AddItem', 'SE_EC_EP_DE_AddItem');
INSERT INTO `t_sys_module_info` VALUES ('20', 'SE_EC_EP_DE_DeleteItem', 'System', '2', 'ExpenseDetails_DeleteItem', 'SE_EC_EP_DE_DeleteItem');
INSERT INTO `t_sys_module_info` VALUES ('21', 'SE_EC_EP_DE_Finish', 'System', '2', 'ExpenseDetails_Finish', 'SE_EC_EP_DE_Finish');
INSERT INTO `t_sys_module_info` VALUES ('22', 'SE_AC_AP_LS_Search', 'System', '2', 'ApprovalList_Search', 'SE_AC_AP_LS_Search');
INSERT INTO `t_sys_module_info` VALUES ('23', 'SE_AC_AP_LS_Details', 'System', '2', 'ApprovalList_Details', 'SE_AC_AP_LS_Details');
INSERT INTO `t_sys_module_info` VALUES ('24', 'SE_AC_AP_LS_Approve', 'System', '2', 'ApprovalList_Approve', 'SE_AC_AP_LS_Approve');
INSERT INTO `t_sys_module_info` VALUES ('25', 'SE_AC_AP_LS_Reject', 'System', '2', 'ApprovalList_Reject', 'SE_AC_AP_LS_Reject');
INSERT INTO `t_sys_module_info` VALUES ('26', 'SE_AC_AP_DE_Approve', 'System', '2', 'ExpenseApproval_Approve', 'SE_AC_AP_DE_Approve');
INSERT INTO `t_sys_module_info` VALUES ('27', 'SE_AC_AP_DE_Reject', 'System', '2', 'ExpenseApproval_Reject', 'SE_AC_AP_DE_Reject');
INSERT INTO `t_sys_module_info` VALUES ('28', 'SE_AC_AP_DE_Export', 'System', '2', 'ExpenseApproval_Export', 'SE_AC_AP_DE_Export');
INSERT INTO `t_sys_module_info` VALUES ('29', 'SE_RC_RE_LS_Search', 'System', '2', 'ReportsSummary_Search', 'SE_RC_RE_LS_Search');
INSERT INTO `t_sys_module_info` VALUES ('30', 'SE_RC_RE_LS_Export', 'System', '2', 'ReportsSummary_Export', 'SE_RC_RE_LS_Export');
INSERT INTO `t_sys_module_info` VALUES ('31', 'SE_RC_RE_LS_Details', 'System', '2', 'ReportsSummary_Details', 'SE_RC_RE_LS_Details');
INSERT INTO `t_sys_module_info` VALUES ('32', 'SE_RC_RE_MO_Search', 'System', '2', 'ReportsSAP_Search', 'SE_RC_RE_MO_Search');
INSERT INTO `t_sys_module_info` VALUES ('33', 'SE_RC_RE_MO_Export', 'System', '2', 'ReportsSAP_Export', 'SE_RC_RE_MO_Export');
INSERT INTO `t_sys_module_info` VALUES ('34', 'SE_EM_PE_LS_Search', 'System', '2', 'UserList_Search', 'SE_EM_PE_LS_Search');
INSERT INTO `t_sys_module_info` VALUES ('35', 'SE_EM_PE_LS_Add', 'System', '2', 'UserList_Add', 'SE_EM_PE_LS_Add');
INSERT INTO `t_sys_module_info` VALUES ('36', 'SE_EM_PE_LS_Modify', 'System', '2', 'UserList_Modify', 'SE_EM_PE_LS_Modify');
INSERT INTO `t_sys_module_info` VALUES ('37', 'SE_EM_PE_LS_Delete', 'System', '2', 'UserList_Delete', 'SE_EM_PE_LS_Delete');
INSERT INTO `t_sys_module_info` VALUES ('38', 'SE_EM_PE_LS_ChangePw', 'System', '2', 'UserList_ChangePw', 'SE_EM_PE_LS_ChangePw');
INSERT INTO `t_sys_module_info` VALUES ('39', 'SE_AM_RO_LS_Add', 'System', '2', 'RoleList_Add', 'SE_AM_RO_LS_Add');
INSERT INTO `t_sys_module_info` VALUES ('40', 'SE_AM_RO_LS_Modify', 'System', '2', 'RoleList_Modify', 'SE_AM_RO_LS_Modify');
INSERT INTO `t_sys_module_info` VALUES ('41', 'SE_AM_RO_LS_Delete', 'System', '2', 'RoleList_Delete', 'SE_AM_RO_LS_Delete');
INSERT INTO `t_sys_module_info` VALUES ('42', 'SE_SY_AM_AL_Add', 'System', '2', 'AuthorityList_Add', 'SE_SY_AM_AL_Add');
INSERT INTO `t_sys_module_info` VALUES ('43', 'SE_SY_AM_AL_Details', 'System', '2', 'AuthorityList_Details', 'SE_SY_AM_AL_Details');
INSERT INTO `t_sys_module_info` VALUES ('44', 'SE_SM_EP_MA_Save', 'System', '2', 'ExpensesSettings_Save', 'SE_SM_EP_MA_Save');
INSERT INTO `t_sys_module_info` VALUES ('45', 'SE_SM_PA_DE_LevelSave', 'System', '2', 'SystemSettings_LevelSave', 'SE_SM_PA_DE_LevelSave');
INSERT INTO `t_sys_module_info` VALUES ('46', 'SE_SM_PA_DE_ExpenseSave', 'System', '2', 'SystemSettings_ExpenseSave', 'SE_SM_PA_DE_ExpenseSave');
INSERT INTO `t_sys_module_info` VALUES ('47', 'SE_SM_PA_DE_CostCenterSave', 'System', '2', 'SystemSettings_CostCenterSave', 'SE_SM_PA_DE_CostCenterSave');
INSERT INTO `t_sys_module_info` VALUES ('48', 'SE_AC_AP_LS_Finish', 'System', '2', 'ApprovalList_Finish', 'SE_AC_AP_LS_Finish');
INSERT INTO `t_sys_module_info` VALUES ('49', 'SE_AC_AP_LS_UndoFinish', 'System', '2', 'ApprovalList_UndoFinish', 'SE_AC_AP_LS_UndoFinish');
INSERT INTO `t_sys_module_info` VALUES ('50', 'SE_AC_AP_DE_Finish', 'System', '2', 'ExpenseApproval_Finish', 'SE_AC_AP_DE_Finish');
INSERT INTO `t_sys_module_info` VALUES ('51', 'SE_AC_AP_DE_UndoFinish', 'System', '2', 'ExpenseApproval_UndoFinish', 'SE_AC_AP_DE__UndoFinish');
INSERT INTO `t_sys_module_info` VALUES ('52', 'SE_RC_RE_IN_Search', 'System', '2', 'ReportsSummary_Search', 'SE_RC_RE_IN_Search');
INSERT INTO `t_sys_module_info` VALUES ('53', 'SE_RC_RE_IN_Export', 'System', '2', 'ReportsSummary_Export', 'SE_RC_RE_IN_Export');
INSERT INTO `t_sys_module_info` VALUES ('54', 'SE_RC_RE_IN_Details', 'System', '2', 'ReportsSummary_Details', 'SE_RC_RE_IN_Details');

-- ----------------------------
-- Table structure for `t_sys_travel_local`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_travel_local`;
CREATE TABLE `t_sys_travel_local` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `travelLocalType` varchar(6) NOT NULL,
  `travelCode` varchar(4) NOT NULL,
  `travelName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_travel_local
-- ----------------------------

-- ----------------------------
-- Table structure for `t_workflow_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_workflow_category`;
CREATE TABLE `t_workflow_category` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `categoryId` varchar(10) NOT NULL,
  `categoryName` varchar(256) NOT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_workflow_category
-- ----------------------------
INSERT INTO `t_workflow_category` VALUES ('1', 'WFCG01', 'Finance', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');

-- ----------------------------
-- Table structure for `t_workflow_defination`
-- ----------------------------
DROP TABLE IF EXISTS `t_workflow_defination`;
CREATE TABLE `t_workflow_defination` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `workflowId` varchar(10) NOT NULL,
  `workflowName` varchar(256) NOT NULL,
  `workflowCategory` varchar(10) NOT NULL,
  `workflowType` varchar(6) NOT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_workflow_defination
-- ----------------------------
INSERT INTO `t_workflow_defination` VALUES ('1', 'WF01', 'Expenses Approval ', 'WFCG01', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');

-- ----------------------------
-- Table structure for `t_workflow_node_defination`
-- ----------------------------
DROP TABLE IF EXISTS `t_workflow_node_defination`;
CREATE TABLE `t_workflow_node_defination` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `nodeId` varchar(10) NOT NULL,
  `nodeName` varchar(256) NOT NULL,
  `nodeWorkflow` varchar(10) NOT NULL,
  `upNodeId` varchar(10) NOT NULL,
  `ifAllApprove` char(1) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_workflow_node_defination
-- ----------------------------
INSERT INTO `t_workflow_node_defination` VALUES ('1', 'WFND01', 'Other CC Manager Approve', 'WF01', 'None', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_workflow_node_defination` VALUES ('2', 'WFND02', 'Self CC Manager Approve', 'WF01', 'WFND01', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_workflow_node_defination` VALUES ('3', 'WFND03', 'China Area Manager Approve', 'WF01', 'WFND02', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_workflow_node_defination` VALUES ('4', 'WFND04', 'VP Approve', 'WF01', 'WFND03', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
INSERT INTO `t_workflow_node_defination` VALUES ('5', 'WFND05', 'Finance Approve', 'WF01', 'WFND04', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');

-- ----------------------------
-- Table structure for `t_workflow_progress`
-- ----------------------------
DROP TABLE IF EXISTS `t_workflow_progress`;
CREATE TABLE `t_workflow_progress` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `nodeId` varchar(10) NOT NULL,
  `employeeNo` varchar(10) DEFAULT NULL,
  `progressStatus` varchar(6) NOT NULL,
  `operTimestamp` timestamp NULL DEFAULT NULL,
  `operComments` varchar(500) DEFAULT NULL,
  `businessId` varchar(20) DEFAULT NULL,
  `businessCategory` varchar(6) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_workflow_progress
-- ----------------------------
