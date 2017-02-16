/*
Navicat MySQL Data Transfer

Source Server         : Lynn
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : lynn

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-02-04 14:52:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_nav`
-- ----------------------------
DROP TABLE IF EXISTS `sys_nav`;
CREATE TABLE `sys_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) unsigned zerofill NOT NULL,
  `nav_name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `nav_app` varchar(45) CHARACTER SET utf8 NOT NULL,
  `nav_index` int(11) NOT NULL,
  `nav_icon` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `nav_type` int(11) NOT NULL DEFAULT '0' COMMENT '栏目类型：0-公共，1-私有需权限',
  `nav_status` int(11) NOT NULL DEFAULT '0' COMMENT '栏目状态：0-不显示，1-显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_nav
-- ----------------------------
INSERT INTO `sys_nav` VALUES ('1', '00000000000', '首页', 'index', '1', 'fa fa-home', '0', '1');
INSERT INTO `sys_nav` VALUES ('2', '00000000000', '直播', 'live', '2', 'fa fa-caret-square-o-right', '0', '1');
INSERT INTO `sys_nav` VALUES ('3', '00000000000', '炉石传说', 'index', '3', 'fa fa-gamepad', '1', '1');
INSERT INTO `sys_nav` VALUES ('4', '00000000003', '统计数据', 'summary', '2', 'fa fa-bar-chart-o', '1', '1');
INSERT INTO `sys_nav` VALUES ('5', '00000000000', '系统管理', 'sys', '5', 'fa fa-list-ul', '1', '1');
INSERT INTO `sys_nav` VALUES ('6', '00000000005', '用户管理', 'sys/user', '1', null, '1', '1');
INSERT INTO `sys_nav` VALUES ('7', '00000000005', '角色管理', 'sys/role', '2', null, '1', '1');
INSERT INTO `sys_nav` VALUES ('8', '00000000005', '菜单管理', 'sys/nav', '3', null, '1', '1');
INSERT INTO `sys_nav` VALUES ('9', '00000000003', '记录数据', 'record', '1', 'fa fa-align-justify', '1', '1');
INSERT INTO `sys_nav` VALUES ('10', '00000000000', '隐藏力量', 'noseeme', '99', 'fa fa-times', '0', '0');
