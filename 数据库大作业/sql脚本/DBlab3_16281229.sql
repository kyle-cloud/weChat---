create database wechat;
use wechat;

create table user(
	id int(7) not null primary key auto_increment,
	email varchar(20) not null,
	password varchar(20) not null,
	nickname varchar(20) not null,
	address varchar(20) not null,
	sex int(7) not null,
	age int(7) not null,
	qrcode varchar(50) not null,
	photourl varchar(50) not null default 'xxx',
    constraint C1 check(sex between 0 and 1)
	);
    
create table test_log(
	id int(7) not null primary key auto_increment,
	content varchar(100)
    );
	
create table contacts(
	id int(7) not null primary key auto_increment,
	userid int(7) not null,
	friendid int(7) not null,
	notename varchar(20) not null default 'nickname',
	label varchar(20) not null default 'friend'	
	);
    
create table request(
	id int(7) not null primary key auto_increment,
    userid int(7) not null,
    requestid int(7) not null,
    agree int (7) not null default 0
    );
	
create table commonnews(
	id int(7) not null primary key auto_increment,
	userid int(7) not null,
	contactid int(7) not null,
	content varchar(50) not null,
	sendtime varchar(20) not null
	);
	
create table chatgroup(
	id int(7) not null primary key auto_increment,
	groupname varchar(20) not null default 'groupchat',
	qrcode varchar(50) not null unique,
	adminid int(7) not null
	);
	
create table usercommunity(
	userid int(7) not null,
	groupid int(7) not null
	);
	
create table groupnews(
	id int(7) not null primary key auto_increment,
	groupid int(7) not null,
	senderid int(7) not null,
	sendtime varchar(20) not null,
	content varchar(50) not null
	);
	
###行列子集视图
create view IS_Man
	as select id, nickname, email, sex
	from user
	where sex = 1
	with check option;

###带表达式的
create view User_view(nickname, birth, email, address)
	as select nickname, 2018-age, email, address
	from user;

###分组视图
create view Conta_num(userid, fri_num) 
	as select userid, count(friendid)
	from contacts group by userid;

CREATE UNIQUE INDEX user_email ON user(email);
CREATE UNIQUE INDEX group_identity ON chatgroup (id);
CREATE INDEX user_group ON usercommunity (userid,groupid);


	