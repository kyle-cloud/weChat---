use wechat;
############################user
insert into user (email, password, nickname, address, sex, age, qrcode)
	values('16281229@bjtu.edu.cn', '16281229', 'kyle', 'beijing', 1, 20, 'qrcode1');

insert into user (email, password, nickname, address, sex, age, qrcode)
	values('16281220@bjtu.edu.cn', '16281220', 'mail', 'beijing', 1, 21, 'qrcode12');

insert into user (email, password, nickname, address, sex, age, qrcode)
	values('16281221@bjtu.edu.cn', '16281221', 'ciel', 'shanghai', 1, 22, 'qrcode3');
	
insert into user (email, password, nickname, address, sex, age, qrcode)
	values('16281222@bjtu.edu.cn', '16281222', 'piel', 'shanghai', 1, 22, 'qrcode4');
	
insert into user (email, password, nickname, address, sex, age, qrcode)
	values('16281223@bjtu.edu.cn', '16281223', 'viol', 'beijing', 0, 23, 'qrcode5');


#############################contacts
insert into contacts (userid, friendid, notename, label)
	values(1, 2, 'ciel', 'friend');

	
insert into contacts (userid, friendid, notename, label)
	values(1, 3, 'ciel', 'friend');
	
	
insert into contacts (userid, friendid, notename, label)
	values(2, 3, 'ciel', 'friend');
	
	
insert into contacts (userid, friendid, notename, label)
	values(2, 4, 'ciel', 'friend');
	
	
insert into contacts (userid, friendid, notename, label)
	values(4, 5, 'ciel', 'friend');
	

##############################commonnews
insert into commonnews (userid, contactid, content, sendtime)
	values(1, 2, 'aaaaaaaa', '1xxx2');

insert into commonnews (userid, contactid, content, sendtime)
	values(2, 1, 'bbbbbbbb', '1xxx2');
	
insert into commonnews (userid, contactid, content, sendtime)
	values(1, 3, 'cccccccc', '1xxx2');
	
insert into commonnews (userid, contactid, content, sendtime)
	values(1, 3, 'ddddddddd', '1xxx2');
	
insert into commonnews (userid, contactid, content, sendtime)
	values(2, 3, 'eeeeeeeeeee', '1xxx2');
	
insert into commonnews (userid, contactid, content, sendtime)
	values(4, 5, 'fffffffffff', '1xxx2');
	
insert into commonnews (userid, contactid, content, sendtime)
	values(5, 3, 'gggggggggg', '1xxx2');
	
###############################chatgroup
insert into chatgroup (groupname, qrcode, adminid)
	values('group1', 'gcode1', 1);
	
insert into chatgroup (groupname, qrcode, adminid)
	values('group2', 'gcode2', 1);
	
insert into chatgroup (groupname, qrcode, adminid)
	values('group3', 'gcode3', 2);
	
###############################usercommunity
insert into usercommunity (userid, groupid) values(1, 1);

insert into usercommunity (userid, groupid) values(2, 1);

insert into usercommunity (userid, groupid) values(3, 1);

insert into usercommunity (userid, groupid) values(4, 1);

insert into usercommunity (userid, groupid) values(5, 1);

insert into usercommunity (userid, groupid) values(2, 2);

insert into usercommunity (userid, groupid) values(3, 2);

insert into usercommunity (userid, groupid) values(5, 2);

insert into usercommunity (userid, groupid) values(3, 3);

insert into usercommunity (userid, groupid) values(4, 3);

##########################################groupnews
insert into groupnews (groupid, senderid, sendtime, content)
	values(1, 1, '1xxx1', 'qweqwe');

insert into groupnews (groupid, senderid, sendtime, content)
	values(1, 1, '1xxx1', 'qasdasd');
	
insert into groupnews (groupid, senderid, sendtime, content)
	values(1, 3, '1xxx1', 'zxczxc');
	
insert into groupnews (groupid, senderid, sendtime, content)
	values(1, 4, '1xxx1', 'rtyrty');
	
insert into groupnews (groupid, senderid, sendtime, content)
	values(2, 2, '1xxx1', 'dfgdfg');
	
insert into groupnews (groupid, senderid, sendtime, content)
	values(2, 5, '1xxx1', 'cvbcvb');
	
insert into groupnews (groupid, senderid, sendtime, content)
	values(3, 3, '1xxx1', 'ghjghj');
	
insert into groupnews (groupid, senderid, sendtime, content)
	values(3, 4, '1xxx1', 'yuiyui');

	
	
	
	
	
	