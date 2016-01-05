DROP TABLE IF EXISTS `t_sys_employee_role_his`;
CREATE TABLE `t_sys_employee_role_his` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `roleCode` varchar(4) NOT NULL,
  `roleName` varchar(100) NOT NULL,
  `operateFlg` char(1),
  `operater` varchar(40) DEFAULT NULL,
  `operateTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
