DROP TABLE IF EXISTS `t_expenses_approve_rules`;
CREATE TABLE `t_expenses_approve_rules` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `ruleId` varchar(10) NOT NULL,
  `nodeId` varchar(10) NOT NULL,
  `approveRoleCode` varchar(10) NOT NULL,
  `approveCondition` varchar(10) DEFAULT NULL,
  `approveExpenseType` varchar(4) DEFAULT NULL,
  `approveAmount` varchar(10) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
