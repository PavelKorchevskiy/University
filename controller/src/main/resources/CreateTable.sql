drop table Student;
create table Student
(
    id       int     not null,
    login    varchar not null,
    password varchar not null,
    name     varchar not null,
    age      int     not null,
    primary key (id)
);
insert into Student (id, login, password, name, age)
values (1, 's1', 's1', 'Lena', 24),
       (2, 's2', 's2', 'Dima', 27),
       (3, 's3', 's3', 'Alex', 21),
       (4, 's4', 's4', 'Kim', 29),
       (5, 's5', 's5', 'Anya', 23),
       (6, 's6', 's6', 'Alli', 31),
       (7, 's7', 's7', 'Pasha', 25),
       (8, 's8', 's8', 'Dasha', 24);
select *
from Student;

create table Teacher
(
    id       int     not null,
    login    varchar not null,
    password varchar not null,
    name     varchar not null,
    age      int     not null,
    salary   varchar,
    primary key (id)
);
insert into Teacher (id, login, password, name, age, salary)
values (1, 't1', 't1', 'Ivanov Ivan', 34, '345.45;367.56;378.01;398;412;432'),
       (2, 't2', 't2', 'Galileo Galilei', 64, '353.54;371.44;388.88;354.65;333;399'),
       (3, 't3', 't3', 'Albert Einstein', 54, '345.67;321.45;37.88;378;321;432;477'),
       (4, 't4', 't4', 'Master Yoda', 334, '452.33;455;432;471;499,476.54');
select *
from Teacher;

create table Groups
(
    id         int not null,
    teacher_id int not null,
    subjects   varchar,
    primary key (id),
    foreign key (teacher_id) references teacher (id)
);
insert into Groups (id, teacher_id, subjects)
values (1, 1, 'biology;chemistry'),
       (2, 2, 'math;geography'),
       (3, 3, 'art;history');
select *
from Groups;

create table Group_Student
(
    group_id   int not null,
    student_id int not null,
    foreign key (group_id) references groups (id),
    foreign key (student_id) references student (id)
);
insert into Group_Student (group_id, student_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 4),
       (2, 5),
       (2, 5),
       (3, 6),
       (3, 7),
       (3, 8);
select *
from Group_Student;

create table Rating
(
    student_id int     not null,
    subject    varchar not null,
    rating     int default 0,
    foreign key (student_id) references student (id)
);

update Teacher
set salary = '452.33;455;432;471;499;476.54'
where id = 4;
update rating
set rating = 50
where student_id = 1
  and subject = 'chemistry';
create table subject
(
    id   int     not null,
    name varchar not null,
    primary key (id)
);
insert into subject (id, name)
VALUES (1, 'biology'),
       (2, 'chemistry'),
       (3, 'math'),
       (4, 'geography'),
       (5, 'art'),
       (6, 'history');
alter table groups drop column subjects;
create table groups_subject
(
    group_id   int not null,
    subject_id int not null,
    foreign key (group_id) references groups (id),
    foreign key (subject_id) references subject (id)
);
insert into groups_subject (group_id, subject_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6);
drop table salary;
create table salary
(
    id         serial primary key not null,
    teacher_id int                not null,
    salary     real               not null,
    foreign key (teacher_id) references Teacher
);
create sequence salary_id_seq start 1 increment 1;
delete
from salary
where id = 1;
insert into salary (teacher_id, salary)
VALUES (3, 474.45),
       (3, 344.45),
       (3, 427.4),
       (3, 394.5),
       (3, 444.45),
       (4, 341.5),
       (4, 424.75),
       (4, 433.48),
       (4, 440.5),
       (4, 384),
       (4, 447.8);

alter table groups
    add column subjects varchar(50);
ALTER TABLE teacher ALTER COLUMN id TYPE serial;

create sequence teacher_id_seq start 1 increment 1;
create sequence student_id_seq start 1 increment 1;
create sequence groups_id_seq start 1 increment 1;

drop table groups_subject;
