DROP TABLE IF EXISTS `t_sys_employee_level_his`;
CREATE TABLE `t_sys_employee_level_his` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `levelCode` varchar(4) NOT NULL,
  `levelName` varchar(100) NOT NULL,
  `operateFlg` char(1),
  `operater` varchar(40) DEFAULT NULL,
  `operateTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
