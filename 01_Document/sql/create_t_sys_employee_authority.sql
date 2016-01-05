DROP TABLE IF EXISTS `t_sys_employee_authority`;
CREATE TABLE `t_sys_employee_authority` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `deptCode` varchar(10) NOT NULL,
  `roleCode` varchar(10) NOT NULL,
  `controlId` varchar(48) NOT NULL,
  `authority` char(1) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
