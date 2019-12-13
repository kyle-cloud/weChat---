use wechat;

select * from user where id=1;

select contactid, content
	from commonnews where userid=1;

########1所在群聊2说的话
select groupid,content
	from groupnews
	where senderid=2 and
		groupid in(
		select groupid
		from usercommunity
		where userid=2);
		
		
		
		
		
########视图查询操作
select nickname, birth, email, address
	from user_view
	where birth <= 1996;

######行列子集
update is_man
	set nickname = 'LYP'
	where email = '16281229@bjtu.edu.cn';

delete from is_man where email = '16281222@bjtu.edu.cn';

#########带表达式
update user_view
	set nickname = 'LPP'
	where email = '16281220@bjtu.edu.cn';

delete from user_view where email = '16281221@bjtu.edu.cn';

############分组视图不可更改
update conta_num
	set fri_num = 4
	where uerid = 4;

delete from conta_num where userid = 4;#######不可删除




##########测试
select * from chatgroup;##U1
insert into chatgroup (groupname, qrcode, adminid) values('group4', 'gcode4', 2);###U1
insert into chatgroup (groupname, qrcode, adminid) values('group5', 'gcode5', 3);###U2

select * from chatgroup;###U6
select groupname from chatgroup;####U6

select * from chatgroup;###U9
insert into chatgroup (groupname, qrcode, adminid) values('group4', 'gcode4', 2);###U9

