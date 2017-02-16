/*
Navicat MySQL Data Transfer

Source Server         : Lynn
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : lynn

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-12-26 10:58:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hs_arena`
-- ----------------------------
DROP TABLE IF EXISTS `hs_arena`;
CREATE TABLE `hs_arena` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `occupation` varchar(64) NOT NULL COMMENT '英雄',
  `wins` int(11) NOT NULL DEFAULT '0' COMMENT '胜场',
  `losses` int(11) NOT NULL DEFAULT '0' COMMENT '败场',
  `gold` int(11) NOT NULL DEFAULT '0' COMMENT '金币',
  `dust` int(11) NOT NULL DEFAULT '0' COMMENT '粉尘',
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of hs_arena
-- ----------------------------
INSERT INTO `hs_arena` VALUES ('1', 'rouge', '9', '3', '150', '0', '2016-12-25 08:00:00');
INSERT INTO `hs_arena` VALUES ('2', 'hunter', '9', '3', '150', '0', '2016-12-25 18:37:01');
INSERT INTO `hs_arena` VALUES ('3', 'hunter', '3', '3', '40', '0', '2016-12-06 08:00:00');
INSERT INTO `hs_arena` VALUES ('4', 'warlock', '4', '3', '50', '0', '2016-12-06 08:00:00');
INSERT INTO `hs_arena` VALUES ('5', 'warrior', '4', '3', '55', '0', '2016-12-07 08:00:00');
INSERT INTO `hs_arena` VALUES ('6', 'rouge', '4', '3', '0', '0', '2016-12-07 08:00:00');
INSERT INTO `hs_arena` VALUES ('7', 'shaman', '3', '3', '0', '0', '2016-12-07 08:00:00');
INSERT INTO `hs_arena` VALUES ('8', 'mage', '3', '3', '0', '0', '2016-12-07 08:00:00');
INSERT INTO `hs_arena` VALUES ('9', 'paladin', '4', '3', '0', '0', '2016-12-10 08:00:00');
INSERT INTO `hs_arena` VALUES ('10', 'warrior', '5', '3', '0', '0', '2016-12-10 08:00:00');
INSERT INTO `hs_arena` VALUES ('11', 'hunter', '2', '3', '0', '0', '2016-12-12 08:00:00');
INSERT INTO `hs_arena` VALUES ('12', 'mage', '3', '3', '0', '0', '2016-12-13 08:00:00');
INSERT INTO `hs_arena` VALUES ('13', 'rouge', '12', '1', '250', '0', '2016-12-17 08:00:00');
INSERT INTO `hs_arena` VALUES ('14', 'warlock', '2', '3', '0', '0', '2016-12-18 08:00:00');
INSERT INTO `hs_arena` VALUES ('15', 'shaman', '3', '3', '0', '0', '2016-12-18 08:00:00');
INSERT INTO `hs_arena` VALUES ('16', 'paladin', '9', '3', '150', '0', '2016-12-19 08:00:00');
INSERT INTO `hs_arena` VALUES ('17', 'warlock', '9', '3', '165', '0', '2016-12-23 08:00:00');
INSERT INTO `hs_arena` VALUES ('18', 'mage', '10', '3', '205', '0', '2016-12-23 08:00:00');
