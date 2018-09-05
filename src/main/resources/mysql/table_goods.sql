/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-09-05 17:21:47
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
  `goods_num` int(11) DEFAULT NULL COMMENT '商品数量（库存）',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `exp` datetime NOT NULL COMMENT '保质期',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';
SET FOREIGN_KEY_CHECKS=1;
