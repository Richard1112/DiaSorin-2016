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
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
