DROP TABLE IF EXISTS `t_no_expenses_app`;
CREATE TABLE `t_no_expenses_app` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `currentYear` varchar(8) NOT NULL,
  `maxAppNumber` varchar(12) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
