/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-11 10:55:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for table_goods
-- ----------------------------
DROP TABLE IF EXISTS `table_goods`;
CREATE TABLE `table_goods` (
  `goods_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品ID（商品条码）',
  `goods_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `goods_type` tinyint(4) NOT NULL COMMENT '商品分类(1:洗浴用品2:护肤品3:服饰包包)',
  `goods_price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `goods_bid` decimal(10,2) DEFAULT NULL COMMENT '商品进价',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `supplier` tinyint(4) DEFAULT NULL COMMENT '供货商',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of table_goods
-- ----------------------------
INSERT INTO `table_goods` VALUES ('002', '测试商品', '2', '10.00', null, '2018-09-20 16:01:18', '1');
INSERT INTO `table_goods` VALUES ('004', '测试商品', '1', '10.00', null, '2018-09-11 09:40:12', null);
INSERT INTO `table_goods` VALUES ('111', '测试商品111', '2', '23.00', null, '2018-09-20 10:37:12', null);
SET FOREIGN_KEY_CHECKS=1;
