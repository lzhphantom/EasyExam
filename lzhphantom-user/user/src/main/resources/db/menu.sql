drop table if exists lzhphantom_menu;
create table lzhphantom_menu
(
    `menu_id`             bigint                              NOT NULL,
    `name`                varchar(32) COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
    `permission`          varchar(32) COLLATE utf8_general_ci          DEFAULT NULL COMMENT '菜单权限标识',
    `path`                varchar(128) COLLATE utf8_general_ci         DEFAULT NULL COMMENT '前端URL',
    `parent_id`           bigint                                       DEFAULT NULL COMMENT '父菜单ID',
    `icon`                varchar(32) COLLATE utf8_general_ci          DEFAULT NULL COMMENT '图标',
    `sort_order`          int                                 NOT NULL DEFAULT '0' COMMENT '排序值',
    `keep_alive`          char(1) COLLATE utf8_general_ci              DEFAULT '0' COMMENT '0-开启，1- 关闭',
    `type`                char(1) COLLATE utf8_general_ci              DEFAULT NULL COMMENT '菜单类型 （0菜单 1按钮）',
    `del_flag`            char(1) COLLATE utf8_general_ci              DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
    `create_by`           varchar(255)                                 DEFAULT NULL COMMENT '创建人',
    `create_time`         timestamp                           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`           varchar(255)                                 DEFAULT NULL COMMENT '删除人',
    `delete_time`         timestamp                           NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`           varchar(255)                                 DEFAULT NULL COMMENT '更新人',
    `update_time`         timestamp                           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`              varchar(255)                                 DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress` bit(1)                                       DEFAULT NULL COMMENT '进行中',
    `action`              varchar(255)                                 DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='菜单权限表';

BEGIN;
insert into lzhphantom_menu(menu_id, name, permission, path, parent_id, icon, sort_order, keep_alive, type, del_flag,
                            create_by, status, is_saga_in_progress, action)
values ('1000', '权限管理', null, '/admin', '-1', 'icon-quanxianguanli', '1', '0', '0', '0', 'SYSTEM', 'Approved', 0,
        'Approved'),
       ('1101', '用户新增', 'sys_user_add', null, '1100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1102', '用户修改', 'sys_user_edit', null, '1100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1103', '用户删除', 'sys_user_del', null, '1100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1100', '用户管理', null, '/admin/user/index', '1000', 'icon-yonghuguanli', '0', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('1104', '导入导出', 'sys_user_import_export', null, '1100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1200', '菜单管理', null, '/admin/menu/index', '1000', 'icon-caidanguanli', '1', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('1201', '菜单新增', 'sys_menu_add', null, '1200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1202', '菜单修改', 'sys_menu_edit', null, '1200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1203', '菜单删除', 'sys_menu_del', null, '1200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1300', '角色管理', null, '/admin/role/index', '1000', 'icon-jiaoseguanli', '2', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('1301', '角色新增', 'sys_role_add', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1302', '角色修改', 'sys_role_edit', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1303', '角色删除', 'sys_role_del', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1304', '分配权限', 'sys_role_perm', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1305', '导入导出', 'sys_role_import_export', null, '1300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('1400', '部门管理', null, '/admin/dept/index', '1000', 'icon-web-icon-', '3', '0', '0', '0', 'SYSTEM', 'Approved',
        0, 'APPROVED'),
       ('1401', '部门新增', 'sys_dept_add', null, '1400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1402', '部门修改', 'sys_dept_edit', null, '1400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1403', '部门删除', 'sys_dept_del', null, '1400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1500', '岗位管理', '', '/admin/post/index', '1000', 'icon-gangweiguanli', '4', '0', '0', '0', 'SYSTEM', 'Approved',
        0, 'APPROVED'),
       ('1501', '岗位查看', 'sys_post_get', null, '1500', '1', '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1502', '岗位新增', 'sys_post_add', null, '1500', '1', '1', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1503', '岗位修改', 'sys_post_edit', null, '1500', '1', '2', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1504', '岗位删除', 'sys_post_del', null, '1500', '1', '3', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('1505', '导入导出', 'sys_post_import_export', null, '1500', null, '4', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2000', '系统管理', null, '/setting', '-1', 'icon-wxbgongju1', '2', '0', '0', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2100', '日志管理', null, '/admin/log/index', '2000', 'icon-rizhiguanli', '3', '0', '0', '0', 'SYSTEM', 'Approved',
        0, 'APPROVED'),
       ('2101', '日志删除', 'sys_log_del', null, '2100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2102', '导入导出', 'sys_log_import_export', null, '2100', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2200', '字典管理', null, '/admin/dict/index', '2000', 'icon-tubiaozhizuomoban-27', '2', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('2201', '字典删除', 'sys_dict_del', null, '2200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2202', '字典新增', 'sys_dict_add', null, '2200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2203', '字典修改', 'sys_dict_edit', null, '2200', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2300', '令牌管理', null, '/admin/token/index', '2000', 'icon-lingpaiguanli', '4', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('2301', '令牌删除', 'sys_token_del', null, '2300', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2400', '终端管理', '', '/admin/client/index', '2000', 'icon-shouji', '0', '0', '0', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2401', '客户端新增', 'sys_client_add', null, '2400', '1', '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2402', '客户端修改', 'sys_client_edit', null, '2400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2403', '客户端删除', 'sys_client_del', null, '2400', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2600', '文件管理', null, '/admin/file/index', '2000', 'icon-wenjianjiawenjianguanli', '1', '0', '0', '0', 'SYSTEM',
        'Approved', 0, 'APPROVED'),
       ('2601', '文件删除', 'sys_file_del', null, '2600', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2602', '文件新增', 'sys_file_add', null, '2600', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2603', '文件修改', 'sys_file_edit', null, '2600', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0, 'APPROVED'),
       ('2700', '参数管理', null, '/admin/param/index', '2000', 'icon-canshu', '5', '0', '0', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2701', '参数新增', 'sys_publicparam_add', null, '2700', null, '0', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2702', '参数删除', 'sys_publicparam_del', null, '2700', null, '1', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('2703', '参数修改', 'sys_publicparam_edit', null, '2700', null, '3', '0', '1', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('3000', '开发平台', null, '/gen', '-1', 'icon-keshihuapingtaiicon_zujian', '3', '1', '0', '0', 'SYSTEM', 'Approved',
        0, 'APPROVED'),
       ('3100', '数据源管理', null, '/gen/datasource', '3000', 'icon-shujuyuan', '3', '1', '0', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('3200', '代码生成', null, '/gen/index', '3000', 'icon-didaima', '0', '0', '0', '0', 'SYSTEM', 'Approved', 0,
        'APPROVED'),
       ('3300', '表单管理', null, '/gen/form', '3000', 'icon-biaodanguanli', '1', '1', '0', '0', 'SYSTEM', 'Approved', 0,
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

drop table if exists lzhphantom_role_menu;
create table lzhphantom_role_menu
(
    `role_id` bigint NOT NULL,
    `menu_id` bigint NOT NULL,
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色菜单表';
BEGIN;
 insert into lzhphantom_role_menu values (1, 1000),
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