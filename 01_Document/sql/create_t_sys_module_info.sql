DROP TABLE IF EXISTS `t_sys_module_info`;
CREATE TABLE `t_sys_module_info` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `controlId` varchar(48) NOT NULL,
  `fatherControlId` varchar(48) DEFAULT NULL,
  `controlDivision` char(1) NOT NULL,
  `controlName` varchar(100) NOT NULL,
  `methodId` varchar(48) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
