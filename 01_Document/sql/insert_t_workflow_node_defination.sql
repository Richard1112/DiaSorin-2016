﻿insert into t_workflow_node_defination(nodeId, nodeName, nodeWorkflow, upNodeId, ifAllApprove, deleteFlg, addTimestamp, addUserKey, updTimestamp, updUserKey, updPgmId) values('WFND01', 'Other CC Manager Approve', 'WF01', 'None', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
insert into t_workflow_node_defination(nodeId, nodeName, nodeWorkflow, upNodeId, ifAllApprove, deleteFlg, addTimestamp, addUserKey, updTimestamp, updUserKey, updPgmId) values('WFND02', 'Self CC Manager Approve', 'WF01', 'WFND01', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
insert into t_workflow_node_defination(nodeId, nodeName, nodeWorkflow, upNodeId, ifAllApprove, deleteFlg, addTimestamp, addUserKey, updTimestamp, updUserKey, updPgmId) values('WFND03', 'China Area Manager Approve', 'WF01', 'WFND02', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
insert into t_workflow_node_defination(nodeId, nodeName, nodeWorkflow, upNodeId, ifAllApprove, deleteFlg, addTimestamp, addUserKey, updTimestamp, updUserKey, updPgmId) values('WFND04', 'VP Approve', 'WF01', 'WFND03', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');
insert into t_workflow_node_defination(nodeId, nodeName, nodeWorkflow, upNodeId, ifAllApprove, deleteFlg, addTimestamp, addUserKey, updTimestamp, updUserKey, updPgmId) values('WFND05', 'Finance Approve', 'WF01', 'WFND04', '0', '0', '2015-04-01 00:00:00', 'diasorin', '2015-04-01 00:00:00', 'diasorin', 'basicData');