DROP TABLE IF EXISTS `t_sys_employee_role`;
CREATE TABLE `t_sys_employee_role` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `roleCode` varchar(4) NOT NULL,
  `roleName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
