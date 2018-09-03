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
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
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

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '系统模块', '0', '', '0', '', '', '2018-08-29 15:35:50', '2018-08-29 15:35:53', '');
INSERT INTO `sys_permission` VALUES ('2', '业务模块', '0', '', '0', '', '', '2018-08-29 15:35:50', '2018-08-29 15:35:53', '');
INSERT INTO `sys_permission` VALUES ('3','菜单管理','1','','1','/sys/permission/show','', '2018-08-29 15:35:50', '2018-08-29 15:35:53','fa fa-bars');
INSERT INTO `sys_permission` VALUES ('4','用户管理','1','','1','/sys/user/show','', '2018-08-29 15:35:50', '2018-08-29 15:35:53','fa fa-bars');
INSERT INTO `sys_permission` VALUES ('5','角色管理','1','','1','/sys/role/show','', '2018-08-29 15:35:50', '2018-08-29 15:35:53','fa fa-bars');
INSERT INTO `sys_permission` VALUES ('6', '商品管理', '1', '', '2', '', '', '2018-08-27 10:22:28', '2018-08-27 10:22:31', '');
INSERT INTO `sys_permission` VALUES ('7', '库存管理', '1', '', '2', '', '', '2018-08-27 10:22:59', '2018-08-27 10:23:02', '');
INSERT INTO `sys_permission` VALUES ('8', '进货名单', '1', '', '2', '', '', '2018-08-27 10:23:22', '2018-08-27 10:23:27', '');
INSERT INTO `sys_permission` VALUES ('9', '昨日战绩', '1', '', '2', '', '', '2018-08-27 10:23:40', '2018-08-27 10:23:43', '');
INSERT INTO `sys_permission` VALUES ('10', '代码生成', '1', '', '1', '/sys/generator/show', '', '2017-09-08 09:11:16', '2017-09-08 09:11:16', 'fa fa-file-code-o');
INSERT INTO `sys_permission` VALUES ('11', '列表', '2', 'generator:index', '10', NULL, '', '2017-09-08 09:12:14', '2017-09-08 09:12:14', NULL);
INSERT INTO `sys_permission` VALUES ('12', '查看', '2', 'menu:fetch', '3', NULL, '', '2017-05-31 15:36:35', '2017-05-31 15:36:35', NULL);
INSERT INTO `sys_permission` VALUES ('13', '添加', '2', 'menu:add', '3', '', '', '2017-06-02 11:33:51', '2017-06-02 11:34:03', '');
INSERT INTO `sys_permission` VALUES ('14', '修改', '2', 'menu:modify', '3', '', '', '2017-06-02 11:33:46', '2017-06-02 11:34:11', '');
INSERT INTO `sys_permission` VALUES ('15', '删除', '2', 'menu:remove', '3', '', '', '2017-06-02 11:33:56', '2017-06-02 11:34:16', '');
INSERT INTO `sys_permission` VALUES ('16', '查看', '2', 'user:fetch', '4', NULL, '', '2017-06-03 14:37:39', '2017-06-03 14:37:39', NULL);
INSERT INTO `sys_permission` VALUES ('17', '添加', '2', 'user:add', '4', NULL, '', '2017-06-03 14:38:05', '2017-06-03 14:38:05', NULL);
INSERT INTO `sys_permission` VALUES ('18', '修改', '2', 'user:modify', '4', NULL, '', '2017-06-03 14:38:27', '2017-06-03 14:38:27', NULL);
INSERT INTO `sys_permission` VALUES ('19', '删除', '2', 'user:remove', '4', NULL, '', '2017-06-03 14:38:54', '2017-06-03 14:38:54', NULL);
INSERT INTO `sys_permission` VALUES ('20', '查看', '2', 'role:fetch', '5', NULL, '', '2017-06-03 14:39:27', '2017-06-03 14:39:27', NULL);
INSERT INTO `sys_permission` VALUES ('21', '添加', '2', 'role:add', '5', NULL, '', '2017-06-03 14:39:44', '2017-06-03 14:39:44', NULL);
INSERT INTO `sys_permission` VALUES ('22', '修改', '2', 'role:modify', '5', NULL, '', '2017-06-03 14:40:07', '2017-06-03 14:40:07', NULL);
INSERT INTO `sys_permission` VALUES ('23', '删除', '2', 'role:remove', '5', NULL, '', '2017-06-03 14:40:33', '2017-06-03 14:40:33', NULL);
INSERT INTO `sys_permission` VALUES ('24', '列表', '2', 'menu:index', '3', NULL, '', '2017-06-06 15:15:51', '2017-06-06 15:15:51', NULL);
INSERT INTO `sys_permission` VALUES ('25', '列表', '2', 'user:index', '4', NULL, '', '2017-06-06 15:39:39', '2017-06-06 15:39:39', NULL);
INSERT INTO `sys_permission` VALUES ('26', '列表', '2', 'role:index', '5', NULL, '', '2017-06-06 15:40:35', '2017-06-06 15:40:35', NULL);

SET FOREIGN_KEY_CHECKS=1;
