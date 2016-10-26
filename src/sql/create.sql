# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 121.199.28.209 (MySQL 5.6.30-log)
# Database: pock
# Generation Time: 2016-10-26 06:49:57 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table BLACKLIST
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BLACKLIST`;

CREATE TABLE `BLACKLIST` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `EVENT_DATE` date DEFAULT NULL COMMENT '发生日期',
  `CUSTOMER_NAME` varchar(10) DEFAULT NULL COMMENT '用户名',
  `CUSTOMER_TEL` varchar(20) NOT NULL COMMENT '联系方式',
  `EVENT_CONTENT` varchar(200) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`ID`),
  KEY `CUSTOMER_TEL` (`CUSTOMER_TEL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='黑名单';



# Dump of table BRANCH
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BRANCH`;

CREATE TABLE `BRANCH` (
  `BRANCH_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BRANCH_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`BRANCH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分店表';



# Dump of table LOG
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LOG`;

CREATE TABLE `LOG` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `CONTENT` varchar(5000) DEFAULT NULL,
  `OPERATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OPERATOR` varchar(50) NOT NULL DEFAULT '',
  `URL` varchar(200) DEFAULT NULL,
  `IP` varchar(15) DEFAULT NULL,
  `SESSIONID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table LOTTERY
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LOTTERY`;

