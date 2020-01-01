--liquibase formatted sql

--changeset commonapi:init_data000001-1
insert into role (id, name, remark) values (1, 'admin', '管理员');
--rollback delete from role

--changeset commonapi:init_data000001-2
insert into manage_user (id, user_name, password, name, remark, role_id) values (1, 'admin', 'admin','管理员', '管理员权限', 1);
--rollback delete from manage_user;