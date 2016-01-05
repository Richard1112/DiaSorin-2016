DROP TABLE IF EXISTS `t_sys_travel_local`;
CREATE TABLE `t_sys_travel_local` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `travelLocalType` varchar(6) NOT NULL,
  `travelCode` varchar(4) NOT NULL,
  `travelName` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