CREATE TABLE `LOTTERY` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `LOTTERY_NO` int(11) NOT NULL COMMENT '抽奖号',
  `WX` varchar(20) NOT NULL COMMENT '微信号',
  `TEL` varchar(20) DEFAULT '' COMMENT '联系方式',
  `RECORD_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '抽奖日期',
  `LOTTERY_TERM_NO` int(11) NOT NULL COMMENT '期数',
  `GETLOTTERY` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否中奖',
  `ISVALID` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否符合条件',
  `ISFINISH` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否领奖',
  `FINISHTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `LOTTERY_NO` (`LOTTERY_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table LOTTERY_TERM
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LOTTERY_TERM`;

CREATE TABLE `LOTTERY_TERM` (
  `LOTTERY_TERM_NO` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `START` date NOT NULL COMMENT '开始时间',
  `END` datetime DEFAULT NULL COMMENT '结束时间',
  `ISEND` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`LOTTERY_TERM_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抽奖期数表';



# Dump of table ORDER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ORDER`;

CREATE TABLE `ORDER` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ORDER_NO` int(11) NOT NULL DEFAULT '999',
  `ORDER_DATE` date NOT NULL COMMENT '取货日期',
  `ORDER_TIME` time DEFAULT NULL COMMENT '取货时间',
  `ORDER_PRICE` int(20) NOT NULL DEFAULT '0' COMMENT '订单价格',
  `ORDER_CONTENT` varchar(100) DEFAULT '' COMMENT '订单内容',
  `ORDER_PAID` int(20) NOT NULL DEFAULT '0' COMMENT '已付订金',
  `CUSTOMER_WX` varchar(100) DEFAULT NULL COMMENT '微信号',
  `CUSTOMER_NAME` varchar(20) DEFAULT NULL COMMENT '客户名',
  `CUSTOMER_TEL` varchar(20) DEFAULT NULL COMMENT '客户联系方式',
  `ORDER_RECORDER` varchar(20) DEFAULT '' COMMENT '订单记录人',
  `ORDER_DELIVER_ADDRESS` varchar(200) DEFAULT NULL COMMENT '送货地址',
  `ORDER_RECORD_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单记录时间',
  `ORDER_FINISHED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已取',
  `ORDER_COMPLETE` tinyint(4) NOT NULL DEFAULT '0',
  `DELETE_FLAG` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0:未被删除 1:已删除',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `WEIJUJU_NO` varchar(20) DEFAULT NULL COMMENT '微聚聚订单编号',
  `BRANCH_ID` int(11) DEFAULT NULL COMMENT '分店',
  `ORDER_REMARK` int(11) NOT NULL DEFAULT '0' COMMENT '标记字段',
  `DELIVER_FLAG` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';



# Dump of table PAYMENT
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAYMENT`;

CREATE TABLE `PAYMENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PAYMENT_TIME` time NOT NULL COMMENT '支付时间',
  `PAYMENT_TYPE_ID` int(11) NOT NULL DEFAULT '1' COMMENT '支付类型：1-订单 2-切件 3-其它',
  `PAYMENT_ACCOUNT_ID` int(11) NOT NULL COMMENT '支付账号',
  `PAID` int(11) NOT NULL COMMENT '价格',
  `MEMO` varchar(100) DEFAULT NULL COMMENT '备注',
  `PAYMENT_DATE` date NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table PAYMENT_ACCOUNT
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAYMENT_ACCOUNT`;

CREATE TABLE `PAYMENT_ACCOUNT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PAYMENT_ACCOUNT_NAME` varchar(10) NOT NULL DEFAULT '' COMMENT '名称',
  `BRANCH_ID` int(11) NOT NULL COMMENT '所属分店',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table PAYMENT_ORDER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAYMENT_ORDER`;

CREATE TABLE `PAYMENT_ORDER` (
  `PAYMENT_ID` int(11) NOT NULL,
  `ORDER_ID` int(11) NOT NULL,
  PRIMARY KEY (`PAYMENT_ID`,`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table USER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `USERNAME` varchar(20) NOT NULL,
  `PASSWORD` varchar(32) NOT NULL,
  `RIGHTS` int(1) NOT NULL,
  `BRANCH_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table view_order
# ------------------------------------------------------------

DROP VIEW IF EXISTS `view_order`;

CREATE TABLE `view_order` (
   `ID` INT(10) UNSIGNED NOT NULL DEFAULT '0',
   `ORDER_NO` INT(11) NOT NULL DEFAULT '999',
   `ORDER_DATE` DATE NOT NULL,
   `ORDER_TIME` TIME NULL DEFAULT NULL,
   `ORDER_PRICE` INT(20) NOT NULL DEFAULT '0',
   `ORDER_CONTENT` VARCHAR(100) NULL DEFAULT '',
   `ORDER_PAID` INT(20) NOT NULL DEFAULT '0',
   `CUSTOMER_WX` VARCHAR(100) NULL DEFAULT NULL,
   `CUSTOMER_NAME` VARCHAR(20) NULL DEFAULT NULL,
   `CUSTOMER_TEL` VARCHAR(20) NULL DEFAULT NULL,
   `ORDER_RECORDER` VARCHAR(20) NULL DEFAULT '',
   `ORDER_DELIVER_ADDRESS` VARCHAR(200) NULL DEFAULT NULL,
   `ORDER_RECORD_TIME` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
   `ORDER_FINISHED` TINYINT(1) NOT NULL DEFAULT '0',
   `ORDER_COMPLETE` TINYINT(4) NOT NULL DEFAULT '0',
   `DELETE_FLAG` TINYINT(1) NOT NULL DEFAULT '0',
   `MEMO` VARCHAR(255) NULL DEFAULT NULL,
   `WEIJUJU_NO` VARCHAR(20) NULL DEFAULT NULL,
   `BRANCH_ID` INT(11) NULL DEFAULT NULL,
   `ORDER_REMARK` INT(11) NOT NULL DEFAULT '0',
   `DELIVER_FLAG` TINYINT(1) NOT NULL DEFAULT '0',
   `BRANCH_NAME` VARCHAR(20) NULL DEFAULT NULL
) ENGINE=MyISAM;



# Dump of table view_order_payment
# ------------------------------------------------------------

DROP VIEW IF EXISTS `view_order_payment`;

CREATE TABLE `view_order_payment` (
   `ID` INT(11) NOT NULL DEFAULT '0',
   `PAYMENT_DATE` DATE NOT NULL,
   `PAYMENT_TIME` TIME NOT NULL,
   `PAYMENT_TYPE_ID` INT(11) NOT NULL DEFAULT '1',
   `PAYMENT_ACCOUNT_ID` INT(11) NOT NULL,
   `PAID` INT(11) NOT NULL,
   `MEMO` VARCHAR(100) NULL DEFAULT NULL,
   `ORDER_ID` INT(10) UNSIGNED NOT NULL DEFAULT '0',
   `PAYMENT_ACCOUNT_NAME` VARCHAR(10) NOT NULL DEFAULT ''
) ENGINE=MyISAM;



# Dump of table view_payment
# ------------------------------------------------------------

DROP VIEW IF EXISTS `view_payment`;

CREATE TABLE `view_payment` (
   `ID` INT(11) NOT NULL DEFAULT '0',
   `PAYMENT_TIME` TIME NOT NULL,
   `PAYMENT_DATE` DATE NOT NULL,
   `PAYMENT_TYPE_ID` INT(11) NOT NULL DEFAULT '1',
   `PAYMENT_ACCOUNT_ID` INT(11) NOT NULL,
   `PAID` INT(11) NOT NULL,
   `MEMO` VARCHAR(100) NULL DEFAULT NULL,
   `PAYMENT_ACCOUNT_NAME` VARCHAR(10) NOT NULL DEFAULT '',
   `BRANCH_NAME` VARCHAR(20) NOT NULL
) ENGINE=MyISAM;



# Dump of table view_payment_account
# ------------------------------------------------------------

DROP VIEW IF EXISTS `view_payment_account`;

CREATE TABLE `view_payment_account` (
   `ID` INT(11) NOT NULL DEFAULT '0',
   `PAYMENT_ACCOUNT_NAME` VARCHAR(10) NOT NULL DEFAULT '',
   `BRANCH_ID` INT(11) NOT NULL,
   `BRANCH_NAME` VARCHAR(20) NOT NULL
) ENGINE=MyISAM;



# Dump of table view_payment_statement
# ------------------------------------------------------------

DROP VIEW IF EXISTS `view_payment_statement`;

CREATE TABLE `view_payment_statement` (
   `PAYMENT_DATE` DATE NOT NULL,
   `PAYMENT_ACCOUNT_ID` INT(11) NOT NULL,
   `AMOUNT` DECIMAL(32) NULL DEFAULT NULL,
   `PAYMENT_ACCOUNT_NAME` VARCHAR(10) NOT NULL DEFAULT '',
   `BRANCH_ID` INT(11) NOT NULL,
   `BRANCH_NAME` VARCHAR(20) NOT NULL
) ENGINE=MyISAM;



# Dump of table view_payment_sum
# ------------------------------------------------------------

DROP VIEW IF EXISTS `view_payment_sum`;

CREATE TABLE `view_payment_sum` (
   `PAYMENT_DATE` DATE NOT NULL,
   `PAYMENT_ACCOUNT_ID` INT(11) NOT NULL,
   `AMOUNT` DECIMAL(32) NULL DEFAULT NULL
) ENGINE=MyISAM;



# Dump of table view_user
# ------------------------------------------------------------

DROP VIEW IF EXISTS `view_user`;

CREATE TABLE `view_user` (
   `ID` INT(10) UNSIGNED NOT NULL DEFAULT '0',
   `USERNAME` VARCHAR(20) NOT NULL,
   `PASSWORD` VARCHAR(32) NOT NULL,
   `BRANCH_ID` INT(11) NOT NULL,
   `RIGHTS` INT(1) NOT NULL,
   `BRANCH_NAME` VARCHAR(20) NOT NULL
) ENGINE=MyISAM;





# Replace placeholder table for view_user with correct view syntax
# ------------------------------------------------------------

DROP TABLE `view_user`;

CREATE ALGORITHM=UNDEFINED DEFINER=`pock`@`%` SQL SECURITY DEFINER VIEW `view_user`
AS SELECT
   `u`.`ID` AS `ID`,
   `u`.`USERNAME` AS `USERNAME`,
   `u`.`PASSWORD` AS `PASSWORD`,
   `u`.`BRANCH_ID` AS `BRANCH_ID`,
   `u`.`RIGHTS` AS `RIGHTS`,
   `b`.`BRANCH_NAME` AS `BRANCH_NAME`
FROM (`USER` `u` join `BRANCH` `b`) where (`u`.`BRANCH_ID` = `b`.`BRANCH_ID`);


# Replace placeholder table for view_payment_account with correct view syntax
# ------------------------------------------------------------

DROP TABLE `view_payment_account`;

CREATE ALGORITHM=UNDEFINED DEFINER=`pock`@`%` SQL SECURITY DEFINER VIEW `view_payment_account`
AS SELECT
   `pa`.`ID` AS `ID`,
   `pa`.`PAYMENT_ACCOUNT_NAME` AS `PAYMENT_ACCOUNT_NAME`,
   `pa`.`BRANCH_ID` AS `BRANCH_ID`,
   `b`.`BRANCH_NAME` AS `BRANCH_NAME`
FROM (`PAYMENT_ACCOUNT` `pa` join `BRANCH` `b`) where (`pa`.`BRANCH_ID` = `b`.`BRANCH_ID`);


# Replace placeholder table for view_order with correct view syntax
# ------------------------------------------------------------

DROP TABLE `view_order`;

CREATE ALGORITHM=UNDEFINED DEFINER=`pock`@`%` SQL SECURITY DEFINER VIEW `view_order`
AS SELECT
   `o`.`ID` AS `ID`,
   `o`.`ORDER_NO` AS `ORDER_NO`,
   `o`.`ORDER_DATE` AS `ORDER_DATE`,
   `o`.`ORDER_TIME` AS `ORDER_TIME`,
   `o`.`ORDER_PRICE` AS `ORDER_PRICE`,
   `o`.`ORDER_CONTENT` AS `ORDER_CONTENT`,
   `o`.`ORDER_PAID` AS `ORDER_PAID`,
   `o`.`CUSTOMER_WX` AS `CUSTOMER_WX`,
   `o`.`CUSTOMER_NAME` AS `CUSTOMER_NAME`,
   `o`.`CUSTOMER_TEL` AS `CUSTOMER_TEL`,
   `o`.`ORDER_RECORDER` AS `ORDER_RECORDER`,
   `o`.`ORDER_DELIVER_ADDRESS` AS `ORDER_DELIVER_ADDRESS`,
   `o`.`ORDER_RECORD_TIME` AS `ORDER_RECORD_TIME`,
   `o`.`ORDER_FINISHED` AS `ORDER_FINISHED`,
   `o`.`ORDER_COMPLETE` AS `ORDER_COMPLETE`,
   `o`.`DELETE_FLAG` AS `DELETE_FLAG`,
   `o`.`MEMO` AS `MEMO`,
   `o`.`WEIJUJU_NO` AS `WEIJUJU_NO`,
   `o`.`BRANCH_ID` AS `BRANCH_ID`,
   `o`.`ORDER_REMARK` AS `ORDER_REMARK`,
   `o`.`DELIVER_FLAG` AS `DELIVER_FLAG`,
   `b`.`BRANCH_NAME` AS `BRANCH_NAME`
FROM (`ORDER` `o` left join `BRANCH` `b` on((`b`.`BRANCH_ID` = `o`.`BRANCH_ID`))) where (`o`.`DELETE_FLAG` = 0);


# Replace placeholder table for view_payment with correct view syntax
# ------------------------------------------------------------

DROP TABLE `view_payment`;

CREATE ALGORITHM=UNDEFINED DEFINER=`pock`@`%` SQL SECURITY DEFINER VIEW `view_payment`
AS SELECT
   `p`.`ID` AS `ID`,
   `p`.`PAYMENT_TIME` AS `PAYMENT_TIME`,
   `p`.`PAYMENT_DATE` AS `PAYMENT_DATE`,
   `p`.`PAYMENT_TYPE_ID` AS `PAYMENT_TYPE_ID`,
   `p`.`PAYMENT_ACCOUNT_ID` AS `PAYMENT_ACCOUNT_ID`,
   `p`.`PAID` AS `PAID`,
   `p`.`MEMO` AS `MEMO`,
   `pa`.`PAYMENT_ACCOUNT_NAME` AS `PAYMENT_ACCOUNT_NAME`,
   `b`.`BRANCH_NAME` AS `BRANCH_NAME`
FROM ((`PAYMENT` `p` join `PAYMENT_ACCOUNT` `pa`) join `BRANCH` `b`) where ((`p`.`PAYMENT_ACCOUNT_ID` = `pa`.`ID`) and (`b`.`BRANCH_ID` = `pa`.`BRANCH_ID`));


# Replace placeholder table for view_order_payment with correct view syntax
# ------------------------------------------------------------

DROP TABLE `view_order_payment`;

CREATE ALGORITHM=UNDEFINED DEFINER=`pock`@`%` SQL SECURITY DEFINER VIEW `view_order_payment`
AS SELECT
   `p`.`ID` AS `ID`,
   `p`.`PAYMENT_DATE` AS `PAYMENT_DATE`,
   `p`.`PAYMENT_TIME` AS `PAYMENT_TIME`,
   `p`.`PAYMENT_TYPE_ID` AS `PAYMENT_TYPE_ID`,
   `p`.`PAYMENT_ACCOUNT_ID` AS `PAYMENT_ACCOUNT_ID`,
   `p`.`PAID` AS `PAID`,
   `p`.`MEMO` AS `MEMO`,
   `o`.`ID` AS `ORDER_ID`,
   `pa`.`PAYMENT_ACCOUNT_NAME` AS `PAYMENT_ACCOUNT_NAME`
FROM (((`PAYMENT` `p` join `ORDER` `o`) join `PAYMENT_ORDER` `po`) join `PAYMENT_ACCOUNT` `pa`) where ((`o`.`ID` = `po`.`ORDER_ID`) and (`po`.`PAYMENT_ID` = `p`.`ID`) and (`pa`.`ID` = `p`.`PAYMENT_ACCOUNT_ID`));


# Replace placeholder table for view_payment_statement with correct view syntax
# ------------------------------------------------------------

DROP TABLE `view_payment_statement`;

CREATE ALGORITHM=UNDEFINED DEFINER=`pock`@`%` SQL SECURITY DEFINER VIEW `view_payment_statement`
AS SELECT
   `ps`.`PAYMENT_DATE` AS `PAYMENT_DATE`,
   `ps`.`PAYMENT_ACCOUNT_ID` AS `PAYMENT_ACCOUNT_ID`,
   `ps`.`AMOUNT` AS `AMOUNT`,
   `pa`.`PAYMENT_ACCOUNT_NAME` AS `PAYMENT_ACCOUNT_NAME`,
   `pa`.`BRANCH_ID` AS `BRANCH_ID`,
   `pa`.`BRANCH_NAME` AS `BRANCH_NAME`
FROM (`view_payment_sum` `ps` join `view_payment_account` `pa`) where (`pa`.`ID` = `ps`.`PAYMENT_ACCOUNT_ID`);


# Replace placeholder table for view_payment_sum with correct view syntax
# ------------------------------------------------------------

DROP TABLE `view_payment_sum`;

CREATE ALGORITHM=UNDEFINED DEFINER=`pock`@`%` SQL SECURITY DEFINER VIEW `view_payment_sum`
AS SELECT
   `p`.`PAYMENT_DATE` AS `PAYMENT_DATE`,
   `p`.`PAYMENT_ACCOUNT_ID` AS `PAYMENT_ACCOUNT_ID`,sum(`p`.`PAID`) AS `AMOUNT`
FROM `PAYMENT` `p` group by `p`.`PAYMENT_DATE`,`p`.`PAYMENT_ACCOUNT_ID` order by `p`.`PAYMENT_DATE`,`p`.`PAYMENT_ACCOUNT_ID`;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
