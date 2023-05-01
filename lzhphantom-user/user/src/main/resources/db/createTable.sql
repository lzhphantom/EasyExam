# drop database if exists lzhphantom_exam;
# create database lzhphantom_exam default character set utf8mb4 collate utf8mb4_general_ci;
set names utf8mb4;
set foreign_key_checks = 0;

drop table if exists lzhphantom_dept;
CREATE TABLE lzhphantom_dept
(
    dept_id               bigint(20)   NOT NULL,
    name                  varchar(255) NOT NULL COMMENT '部门名称',
    sort_order            int(11)      NOT NULL COMMENT '排序值',
    parent_id             bigint(20)            DEFAULT NULL COMMENT '父级部门id',
    del_flag              varchar(2)            DEFAULT NULL COMMENT '是否删除 -1：已删除 0：正常',
    `create_by`           varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (dept_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci COMMENT ='部门表';

drop table if exists lzhphantom_dict;
CREATE TABLE lzhphantom_dict
(
    id                    bigint(20) NOT NULL COMMENT '字典编号',
    dict_key              varchar(255)        DEFAULT NULL COMMENT '字典key',
    description           varchar(255)        DEFAULT NULL COMMENT '字典描述',
    system_flag           varchar(255)        DEFAULT NULL COMMENT '是否系统内置',
    remark                varchar(255)        DEFAULT NULL COMMENT '备注信息',
    del_flag              varchar(1)          DEFAULT NULL COMMENT '删除标记,1:已删除,0:正常',
    `create_by`           varchar(255)        DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)        DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp  NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)        DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)        DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)              DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)        DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (id) using btree,
    key lzhphantom_dict_del_flag (del_flag) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci COMMENT ='字典表';

drop table if exists lzhphantom_dict_item;
CREATE TABLE lzhphantom_dict_item
(
    id                    bigint(20) NOT NULL COMMENT '字典项id',
    dict_id               bigint(20)          DEFAULT NULL COMMENT '所属字典类id',
    dict_key              varchar(255)        DEFAULT NULL COMMENT '所属字典类key',
    value                 varchar(255)        DEFAULT NULL COMMENT '数据值',
    label                 varchar(255)        DEFAULT NULL COMMENT '标签名',
    type                  varchar(255)        DEFAULT NULL COMMENT '类型',
    description           varchar(255)        DEFAULT NULL COMMENT '描述',
    sort_order            int(11)             DEFAULT NULL COMMENT '排序值，默认升序',
    remark                varchar(255)        DEFAULT NULL COMMENT '备注信息',
    del_flag              varchar(255)        DEFAULT NULL COMMENT '删除标记,1:已删除,0:正常',
    `create_by`           varchar(255)        DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)        DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp  NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)        DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)        DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)              DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)        DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (id) using btree,
    KEY `lzhphantom_dict_value` (`value`) USING BTREE,
    KEY `lzhphantom_dict_label` (`label`) USING BTREE,
    KEY `lzhphantom_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci COMMENT ='字典项';

drop table if exists lzhphantom_file_manage;
CREATE TABLE lzhphantom_file_manage
(
    id                    BIGINT(20) UNSIGNED NOT NULL COMMENT '编号',
    file_name             VARCHAR(255)                 DEFAULT NULL COMMENT '文件名',
    original              VARCHAR(255)                 DEFAULT NULL COMMENT '原文件名',
    bucket_name           VARCHAR(255)                 DEFAULT NULL COMMENT '容器名称',
    type                  VARCHAR(50)                  DEFAULT NULL COMMENT '文件类型',
    file_size             BIGINT(20)                   DEFAULT NULL COMMENT '文件大小',
    del_flag              TINYINT(1)          NOT NULL DEFAULT '0' COMMENT '删除标识：1-删除，0-正常',
    `create_by`           varchar(255)                 DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)                 DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp           NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)                 DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)                 DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                       DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)                 DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (id) using btree
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='文件管理';

drop table if exists lzhphantom_menu;
CREATE TABLE `lzhphantom_menu`
(
    `menu_id`             bigint(20)   NOT NULL COMMENT '菜单id',
    `name`                varchar(255) NOT NULL COMMENT '菜单名称',
    `permission`          varchar(255)          DEFAULT NULL COMMENT '菜单权限标识',
    `parent_id`           bigint(20)   NOT NULL COMMENT '菜单父id',
    `icon`                varchar(255)          DEFAULT NULL COMMENT '菜单图标',
    `path`                varchar(255)          DEFAULT NULL COMMENT '前端路由标识路径',
    `sort_order`          int(11)               DEFAULT NULL COMMENT '排序值',
    `type`                varchar(255) NOT NULL COMMENT '菜单类型 （0菜单 1按钮）',
    `keep_alive`          varchar(255)          DEFAULT NULL COMMENT '路由缓冲',
    `del_flag`            varchar(255)          DEFAULT NULL COMMENT '0--正常 1--删除',
    `create_by`           varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci
    COMMENT ='菜单';

drop table if exists lzhphantom_oauth_client_details;
CREATE TABLE lzhphantom_oauth_client_details
(
    client_id               VARCHAR(255) NOT NULL,
    client_secret           VARCHAR(255) NOT NULL,
    resource_ids            VARCHAR(255),
    scope                   VARCHAR(255) NOT NULL,
    authorized_grant_types  VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities             VARCHAR(255),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    auto_approve            VARCHAR(255),
    `create_by`             varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`             varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`           timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`             varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`                varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress`   bit(1)                DEFAULT NULL COMMENT '进行中',
    `action`                varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (client_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci COMMENT ='终端信息表';

drop table if exists lzhphantom_post;
CREATE TABLE `lzhphantom_post`
(
    `post_id`             bigint(20)  NOT NULL,
    `post_code`           varchar(64)          DEFAULT NULL COMMENT '岗位编码',
    `post_name`           varchar(50) NOT NULL COMMENT '岗位名称',
    `post_sort`           int(11)              DEFAULT NULL COMMENT '岗位排序',
    `del_flag`            varchar(2)  NOT NULL DEFAULT '0' COMMENT '是否删除（0：正常；-1：删除）',
    `remark`              varchar(500)         DEFAULT NULL COMMENT '备注',
    `create_by`           varchar(255)         DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)         DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp   NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)         DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)         DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)               DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)         DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`post_id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci COMMENT ='岗位信息表';

drop table if exists lzhphantom_public_param;
CREATE TABLE `lzhphantom_public_param`
(
    `public_id`           bigint(20)   NOT NULL COMMENT '公共参数编号',
    `public_name`         varchar(255) NOT NULL COMMENT '公共参数名称',
    `public_key`          varchar(255) NOT NULL COMMENT '键[英文大写+下划线]',
    `public_value`        varchar(255) NOT NULL COMMENT '值',
    `flag`                varchar(255)          DEFAULT NULL COMMENT '标识[1有效；2无效]',
    `validate_code`       varchar(255)          DEFAULT NULL COMMENT '编码',
    `system_flag`         varchar(255)          DEFAULT NULL COMMENT '是否是系统内置',
    `public_type`         varchar(255)          DEFAULT NULL COMMENT '类型[1-检索；2-原文...]',
    `create_by`           varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`public_id`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='公共参数';

drop table if exists lzhphantom_role;
CREATE TABLE `lzhphantom_role`
(
    `role_id`             bigint(20)   NOT NULL COMMENT '角色编号',
    `role_name`           varchar(255) NOT NULL COMMENT '角色名称',
    `role_code`           varchar(255) NOT NULL COMMENT '角色标识',
    `role_desc`           varchar(255) NOT NULL COMMENT '角色描述',
    `del_flag`            tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
    `create_by`           varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `lzhphantom_role_code` (`role_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci COMMENT ='角色表';

