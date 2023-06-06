# DROP DATABASE IF EXISTS `lzhphantom_codegen`;

# CREATE DATABASE  `lzhphantom_codegen` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

USE `lzhphantom_codegen`;

-- ----------------------------
-- Table structure for lzhphantom_gen_datasource_conf
-- ----------------------------
DROP TABLE IF EXISTS `lzhphantom_gen_datasource_conf`;
CREATE TABLE `lzhphantom_gen_datasource_conf`
(
    `id`          bigint NOT NULL,
    `name`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '数据源名称',
    `url`         varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'jdbc-url',
    `username`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '用户名',
    `password`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '密码',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      DEFAULT '0' COMMENT '删除标记',
    `create_by`           varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                DEFAULT 0 COMMENT '进行中',
    `action`              varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='数据源表';

-- ----------------------------
-- Records of lzhphantom_gen_datasource_conf
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for lzhphantom_gen_form_conf
-- ----------------------------
DROP TABLE IF EXISTS `lzhphantom_gen_form_conf`;
CREATE TABLE `lzhphantom_gen_form_conf`
(
    `id`          bigint NOT NULL,
    `table_name`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表名',
    `form_info`   json   NOT NULL COMMENT '表单信息',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     DEFAULT '0' COMMENT '删除标记',
    `create_by`           varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                DEFAULT 0 COMMENT '进行中',
    `action`              varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `table_name` (`table_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='表单配置';

-- ----------------------------
-- Records of lzhphantom_gen_form_conf
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
