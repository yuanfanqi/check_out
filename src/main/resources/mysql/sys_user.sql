/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-11 10:54:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `USER_NAME` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `PASSWORD` varchar(32) NOT NULL DEFAULT '' COMMENT '用户密码',
  `SALT` varchar(32) NOT NULL DEFAULT '' COMMENT '加密用盐',
  `NICK_NAME` varchar(32) DEFAULT '' COMMENT '昵称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `LOCKED` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否被锁定',
  `ONLINE` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否在线',
  `LOGIN_TIME` datetime DEFAULT NULL COMMENT '上次登录时间',
  `LOGIN_IP` varchar(40) DEFAULT '' COMMENT '上次登录IP',
  `VALID` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效',
  `REMARK` varchar(500) DEFAULT '暂无备注' COMMENT '备注',
  `PHONE` varchar(20) DEFAULT '' COMMENT '手机号码',
  `EMAIL` varchar(100) DEFAULT '' COMMENT '邮箱',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_NAME_UNIQUE` (`USER_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'b9d11b3be25f5a1a7dc8ca04cd310b28', '123456', 'admin', '2018-08-29 14:57:57', '2018-08-29 14:58:18', '\0', '', '2018-10-11 10:51:30', '', '', '暂无备注', '', '');
SET FOREIGN_KEY_CHECKS=1;
