#drop user 'U1'@'localhost';
#drop user 'U2'@'localhost';
#drop user 'U3'@'localhost';
#drop user 'U4'@'localhost';
#drop user 'U5'@'localhost';
#drop user 'U6'@'localhost';
#drop user 'U7'@'localhost';
#drop user 'U8'@'localhost';
#drop user 'U9'@'localhost';
#drop user 'U10'@'localhost';
#drop role 'R1'@'localhost';
#drop role 'R2'@'localhost';

####### 为不同的用户授予和收回表级SELECT, INSERT, UPDATE, DELETE, ALL PRIVILEGES权限
Revoke select on table chatgroup from 'U1'@'localhost';
Revoke insert on table chatgroup from 'U2'@'localhost';
Revoke update on table chatgroup from 'U3'@'localhost';
Revoke delete on table chatgroup from 'U4'@'localhost';

####列级
Revoke select(groupname) on table chatgroup from 'U6'@'localhost';
Revoke insert(groupname, qrcode, adminid) on table chatgroup from 'U7'@'localhost';
Revoke update(groupname) on table chatgroup from 'U8'@'localhost';

###收回角色
Revoke 'R1'@'localhost' from 'U9'@'localhost';
Revoke 'R2'@'localhost' from 'U10'@'localhost';