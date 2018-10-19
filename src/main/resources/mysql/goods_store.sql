/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-11 10:54:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods_store
-- ----------------------------
DROP TABLE IF EXISTS `goods_store`;
CREATE TABLE `goods_store` (
  `goods_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品ID（商品条码）',
  `goods_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `goods_num` int(11) DEFAULT NULL COMMENT '商品数量（库存）',
  `insert_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `exp` datetime NOT NULL COMMENT '保质期',
  PRIMARY KEY (`goods_id`,`insert_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品库存表';

-- ----------------------------
-- Records of goods_store
-- ----------------------------
INSERT INTO `goods_store` VALUES ('111', '测试商品111', '10', '2018-09-20 10:37:12', '2018-10-04 00:00:00');
SET FOREIGN_KEY_CHECKS=1;
