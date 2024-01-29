INSERT INTO users (username, email, password)
VALUES ('admin', 'admin@admin.com', '$2a$10$/kd80mD.upil3PIrEMCWeOxgyntcT/T.Nyc.iDGwX7MBEGP0rlxQe');

insert into users_roles (user_id, role_id)
values ((select user_id from users where username = 'admin'), (select role_id from roles where name = 'ROLE_ADMIN'));
