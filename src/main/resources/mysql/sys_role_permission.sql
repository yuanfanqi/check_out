/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-11 10:54:47
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
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8 COMMENT='系统角色与权限中间表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('255', '45', '1');
INSERT INTO `sys_role_permission` VALUES ('256', '45', '3');
INSERT INTO `sys_role_permission` VALUES ('257', '45', '12');
SET FOREIGN_KEY_CHECKS=1;
