/*
Navicat MySQL Data Transfer

Source Server         : Lynn
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : lynn

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-02-04 14:52:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_role_nav`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_nav`;
CREATE TABLE `sys_role_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `nav_id` int(11) NOT NULL,
  `privilege` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_role_nav
-- ----------------------------
INSERT INTO `sys_role_nav` VALUES ('3', '1', '3', '2');
INSERT INTO `sys_role_nav` VALUES ('4', '1', '4', '2');
INSERT INTO `sys_role_nav` VALUES ('5', '1', '5', '2');
INSERT INTO `sys_role_nav` VALUES ('6', '1', '6', '2');
INSERT INTO `sys_role_nav` VALUES ('7', '1', '7', '0');
INSERT INTO `sys_role_nav` VALUES ('8', '1', '8', '2');
INSERT INTO `sys_role_nav` VALUES ('9', '1', '9', '2');
INSERT INTO `sys_role_nav` VALUES ('10', '1', '10', '0');
