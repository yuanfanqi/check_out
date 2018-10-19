/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-11 10:54:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `SYS_USER_ID` int(11) DEFAULT NULL COMMENT '关联系统用户表ID',
  `SYS_ROLE_ID` int(11) DEFAULT NULL COMMENT '关联系统角色表ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='系统用户与角色中间表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
