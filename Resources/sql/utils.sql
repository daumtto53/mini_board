show databases;

use mini_board;
use mini_board_test;

-- desc
desc post;
desc member;
desc reply;

-- WARNING DROP
drop table member, member_seq, post, post_seq, reply, reply_seq;
drop table reply, post;
-- WARNING DROP

select * from member;
select * from post;
select * from reply;