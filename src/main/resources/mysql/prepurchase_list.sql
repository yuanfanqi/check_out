/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-18 17:00:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for prepurchase_list
-- ----------------------------
DROP TABLE IF EXISTS `prepurchase_list`;
CREATE TABLE `prepurchase_list` (
  `theme_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '待补货' COMMENT '名单期数',
  `goods_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `goods_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品ID（商品条码）',
  `last_price` decimal(10,2) DEFAULT NULL COMMENT '上次进货价格',
  `prepurchase_num` int(11) DEFAULT NULL COMMENT '预计补货数量',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`goods_id`,`theme_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预进货名单表';
SET FOREIGN_KEY_CHECKS=1;
