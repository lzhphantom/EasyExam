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
    del_flag              varchar(2)            DEFAULT '0' COMMENT '是否删除 -1：已删除 0：正常',
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
    del_flag              varchar(1)          DEFAULT '0' COMMENT '删除标记,1:已删除,0:正常',
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
    del_flag              varchar(255)        DEFAULT '0' COMMENT '删除标记,1:已删除,0:正常',
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
    `del_flag`            varchar(255)          DEFAULT '0' COMMENT '0--正常 1--删除',
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

BEGIN;
insert into lzhphantom_menu(menu_id, name, permission, path, parent_id, icon, sort_order, keep_alive, type, del_flag,
                            create_by, status, is_saga_in_progress, action)
values ('1000', '权限管理', null, '/admin', '-1', 'icon-quanxianguanli', '1', '0', '0', '0', 'SYSTEM', 'Approved', 0,
        'Approved'),
       ('1101', '用户新增', 'sys_user_add', null, '1100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1102', '用户修改', 'sys_user_edit', null, '1100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1103', '用户删除', 'sys_user_del', null, '1100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1100', '用户管理', null, '/admin/user/index', '1000', 'icon-yonghuguanli', '0', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('1104', '导入导出', 'sys_user_import_export', null, '1100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1200', '菜单管理', null, '/admin/menu/index', '1000', 'icon-caidanguanli', '1', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('1201', '菜单新增', 'sys_menu_add', null, '1200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1202', '菜单修改', 'sys_menu_edit', null, '1200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1203', '菜单删除', 'sys_menu_del', null, '1200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1300', '角色管理', null, '/admin/role/index', '1000', 'icon-jiaoseguanli', '2', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('1301', '角色新增', 'sys_role_add', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1302', '角色修改', 'sys_role_edit', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1303', '角色删除', 'sys_role_del', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1304', '分配权限', 'sys_role_perm', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1305', '导入导出', 'sys_role_import_export', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1400', '部门管理', null, '/admin/dept/index', '1000', 'icon-web-icon-', '3', '0', '0', '0', 'SYSTEM',
        'Approved',
        0, 'APPROVED'),
       ('1401', '部门新增', 'sys_dept_add', null, '1400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1402', '部门修改', 'sys_dept_edit', null, '1400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1403', '部门删除', 'sys_dept_del', null, '1400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1500', '岗位管理', '', '/admin/post/index', '1000', 'icon-gangweiguanli', '4', '0', '0', '0', 'SYSTEM',
        'Approved',
        0, 'APPROVED'),
       ('1501', '岗位查看', 'sys_post_get', null, '1500', '1', '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1502', '岗位新增', 'sys_post_add', null, '1500', '1', '1', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1503', '岗位修改', 'sys_post_edit', null, '1500', '1', '2', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1504', '岗位删除', 'sys_post_del', null, '1500', '1', '3', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1505', '导入导出', 'sys_post_import_export', null, '1500', null, '4', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2000', '系统管理', null, '/setting', '-1', 'icon-wxbgongju1', '2', '0', '0', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2100', '日志管理', null, '/admin/log/index', '2000', 'icon-rizhiguanli', '3', '0', '0', '0', 'SYSTEM',
        'Approved',
        0, 'APPROVED'),
       ('2101', '日志删除', 'sys_log_del', null, '2100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2102', '导入导出', 'sys_log_import_export', null, '2100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2200', '字典管理', null, '/admin/dict/index', '2000', 'icon-tubiaozhizuomoban-27', '2', '0', '0', '0',
        'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('2201', '字典删除', 'sys_dict_del', null, '2200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2202', '字典新增', 'sys_dict_add', null, '2200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2203', '字典修改', 'sys_dict_edit', null, '2200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2300', '令牌管理', null, '/admin/token/index', '2000', 'icon-lingpaiguanli', '4', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('2301', '令牌删除', 'sys_token_del', null, '2300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2400', '终端管理', '', '/admin/client/index', '2000', 'icon-shouji', '0', '0', '0', '0', 'SYSTEM', 'Approved',
        0,
        'APPROVED'),
       ('2401', '客户端新增', 'sys_client_add', null, '2400', '1', '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2402', '客户端修改', 'sys_client_edit', null, '2400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2403', '客户端删除', 'sys_client_del', null, '2400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2600', '文件管理', null, '/admin/file/index', '2000', 'icon-wenjianjiawenjianguanli', '1', '0', '0', '0',
        'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('2601', '文件删除', 'sys_file_del', null, '2600', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2602', '文件新增', 'sys_file_add', null, '2600', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2603', '文件修改', 'sys_file_edit', null, '2600', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2700', '参数管理', null, '/admin/param/index', '2000', 'icon-canshu', '5', '0', '0', '0', 'SYSTEM', 'Approved',
        0,
        'APPROVED'),
       ('2701', '参数新增', 'sys_publicparam_add', null, '2700', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2702', '参数删除', 'sys_publicparam_del', null, '2700', null, '1', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2703', '参数修改', 'sys_publicparam_edit', null, '2700', null, '3', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('3000', '开发平台', null, '/gen', '-1', 'icon-keshihuapingtaiicon_zujian', '3', '1', '0', '0', 'SYSTEM',
        'Approved',
        0, 'APPROVED'),
       ('3100', '数据源管理', null, '/gen/datasource', '3000', 'icon-shujuyuan', '3', '1', '0', '0', 'SYSTEM',
        'Approved', 0,
        'APPROVED'),
       ('3200', '代码生成', null, '/gen/index', '3000', 'icon-didaima', '0', '0', '0', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('3300', '表单管理', null, '/gen/form', '3000', 'icon-biaodanguanli', '1', '1', '0', '0', 'SYSTEM', 'Approved',
        0,
        'APPROVED'),
       ('3301', '表单新增', 'gen_form_add', null, '3300', '', '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('3302', '表单修改', 'gen_form_edit', null, '3300', '', '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('3303', '表单删除', 'gen_form_del', null, '3300', '', '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('3400', '表单设计', null, '/gen/design', '3000', 'icon-sheji', '2', '1', '0', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('4000', '服务监控', null, 'http://localhost:5001/login', '-1', 'icon-iconset0265', '4', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('9999', '系统官网', null, 'https://pig4cloud.com/#/', '-1', 'icon-web-icon-', '999', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED');
COMMIT;

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

BEGIN;
insert into lzhphantom_oauth_client_details(client_id, resource_ids, scope,
                                            authorized_grant_types, web_server_redirect_uri,
                                            auto_approve, create_by, status, is_saga_in_progress, action)
values ('app', 'app', 'server', 'app,refresh_token', null, true, 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('daemon', 'daemon', 'server', 'password,refresh_token', null, true, 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('gen', 'gen', 'server', 'password,refresh_token', null, true, 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('lzhphantom', 'lzhphantom', 'server', 'password,app,refresh_token,authorization_code,client_credentials',
        'https://pigx.vip', true, 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('test', 'test', 'server', 'password,app,refresh_token', null, true, 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('client', 'client', 'server', 'client_credentials', null, true, 'SYSTEM', 'Approved', 0, 'APPROVED');
commit;

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

BEGIN;
INSERT INTO `lzhphantom_post`(post_id, post_code, post_name, post_sort, del_flag,
                              create_by, status, is_saga_in_progress, action, remark)
VALUES (1, 'user', '员工', 2, '0', 'SYSTEM', 'Approved', 0, 'APPROVED', '打工人'),
       (2, 'cto', 'cto', 0, '0', 'SYSTEM', 'Approved', 0, 'APPROVED', 'cto666'),
       (3, 'lzhphantom', '董事长', -1, '0', 'SYSTEM', 'Approved', 0, 'APPROVED', '大boss');
COMMIT;

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

BEGIN;
INSERT INTO `lzhphantom_role`(role_id, role_name, role_code, role_desc, del_flag, create_by, status,
                              is_saga_in_progress, action)
VALUES (1, '管理员', 'ROLE_ADMIN', '管理员', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       (2, '普通用户', 'GENERAL_USER', '普通用户', '0', 'SYSTEM', 'Approved', 0, 'APPROVED');
COMMIT;

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
    `del_flag`            varchar(1)            DEFAULT '0' COMMENT '删除标记',
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

BEGIN;
INSERT INTO `lzhphantom_user`(user_id, username, password, salt, phone, avatar, dept_id, lock_flag, del_flag,
                              create_by, status, is_saga_in_progress, action)
VALUES (1, 'admin', '$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC', NULL, '17034642999', '', 1, '0', '0',
        'SYSTEM', 'Approved', 0, 'APPROVED');
COMMIT;

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

BEGIN;
INSERT INTO `lzhphantom_user_post` VALUES (1, 1);
COMMIT;

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

BEGIN;
insert into lzhphantom_role_menu
values (1, 1000),
       (1, 1100),
       (1, 1101),
       (1, 1102),
       (1, 1103),
       (1, 1104),
       (1, 1200),
       (1, 1201),
       (1, 1202),
       (1, 1203),
       (1, 1300),
       (1, 1301),
       (1, 1302),
       (1, 1303),
       (1, 1304),
       (1, 1305),
       (1, 1400),
       (1, 1401),
       (1, 1402),
       (1, 1403),
       (1, 1500),
       (1, 1501),
       (1, 1502),
       (1, 1503),
       (1, 1504),
       (1, 1505),
       (1, 2000),
       (1, 2100),
       (1, 2101),
       (1, 2102),
       (1, 2200),
       (1, 2201),
       (1, 2202),
       (1, 2203),
       (1, 2300),
       (1, 2301),
       (1, 2400),
       (1, 2401),
       (1, 2402),
       (1, 2403),
       (1, 2600),
       (1, 2601),
       (1, 2602),
       (1, 2603),
       (1, 2700),
       (1, 2701),
       (1, 2702),
       (1, 2703),
       (1, 3000),
       (1, 3100),
       (1, 3200),
       (1, 3300),
       (1, 3301),
       (1, 3302),
       (1, 3303),
       (1, 3400),
       (1, 4000),
       (1, 9999),
       (2, 4000),
       (2, 9999);
COMMIT;

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

BEGIN;
INSERT INTO `lzhphantom_user_role` VALUES (1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;