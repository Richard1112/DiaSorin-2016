DROP TABLE IF EXISTS `t_expenses_parameter`;
CREATE TABLE `t_expenses_parameter` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `travelLocal` varchar(6) NOT NULL,
  `expenseCode` varchar(4) NOT NULL,
  `employeeLevelCode` varchar(4) NOT NULL,
  `allowExpensesUp` decimal(12, 2) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
