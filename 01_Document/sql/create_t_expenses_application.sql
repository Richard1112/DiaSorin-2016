DROP TABLE IF EXISTS `t_expenses_application`;
CREATE TABLE `t_expenses_application` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expensesAppNo` varchar(20) NOT NULL,
  `employeeNo` varchar(10) NOT NULL,
  `applicationDate` varchar(8) NOT NULL,
  `costCenterCode` varchar(10) NOT NULL,
  `travelLocalType` varchar(4) NOT NULL,
  `travelReason` varchar(100) DEFAULT NULL,
  `travelDateStart` varchar(8) DEFAULT NULL,
  `travelDateEnd` varchar(8) DEFAULT NULL,
  `travelAppNo` varchar(16) DEFAULT NULL,
  `expenseSum` decimal(12, 2),
  `status` varchar(6) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