drop table if exists lzhphantom_system_log;
CREATE TABLE `lzhphantom_system_log`
(
    `id`                  bigint(20)   NOT NULL COMMENT '日志编号',
    `type`                varchar(255) NOT NULL COMMENT '日志类型（0-正常 9-错误）',
    `title`               varchar(255) NOT NULL COMMENT '日志标题',
    `remote_addr`         varchar(255)          DEFAULT NULL COMMENT '操作ip地址',
    `user_agent`          varchar(255)          DEFAULT NULL COMMENT '用户代理',
    `request_uri`         varchar(255)          DEFAULT NULL COMMENT '请求uri',
    `method`              varchar(255)          DEFAULT NULL COMMENT '操作方式',
    `params`              varchar(255)          DEFAULT NULL COMMENT '数据',
    `time`                bigint(20)            DEFAULT NULL COMMENT '方法执行时间',
    `exception`           varchar(255)          DEFAULT NULL COMMENT '异常信息',
    `service_id`          varchar(255)          DEFAULT NULL COMMENT '应用标识',
    `del_flag`            varchar(1)            DEFAULT NULL COMMENT '删除标记',
    `create_by`           varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`id`),
    KEY `lzhphantom_log_create_by` (`create_by`),
    KEY `lzhphantom_log_request_uri` (`request_uri`),
    KEY `lzhphantom_log_type` (`type`),
    KEY `lzhphantom_log_create_date` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci COMMENT ='系统日志';

drop table if exists lzhphantom_user;
CREATE TABLE `lzhphantom_user`
(
    `user_id`             bigint(20) NOT NULL COMMENT '主键id',
    `username`            varchar(50)         DEFAULT NULL COMMENT '用户名',
    `password`            varchar(50)         DEFAULT NULL COMMENT '密码',
    `salt`                varchar(50)         DEFAULT NULL COMMENT '随机盐',
    `lock_flag`           varchar(2)          DEFAULT NULL COMMENT '锁定标记',
    `phone`               varchar(20)         DEFAULT NULL COMMENT '手机号',
    `avatar`              varchar(255)        DEFAULT NULL COMMENT '头像地址',
    `dept_id`             bigint(20)          DEFAULT NULL COMMENT '用户所属部门id',
    `del_flag`            varchar(1)          DEFAULT NULL COMMENT '0-正常，1-删除',
    `create_by`           varchar(255)        DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)        DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp  NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)        DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)        DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)              DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)        DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`user_id`),
    KEY `lzhphantom_username` (`username`),
    unique key `lzhphantom_phone` (phone)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci COMMENT ='用户表';

drop table if exists lzhphantom_dept_relation;
create table lzhphantom_dept_relation
(
    ancestor   bigint not null,
    descendant bigint not null,
    primary key (ancestor, descendant),
    key idx1 (ancestor),
    key idx2 (descendant)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  collate utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='部门关系表';

DROP TABLE IF EXISTS `lzhphantom_user_post`;
CREATE TABLE `lzhphantom_user_post`
(
    `user_id` bigint(0) NOT NULL COMMENT '用户ID',
    `post_id` bigint(0) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT = '用户与岗位关联表';

DROP TABLE IF EXISTS `lzhphantom_role_menu`;
CREATE TABLE `lzhphantom_role_menu`
(
    `role_id` bigint NOT NULL,
    `menu_id` bigint NOT NULL,
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色菜单表';

DROP TABLE IF EXISTS `lzhphantom_user_role`;
CREATE TABLE `lzhphantom_user_role`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户角色表';

SET FOREIGN_KEY_CHECKS = 1;