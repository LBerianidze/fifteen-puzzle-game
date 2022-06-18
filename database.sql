drop table if exists game;
drop table if exists player;


create table player(id int auto_increment primary key, name nvarchar(64), registerdate datetime default now());
create table game(id int auto_increment primary key, user_id int, start_date datetime, end_date datetime, moves_count int);