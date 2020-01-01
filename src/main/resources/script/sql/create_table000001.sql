--liquibase formatted sql

--changeset commonapi:create_table00001-1
create table if not exists `manage_user`(
	id int not null primary key auto_increment,
	user_name varchar(20) not null default '' comment '用户名',
	password varchar(256) not null default '' comment '密码',
	name varchar(20) not null default '' comment '姓名',
	role_id int not null default 0 comment '对应角色ID',
	salt varchar(128) not null default '' comment '盐',
	mobile varchar(15) not null default '' comment '手机号',
	email varchar(20) not null default '' comment '邮箱',
    remark varchar(256) not null default '' comment '备注',
	last_login_time timestamp not null default  CURRENT_TIMESTAMP comment '最近登录时间',
	last_login_ip varchar(50) not null default '' comment '最近登录IP',
	create_time timestamp not null default  CURRENT_TIMESTAMP comment '注册时间'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci comment '后台用户表';
--rollback drop table `manage_user`;

--changeset commonapi:create_table00001-2
create table if not exists `role`(
	id int not null primary key auto_increment,
	name varchar(20) not null default '' comment '角色名称',
    remark varchar(256) not null default '' comment '备注',
	create_time timestamp not null default  CURRENT_TIMESTAMP comment '创建时间'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci comment '角色表';
--rollback drop table `role`;


--changeset commonapi:create_table00001-3
create table if not exists `manage_resources`(
	id int not null primary key auto_increment,
	name varchar(20) not null default '' comment '资源名称',
	resource_url varchar(1024) not null default '' comment '资源对应的路径',
	parent_resource_id int not null default 0 comment '父资源对应的id，0为最高级资源',
	menu_name varchar(20) not null default '' comment '菜单名称',
    remark varchar(256) not null default '' comment '备注',
	create_time timestamp not null default  CURRENT_TIMESTAMP comment '创建时间'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci comment '用户可以访问的资源';
--rollback drop table `manage_resources`;

--changeset commonapi:create_table00001-4
create table if not exists `role_resource_rela`(
	id int not null primary key auto_increment,
	role_id int not null default 0 comment '角色id',
	resource_id int not null default 0 comment '资源id',
	create_time timestamp not null default  CURRENT_TIMESTAMP comment '创建时间'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci comment '资源角色关系表';
--rollback drop table `role_resource_rela`;

--changeset commonapi:create_table00001-5
create table if not exists `operation_log`(
	id int not null primary key auto_increment,
	username varchar(20) not null default '' comment '操作用户名',
	operation_path varchar(1024) null default '' comment '操作路径',
	operation_content varchar(1024) null default '' comment '操作内容',
	operation_param varchar(4096) not null default '' comment '操作参数',
	operation_time timestamp not null default  CURRENT_TIMESTAMP comment '操作时间'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci comment '操作日志';
--rollback drop table `operation_log`;

--changeset commonapi:create_table00001-6
alter table `role_resource_rela` add index `role_id_index`(`role_id`) USING BTREE;
--rollback alter table `role_resource_rela` drop index `role_id_index`;

--changeset commonapi:create_table00001-7
alter table `role_resource_rela` add index `resource_id_index`(`resource_id`) USING BTREE;
--rollback alter table `role_resource_rela` drop index `resource_id_index`;
