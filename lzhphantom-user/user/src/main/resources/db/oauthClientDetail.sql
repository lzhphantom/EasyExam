drop table if exists lzhphantom_oauth_client_details;
CREATE TABLE lzhphantom_oauth_client_details
(
    client_id               VARCHAR(255) NOT NULL COMMENT '客户端ID',
    client_secret           VARCHAR(255) COMMENT '资源列表',
    resource_ids            VARCHAR(255) COMMENT '客户端密钥',
    scope                   VARCHAR(255) NOT NULL COMMENT '域',
    authorized_grant_types  VARCHAR(255) COMMENT '认证类型',
    web_server_redirect_uri VARCHAR(255) COMMENT '重定向地址',
    authorities             VARCHAR(255) COMMENT '角色列表',
    access_token_validity   INTEGER COMMENT 'token 有效期',
    refresh_token_validity  INTEGER COMMENT '刷新令牌有效期',
    additional_information  VARCHAR(4096) COMMENT '令牌扩展字段JSON',
    auto_approve            VARCHAR(255) COMMENT '是否自动放行',
    `create_by`             varchar(255)          DEFAULT NULL COMMENT '创建人',
    `create_time`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_by`             varchar(255)          DEFAULT NULL COMMENT '删除人',
    `delete_time`           timestamp    NULL     DEFAULT NULL COMMENT '删除时间',
    `update_by`             varchar(255)          DEFAULT NULL COMMENT '更新人',
    `update_time`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`                varchar(255)          DEFAULT NULL COMMENT '状态',
    `is_saga_in_progress`   bit(1)                DEFAULT NULL COMMENT '进行中',
    `action`                varchar(255)          DEFAULT NULL COMMENT '行为',
    PRIMARY KEY (`client_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='终端信息表';

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