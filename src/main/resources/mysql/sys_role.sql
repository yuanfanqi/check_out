/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-09-04 18:05:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `ROLE_NAME` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
  `VALID` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效(0:否|1:是)',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `REMARK` varchar(500) DEFAULT '暂无备注' COMMENT '备注',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ROLE_NAME_UNIQUE` (`ROLE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('45', '收银员', '', '2018-09-04 16:05:29', '2018-09-04 16:05:29', '具有查看权限');
SET FOREIGN_KEY_CHECKS=1;
