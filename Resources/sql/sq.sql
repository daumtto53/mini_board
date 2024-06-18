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

-- drop table board_file;
-- drop table board_file_seq;
-- drop table member;
-- drop table member_seq;
-- drop table post;
-- drop table post_seq;
-- drop table reply;
-- drop table reply_seq;
-- drop table role;
-- drop table member_role;

-- ALTER TABLE member AUTO_INCREMENT = 1;
-- ALTER TABLE member_role AUTO_INCREMENT = 1;