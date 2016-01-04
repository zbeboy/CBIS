create table users(
  username varchar(64) not null primary key,
  password varchar(500) not null,
  enabled boolean not null
);

create table authorities(
  username varchar(64) not null,
  authority varchar(50) not null,
  foreign key(username) references users(username)
);

create table persistent_logins(
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);

create table yard(
  id int not null primary key auto_increment,
  yard_name varchar(30) not null,
  yard_address varchar(200)
);

create table tie(
  id int not null primary key auto_increment,
  tie_name varchar(30) not null,
  tie_address varchar(200) ,
  tie_phone varchar(20),
  tie_principal varchar(20),
  tie_introduce text,
  tie_training_goal text,
  tie_trait text,
  yard_id int not null,
  foreign key(yard_id) references yard(id)
);

create table major(
  id int not null primary key auto_increment,
  tie_id int not null,
  major_name varchar(150) not null,
  major_introduce text,
  major_training_goal text,
  major_trait text,
  major_foregoer varchar(20),
  foreign key(tie_id) references tie(id)
);

create table grade(
  id int not null primary key auto_increment,
  major_id int not null,
  year varchar(20) not null,
  grade_name varchar(70) not null,
  grade_teacher varchar(20),
  foreign key(major_id) references major(id)
);

create table student(
  id int not null primary key auto_increment,
  student_number varchar(25) not null,
  student_name varchar(20) not null,
  grade_id int not null,
  student_phone varchar(15),
  student_email varchar(100),
  student_birthday date,
  student_head_photo varchar(800),
  student_sex varchar(2),
  student_identity_card varchar(20),
  student_address varchar(200),
  student_introduce text,
  foreign key(grade_id) references grade(id)
);

create table teacher(
  id int not null primary key auto_increment,
  tie_id int not null,
  teacher_job_number varchar(25) not null,
  teacher_name varchar(20) not null,
  teacher_phone varchar(15),
  teacher_email varchar(100),
  teacher_birthday date,
  teacher_head_photo varchar(800),
  teacher_sex varchar(2),
  teacher_introduce text,
  teacher_identity_card varchar(20),
  teacher_address varchar(200),
  foreign key(tie_id) references tie(id)
);

create table tie_elegant(
  id int not null primary key auto_increment,
  tie_id int not null,
  tie_elegant text,
  foreign key(tie_id) references tie(id)
);

create table tie_notice(
  id int not null primary key auto_increment,
  tie_id int not null,
  tie_notice text,
  foreign key(tie_id) references tie(id)
);

create table tie_notice_affix(
  id int not null primary key auto_increment,
  tie_notice_file_url varchar(500) not null,
  tie_notice_file_size varchar(50),
  tie_notice_file_name varchar(30) not null,
  tie_notice_file_date datetime,
  tie_notice_id int not null,
  foreign key(tie_notice_id) references tie_notice(id)
);

create table major_exam_trends(
  id int not null primary key auto_increment,
  major_id int not null,
  exam_title varchar(100) not null,
  exam_start_time datetime,
  exam_end_time datetime,
  exam_place varchar(200),
  exam_cotent varchar(600),
  exam_announcements varchar(500),
  foreign key(major_id) references major(id)
);

create table system_inform(
  id int not null primary key auto_increment,
  system_inform varchar(1000),
  system_inform_show int
);

create table bring_in(
  id int not null primary key auto_increment,
  release_article varchar(5000) not null,
  HR_email varchar(100)
);

create table teach_type(
  id int not null primary key auto_increment,
  name varchar(10) not null
);

create table teach_task_info(
  id int not null primary key auto_increment,
  tie_id int not null,
  teach_task_file_url varchar(500) not null,
  teach_task_file_size varchar(50) ,
  teach_task_file_name varchar(30) not null,
  teach_task_file_date datetime,
  teach_task_term varchar(20) not null,
  teach_task_down_times int ,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not null,
  foreign key(tie_id) references tie(id),
  foreign key(teach_type_id) references teach_type(id)
);

create table teach_task_title(
  id int not null primary key auto_increment,
  title varchar(150),
  title_x int not null,
  title_y int not null,
  title_lx int,
  title_ly int,
  title_font varchar(25),
  title_font_size varchar(25),
  title_font_color varchar(25),
  title_background varchar(25),
  title_is_big boolean,
  title_is_italic boolean,
  is_edit boolean,
  teach_task_info_id int not null,
  foreign key(teach_task_info_id) references teach_task_info(id)
);

