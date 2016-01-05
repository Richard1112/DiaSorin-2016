DROP TABLE IF EXISTS `t_expenses_details`;
CREATE TABLE `t_expenses_details` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expensesDetailsNo` varchar(30) NOT NULL,
  `belongExpensesAppNo` varchar(25) NOT NULL,
  `expensesDateType` varchar(6) NOT NULL,
  `expensesDate` varchar(8) DEFAULT NULL,
  `expensesDateEnd` varchar(8) DEFAULT NULL,
  `travelLocation` varchar(100) DEFAULT NULL,
  `expensesItem` varchar(4) DEFAULT NULL,
  `kilometers` decimal(6, 2),
  `expensesAmount` decimal(12, 2),
  `expensesComments` varchar(100) DEFAULT NULL,
  `rejectErrorFlg` char(1) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
