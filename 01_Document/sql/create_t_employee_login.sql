DROP TABLE IF EXISTS `t_employee_login`;
CREATE TABLE `t_employee_login` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `employeeNo` varchar(10) NOT NULL,
  `employeePassword` varchar(48) NOT NULL,
  `loginStatus` char(1) NOT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
