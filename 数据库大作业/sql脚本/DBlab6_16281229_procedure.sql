use wechat;

delimiter $$
create procedure select_user()
begin
	select * from user;
end$$

delimiter $$
create procedure update_user(in userid integer, in name varchar(20))
begin
	update user  set nickname = name
	where id = userid;
end$$

delimiter $$
create procedure delete_user(in userid integer)
begin
	delete from user
	where id = userid;
end$$

delimiter $$
create procedure insert_user()
begin
	insert into user (email, password, nickname, address, sex, age, qrcode)
	values('16281229@bjtu.edu.cn', '16281229', 'kyle', 'beijing', 1, 20, 'qrcode1');
end$$

create trigger tri_empAdd After insert on user for each row
	insert into test_log(content) values('one record has been inserted');
    
create trigger tri_empUpd After update on user for each row
	insert into test_log(content) values('one record has been modified');
    
create trigger tri_empDel After delete on user for each row
	insert into test_log(content) values('one record has been deleted');
    



