DROP TABLE IF EXISTS `t_employee_login_history`;
CREATE TABLE `t_employee_login_history` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `employeeNo` varchar(10) NOT NULL,
  `loginTimestamp` timestamp NULL DEFAULT NULL,
  `logoutTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
