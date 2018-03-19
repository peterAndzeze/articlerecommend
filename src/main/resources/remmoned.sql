/*
 Navicat MySQL Data Transfer

 Source Server         : customer
 Source Server Type    : MySQL
 Source Server Version : 50631
 Source Host           : localhost
 Source Database       : remmoned

 Target Server Type    : MySQL
 Target Server Version : 50631
 File Encoding         : utf-8

 Date: 03/18/2018 17:42:37 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_article`
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '虚拟主键',
  `content` varchar(256) DEFAULT NULL COMMENT '文章内容（标签信息）',
  `release_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  `topic_id` bigint(32) DEFAULT NULL COMMENT '主题',
  `source_url` varchar(255) DEFAULT NULL COMMENT '文章来源',
  `title` varchar(128) DEFAULT NULL COMMENT '文章标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8 COMMENT='文章信息表';

-- ----------------------------
--  Table structure for `tb_article_topic`
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_topic`;
CREATE TABLE `tb_article_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_name` text NOT NULL COMMENT '主题名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章主题信息';

-- ----------------------------
--  Table structure for `tb_user_article_logs`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_article_logs`;
CREATE TABLE `tb_user_article_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `article_id` bigint(20) NOT NULL COMMENT '文章编号',
  `view_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '浏览时间',
  `prefer_degree` int(11) NOT NULL DEFAULT '0' COMMENT '用户偏好程度 默认为0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `news_id` (`article_id`),
  CONSTRAINT `newslogs_news_id` FOREIGN KEY (`article_id`) REFERENCES `tb_article` (`id`),
  CONSTRAINT `newslogs_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户操作文章结果信息';

-- ----------------------------
--  Table structure for `tb_users`
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
  `id` bigint(20) NOT NULL COMMENT '用户编号',
  `pref_list` text NOT NULL COMMENT '关键词列表',
  `latest_log_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL COMMENT '用户姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

SET FOREIGN_KEY_CHECKS = 1;
