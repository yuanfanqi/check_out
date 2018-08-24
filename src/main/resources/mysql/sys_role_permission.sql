/*
Navicat MySQL Data Transfer

Source Server         : 10.1.1.12
Source Server Version : 50718
Source Host           : 10.1.1.12:3306
Source Database       : jrtz_app

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-08-24 17:44:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `SYS_ROLE_ID` int(11) DEFAULT NULL COMMENT '关联系统角色表ID',
  `SYS_PERMISSION_ID` int(11) DEFAULT NULL COMMENT '关联系统权限表ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8 COMMENT='系统角色与权限中间表';
SET FOREIGN_KEY_CHECKS=1;
