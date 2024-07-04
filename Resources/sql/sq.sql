show databases;
show tables;

use mini_board;
use mini_board_test;

select * from member;
select * from board_file;
select * from post;
select * from reply;
select path from board_file;

select * from member;
select * from member_role;
select * from role;

-- drop database mini_board;

drop table board_file;

drop table member;
drop table post;
drop table reply;
drop table role;
drop table member_role;

ALTER TABLE member AUTO_INCREMENT = 1;
ALTER TABLE member_role AUTO_INCREMENT = 1;



select count(*) 
from post
where content like "%content%" or title like "%content%";

select *
from member;

select * from post;
select * from member;
select * from reply;

select * from role;
select * from member_role;
select * from member;

