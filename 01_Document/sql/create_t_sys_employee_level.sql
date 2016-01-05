DROP TABLE IF EXISTS `t_sys_employee_level`;
CREATE TABLE `t_sys_employee_level` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `levelCode` varchar(4) NOT NULL,
  `levelName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
