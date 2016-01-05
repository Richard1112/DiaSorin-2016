DROP TABLE IF EXISTS `t_sys_cost_center`;
CREATE TABLE `t_sys_cost_center` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `costCenterCode` varchar(10) NOT NULL,
  `costCenterName` varchar(100) NOT NULL,
  `costCenterDisplayName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
