DROP TABLE IF EXISTS `t_expenses_purpose_sum`;
CREATE TABLE `t_expenses_purpose_sum` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expensesDetailsNo` varchar(25) NOT NULL,
  `belongExpensesAppNo` varchar(20) NOT NULL,
  `expensesPurpose` varchar(100) DEFAULT NULL,
  `expensesAmount` decimal(12, 2),
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
