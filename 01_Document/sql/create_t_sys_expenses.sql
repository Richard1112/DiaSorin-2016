﻿DROP TABLE IF EXISTS `t_sys_expenses`;
CREATE TABLE `t_sys_expenses` (
  `no` bigint(12) NOT NULL AUTO_INCREMENT,
  `expenseCode` varchar(4) NOT NULL,
  `expenseName` varchar(100) NOT NULL,
  `fatherExpenseCode` varchar(4) NOT NULL,
  `timeMethod` varchar(6) DEFAULT NULL,
  `computeMethod` varchar(6) DEFAULT NULL,
  `extendsFieldNm1` varchar(50) DEFAULT NULL,
  `extendsFieldCo1` varchar(50) DEFAULT NULL,
  `extendsFieldNm2` varchar(50) DEFAULT NULL,
  `extendsFieldCo2` varchar(50) DEFAULT NULL,
  `extendsFieldNm3` varchar(50) DEFAULT NULL,
  `extendsFieldCo3` varchar(50) DEFAULT NULL,
  `financeNo` varchar(10) DEFAULT NULL,
  `showOrderNo` int(3),
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
