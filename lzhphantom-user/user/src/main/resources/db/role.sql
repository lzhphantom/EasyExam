drop table if exists lzhphantom_role;
create table lzhphantom_role
(
    `role_id`             bigint                                                 NOT NULL,
    `role_name`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `role_code`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `role_desc`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL,
    `del_flag`            char(1) CHARACTER SET utf8 COLLATE utf8_general_ci              DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
    `create_by`           varchar(255)                                                    DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)                                                    DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp                                              NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)                                                    DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp                                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)                                                    DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                                                          DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)                                                    DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `role_idx1_role_code` (`role_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统角色表';
BEGIN;
INSERT INTO `lzhphantom_role`(role_id, role_name, role_code, role_desc, del_flag, create_by, status,
                              is_saga_in_progress, action)
VALUES (1, '管理员', 'ROLE_ADMIN', '管理员', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       (2, '普通用户', 'GENERAL_USER', '普通用户', '0', 'SYSTEM', 'Approved', 0, 'APPROVED');
COMMIT;

drop table if exists lzhphantom_user_role;
create table lzhphantom_user_role
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户角色表';

BEGIN;
INSERT INTO `lzhphantom_user_role` VALUES (1, 1);
COMMIT;