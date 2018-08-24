/*
Navicat MySQL Data Transfer

Source Server         : 10.1.1.12
Source Server Version : 50718
Source Host           : 10.1.1.12:3306
Source Database       : jrtz_app

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-08-24 14:52:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `PERMISSION_NAME` varchar(32) NOT NULL DEFAULT '' COMMENT '权限名称',
  `TYPE` smallint(1) NOT NULL DEFAULT '1' COMMENT '权限类型(1:菜单|2:权限)',
  `PERMISSION_SIGN` varchar(50) DEFAULT '' COMMENT '权限标识',
  `PARENT_ID` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `URL` varchar(200) DEFAULT '' COMMENT '链接地址',
  `VALID` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效(0:否|1:是)',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `ICON` varchar(100) DEFAULT '' COMMENT 'font awsome所使用的图标代码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='系统权限表';
SET FOREIGN_KEY_CHECKS=1;
