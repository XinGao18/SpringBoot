/*
 Navicat Premium Data Transfer

 Source Server         : blog
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 192.168.199.131:3306
 Source Schema         : db_store

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 17/05/2022 13:11:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_profile
-- ----------------------------
DROP TABLE IF EXISTS `t_profile`;
CREATE TABLE `t_profile`  (
  `uid` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `thumburl` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `lastModified` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `lastModifiedDate` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `size` int(11) NULL DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `error` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `originFileObj` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `precent` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `response` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `length` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `prototype` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `upload` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_profile
-- ----------------------------
INSERT INTO `t_profile` VALUES ('9', 'data:image/png;base64,iVNQaabshdvbjsdvAABJRU5ErkJggg==', 'Merry', '我喜欢你.jpg', NULL, NULL, 12345, 'image/jpg', '0', 'fbtdhf', 'fbf', 'fbf', 'fbf', 'fbf', 'Object', 'fgndgndf');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'sex',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'email',
  `intro` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自我简绍',
  PRIMARY KEY (`username`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('Merry', 'Female', 'xin.gao18@student.xjtlu.edu.cn', 'I am very shame!');

SET FOREIGN_KEY_CHECKS = 1;
