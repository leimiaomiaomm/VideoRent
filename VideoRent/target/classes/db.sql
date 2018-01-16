/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : video_rent

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-12-26 15:04:39
*/


CREATE DATABASE IF NOT EXISTS video_rent DEFAULT  CHARACTER SET utf8 COLLATE utf8_general_ci;
USE video_rent;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(50) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_admin` char(2) NOT NULL COMMENT '身份，0-管理员；1-普通用户',
  `ID_card` varchar(255) NOT NULL COMMENT '身份证',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `gender` varchar(255) DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_r_video
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_r_video` (
  `user_r_video_id` int(50) NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(11) NOT NULL,
  `video_id` VARCHAR(11) NOT NULL,
  `actual_return_date` VARCHAR(50) DEFAULT NULL COMMENT '实际归还日期',
  `rent_date` VARCHAR(50) DEFAULT NULL COMMENT '借阅日期',
  `return_date` VARCHAR(50) DEFAULT NULL COMMENT '应归还日期',
  `rent` varchar(255) DEFAULT '' COMMENT '租金',
  `deposit` varchar(255) DEFAULT NULL COMMENT '押金',
  `invoice_number` varchar(255) DEFAULT NULL COMMENT '发票号',
  `is_rent` int(2) DEFAULT NULL COMMENT '是否租借：0-预约；1-租借' ,
  PRIMARY KEY (`user_r_video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_r_video
-- ----------------------------

-- ----------------------------
-- Table structure for video
-- ----------------------------
CREATE TABLE IF NOT EXISTS `video` (
  `video_id` int(50) NOT NULL AUTO_INCREMENT,
  `video_no` varchar(255) DEFAULT NULL COMMENT '编号',
  `video_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `director` varchar(255) DEFAULT NULL COMMENT '导演',
  `actor` varchar(255) DEFAULT NULL COMMENT '主演',
  `type` varchar(255) DEFAULT NULL COMMENT '影片类型',
  `year` varchar(255) DEFAULT NULL COMMENT '年份',
  `count` int(50) DEFAULT '0' COMMENT '总数量',
  `current_count` int(50) DEFAULT '0' COMMENT '库存',
  `price` varchar(10) DEFAULT NULL COMMENT '价格',
  `video_url` varchar(255) DEFAULT NULL COMMENT '预览视频文件存放路径',
  `desc` varchar(255) DEFAULT NULL COMMENT '简介',
  `status` char(2) DEFAULT '' COMMENT '影碟状态，0-未出租；1-已出租；2-丢失/损坏',
  `rent_times` varchar(255) DEFAULT NULL COMMENT '租借次数',
  `position` varchar(255) DEFAULT NULL COMMENT '存放位置',
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
