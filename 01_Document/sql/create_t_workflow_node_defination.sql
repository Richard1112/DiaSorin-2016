DROP TABLE IF EXISTS `t_workflow_node_defination`;
CREATE TABLE `t_workflow_node_defination` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `nodeId` varchar(10) NOT NULL,
  `nodeName` varchar(256) NOT NULL,
  `nodeWorkflow` varchar(10) NOT NULL,
  `upNodeId` varchar(10) NOT NULL,
  `ifAllApprove` char(1),
  `deleteFlg` char(1) NOT NULL,
  `addTimestamp` timestamp NULL DEFAULT NULL,
  `addUserKey` varchar(40) DEFAULT NULL,
  `updTimestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updUserKey` varchar(40) DEFAULT NULL,
  `updPgmId` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
