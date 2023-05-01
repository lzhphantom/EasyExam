drop table if exists lzhphantom_user;
create table lzhphantom_user
(
    `user_id`             bigint                                                  NOT NULL,
    `username`            varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    `password`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `salt`                varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '随机盐',
    `phone`               varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '简介',
    `avatar`              varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '头像',
    `dept_id`             bigint                                                           DEFAULT NULL COMMENT '部门ID',
    `lock_flag`           char(1) CHARACTER SET utf8 COLLATE utf8_general_ci               DEFAULT '0' COMMENT '0-正常，9-锁定',
    `del_flag`            char(1) CHARACTER SET utf8 COLLATE utf8_general_ci               DEFAULT '0' COMMENT '0-正常，1-删除',
    `create_by`           varchar(255)                                                     DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)                                                     DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp                                               NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)                                                     DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp                                               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)                                                     DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                                                           DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)                                                     DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`user_id`),
    KEY `user_idx1_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户表';

BEGIN;
INSERT INTO `lzhphantom_user`(user_id, username, password, salt, phone, avatar, dept_id, lock_flag, del_flag,
                              create_by, status, is_saga_in_progress, action)
VALUES (1, 'admin', '$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC', NULL, '17034642999', '', 1, '0', '0',
        'SYSTEM', 'Approved', 0, 'APPROVED');
COMMIT;