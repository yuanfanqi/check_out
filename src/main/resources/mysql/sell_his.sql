/*
Navicat MySQL Data Transfer

Source Server         : loc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : check_out

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-09-05 17:21:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sell_his
-- ----------------------------
DROP TABLE IF EXISTS `sell_his`;
CREATE TABLE `sell_his` (
  `sell_date` datetime NOT NULL COMMENT '日期',
  `goods_name` varchar(100) NOT NULL COMMENT '商品名称',
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID（商品条码）',
  `sell_num` int(11) DEFAULT NULL COMMENT '销售数量',
  `sell_price` decimal(10,2) DEFAULT NULL COMMENT '实际销售价格',
  PRIMARY KEY (`sell_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售历史表';
SET FOREIGN_KEY_CHECKS=1;
