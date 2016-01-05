DROP TABLE IF EXISTS `t_sys_cost_center_his`;
CREATE TABLE `t_sys_cost_center_his` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `costCenterCode` varchar(10) NOT NULL,
  `costCenterName` varchar(100) NOT NULL,
  `costCenterDisplayName` varchar(100) NOT NULL,
  `operateFlg` char(1),
  `operater` varchar(40) DEFAULT NULL,
  `operateTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
