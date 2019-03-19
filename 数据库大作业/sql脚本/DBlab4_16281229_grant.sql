#####创建用户
create user 'U1'@'localhost';
create user 'U2'@'localhost';
create user 'U3'@'localhost';
create user 'U4'@'localhost';
create user 'U5'@'localhost';
create user 'U6'@'localhost';
create user 'U7'@'localhost';
create user 'U8'@'localhost';
create user 'U9'@'localhost';
create user 'U10'@'localhost';

####### 为不同的用户授予和收回表级SELECT, INSERT, UPDATE, DELETE, ALL PRIVILEGES权限
Grant select on table chatgroup to 'U1'@'localhost';
Grant insert on table chatgroup to 'U2'@'localhost';
Grant update on table chatgroup to 'U3'@'localhost';
Grant delete on table chatgroup to 'U4'@'localhost';
Grant all privileges on table chatgroup to 'U5'@'localhost';

Grant select on table chatgroup to 'U9'@'localhost';
Grant select on table chatgroup to 'U10'@'localhost';
####列级
Grant select(groupname) on table chatgroup to 'U6'@'localhost';
Grant insert(groupname, qrcode, adminid) on table chatgroup to 'U7'@'localhost';
Grant update(groupname) on table chatgroup to 'U8'@'localhost';

########创建角色
create role 'R1'@'localhost';
create role 'R2'@'localhost';
######赋予角色
Grant insert on table chatgroup to 'R1'@'localhost';
Grant select on table chatgroup to 'R2'@'localhost';

Grant 'R1'@'localhost' to 'U9'@'localhost';
Grant 'R2'@'localhost' to 'U10'@'localhost';