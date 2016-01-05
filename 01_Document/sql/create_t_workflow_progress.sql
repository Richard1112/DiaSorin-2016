DROP TABLE IF EXISTS `t_workflow_progress`;
CREATE TABLE `t_workflow_progress` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `nodeId` varchar(10) NOT NULL,
  `employeeNo` varchar(10) DEFAULT NULL,
  `progressStatus` varchar(6) NOT NULL,
  `operTimestamp` timestamp NULL DEFAULT NULL,
  `operComments` varchar(500) DEFAULT NULL,
  `businessId` varchar(20) DEFAULT NULL,
  `businessCategory` varchar(6) DEFAULT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
