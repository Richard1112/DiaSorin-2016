DROP TABLE IF EXISTS `t_workflow_defination`;
CREATE TABLE `t_workflow_defination` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `workflowId` varchar(10) NOT NULL,
  `workflowName` varchar(256) NOT NULL,
  `workflowCategory` varchar(10) NOT NULL,
  `workflowType` varchar(6) NOT NULL,
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