create table teach_task_content(
  id int not null primary key auto_increment,
  teach_task_title_id int not null,
  content varchar(600),
  content_x int not null,
  content_y int not null,
  content_lx int,
  content_ly int,
  content_font varchar(25),
  content_font_size varchar(25),
  content_font_color varchar(25),
  content_font_background varchar(25),
  content_is_big boolean,
  content_is_italic boolean,
  is_edit boolean,
  foreign key(teach_task_title_id) references teach_task_title(id)
);

create table four_items_type(
  id int not null primary key auto_increment,
  name varchar(25) not null
);

create table four_items(
  id int not null primary key auto_increment,
  teach_task_info_id int not null,
  content_y int not null,
  four_items_type_id int not null,
  four_items_file_url varchar(500) not null,
  four_items_file_size varchar(50),
  four_items_file_name varchar(30) not null,
  four_items_file_date datetime,
  foreign key(teach_task_info_id) references teach_task_info(id),
  foreign key(four_items_type_id) references four_items_type(id)
);

create table place_file_info(
  id int not null primary key auto_increment,
  teach_task_info_id int not null,
  place_file_info_url varchar(500) not null,
  place_file_info_size varchar(50),
  place_file_info_name varchar(30) not null,
  place_file_info_date datetime,
  foreign key (teach_task_info_id) references teach_task_info(id)
);

create table place_file_title(
  id int not null primary key auto_increment,
  title varchar(150),
  title_x int not null,
  title_y int not null,
  title_lx int,
  title_ly int,
  title_font varchar(25),
  title_font_size varchar(25),
  title_font_color varchar(25),
  title_background varchar(25),
  title_is_big boolean,
  title_is_italic boolean,
  is_edit boolean,
  place_file_info_id int not null,
  foreign key(place_file_info_id) references place_file_info(id)
);

create table place_file_content(
  id int not null primary key auto_increment,
  place_file_title_id int not null,
  content varchar(600),
  content_x int not null,
  content_y int not null,
  content_lx int ,
  content_ly int,
  content_font varchar(25),
  content_font_size varchar(25),
  content_font_color varchar(25),
  content_background varchar(25),
  content_is_big boolean,
  content_is_italic boolean,
  is_edit boolean,
  foreign key(place_file_title_id) references place_file_title(id)
);

create table teach_course_info(
  id int not null primary key auto_increment,
  tie_id int not null,
  teach_course_info_term varchar(20) not null,
  teach_course_info_file_url varchar(500) not null,
  teach_course_info_file_pdf varchar(500),
  teach_course_info_file_size varchar(50),
  teach_course_info_file_name varchar(30) not null,
  teach_course_info_file_date datetime,
  teach_course_info_file_down_times int,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not null,
  foreign key (tie_id) references tie(id),
  foreign key (teach_type_id) references teach_type(id)
);

create table student_course_timetable_info(
  id int not null primary key auto_increment,
  grade_id int not null,
  timetable_info_term varchar(20) not null,
  timetable_info_file_url varchar(500) not null,
  timetable_info_file_pdf varchar(500) ,
  timetable_info_file_size varchar(50),
  timetable_info_file_name varchar(30) not null,
  timetable_info_file_date datetime,
  timetable_info_file_down_times int ,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not null,
  foreign key(grade_id) references grade(id),
  foreign key(teach_type_id) references teach_type(id)
);

create table teacher_course_timetable_info(
  id int not null primary key auto_increment,
  tie_id int not null,
  timetable_info_term varchar(20) not null,
  timetable_info_file_url varchar(500) not null,
  timetable_info_file_pdf varchar(500),
  timetable_info_file_name varchar(30) not null,
  timetable_info_file_date datetime,
  timetable_info_file_down_times int,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not  null,
  foreign key(tie_id) references tie(id),
  foreign key(teach_type_id) references teach_type(id)
);

create table classroom_course_timetable_info(
  id int not null primary key auto_increment,
  tie_id int not null,
  timetable_info_term varchar(20) not null,
  timetable_info_file_url varchar(500) not null,
  timetable_info_file_pdf varchar(500),
  timetable_info_file_size varchar(50),
  timetable_info_file_name varchar(30) not null,
  timetable_info_file_date datetime,
  timetable_info_file_down_times int,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not null,
  foreign key(tie_id) references tie(id),
  foreign key(teach_type_id) references teach_type(id)
);

insert into users values('superadmin','e10adc3949ba59abbe56e057f20f883e',true);
insert into authorities values('superadmin','ROLE_SUPER');

insert into teach_type(name) values('理论');
insert into teach_type(name) values('实践');

insert into four_items_type(name) values('大纲');
insert into four_items_type(name) values('计划');
insert into four_items_type(name) values('日程');
insert into four_items_type(name) values('ppt');