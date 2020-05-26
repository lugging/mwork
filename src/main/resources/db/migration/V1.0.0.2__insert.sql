create table ytx_login
(
    user_id       bigint auto_increment comment '主键，自增'
        primary key,
    create_time   datetime         null comment '建立时间',
    update_time   datetime         null comment '更新时间'
);
