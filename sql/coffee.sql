/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : coffee

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 25/10/2024 14:12:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `item_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `stock` int(0) NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `press` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `press_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, '测试3', '‌‌浓缩咖啡', 0, 11.00, ' 111', 'http://localhost:9999/images/img.png', '测试3', '测试3', '2023-10-10');
INSERT INTO `item` VALUES (2, '测试1', '‌‌浓缩咖啡', 5, 11.00, ' 111', 'http://localhost:9999/images/img_1.png', '测试1', '测试1', '2023-10-10');
INSERT INTO `item` VALUES (3, '测试2', '‌‌浓缩咖啡', 10, 10.00, ' ', 'http://localhost:9999/images/img_2.png', '测试2', '测试2', '2023-10-10');
INSERT INTO `item` VALUES (4, '测试4', '‌‌美式咖啡', 11, 22.00, ' 测试4测试4测试4测试4测试4', 'http://localhost:9999/images/img_3.png', '测试4', '测试4', '2023-10-10');
INSERT INTO `item` VALUES (9, '1', '‌‌浓缩咖啡', 11, 1.00, '11', '/api/upload/img_2.png', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `order_total` decimal(10, 2) NULL DEFAULT NULL,
  `created_time` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `fk_order_user_auth_1`(`user_id`) USING BTREE,
  CONSTRAINT `fk_order_user_auth_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (27, 1, 11.00, '2024-10-25 12:03:48.122897', '22', '已完成');
INSERT INTO `order` VALUES (28, 1, 11.00, '2024-10-25 14:01:40.429000', '111', '处理中');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `order_id` int(0) NOT NULL,
  `item_id` int(0) NOT NULL,
  `number` int(0) NOT NULL,
  PRIMARY KEY (`order_id`, `item_id`) USING BTREE,
  INDEX `fk_order_detail_item_1`(`item_id`) USING BTREE,
  CONSTRAINT `fk_order_detail_item_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_detail_order_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (27, 2, 1);
INSERT INTO `order_detail` VALUES (28, 2, 1);

-- ----------------------------
-- Table structure for shoppingcart_item
-- ----------------------------
DROP TABLE IF EXISTS `shoppingcart_item`;
CREATE TABLE `shoppingcart_item`  (
  `user_id` int(0) NOT NULL,
  `item_id` int(0) NOT NULL,
  `number` int(0) NOT NULL,
  PRIMARY KEY (`user_id`, `item_id`) USING BTREE,
  INDEX `fk_shoppingcart_item_1`(`item_id`) USING BTREE,
  CONSTRAINT `fk_shoppingcart_item_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_shoppingcart_user_auth_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `account_unique`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', '123456', '1', '1');
INSERT INTO `user` VALUES (3, 'test123456', 'test123456', '18320222222', '2777679537@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
