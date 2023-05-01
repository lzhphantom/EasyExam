drop table if exists lzhphantom_post;
create table lzhphantom_post
(
    `post_id`             bigint(0)                                               NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `post_code`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '岗位编码',
    `post_name`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '岗位名称',
    `post_sort`           int(0)                                                  NOT NULL COMMENT '岗位排序',
    `del_flag`            char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
    `remark`              varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '备注信息',
    `create_by`           varchar(255)                                                     DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)                                                     DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp                                               NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)                                                     DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp                                               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)                                                     DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                                                           DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)                                                     DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT = '岗位信息表';
BEGIN;
INSERT INTO `lzhphantom_post`(post_id, post_code, post_name, post_sort, del_flag,
                              create_by, status, is_saga_in_progress, action, remark)
VALUES (1, 'user', '员工', 2, '0', 'SYSTEM', 'Approved', 0, 'APPROVED', '打工人'),
       (2, 'cto', 'cto', 0, '0', 'SYSTEM', 'Approved', 0, 'APPROVED', 'cto666'),
       (3, 'lzhphantom', '董事长', -1, '0', 'SYSTEM', 'Approved', 0, 'APPROVED', '大boss');
COMMIT;

drop table if exists lzhphantom_user_post;
create table lzhphantom_user_post
(
    `user_id` bigint(0) NOT NULL COMMENT '用户ID',
    `post_id` bigint(0) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT = '用户与岗位关联表';
BEGIN;
INSERT INTO `lzhphantom_user_post` VALUES (1, 1);
COMMIT;