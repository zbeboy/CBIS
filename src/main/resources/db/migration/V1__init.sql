create table user_type(
  id int not null primary key auto_increment,
  name varchar(50) not null
);

create table users(
  username varchar(64) not null primary key,
  password varchar(500) not null,
  enabled boolean not null,
  user_type_id int not null,
  real_name varchar(30) comment '真实姓名',
  mobile varchar(15)  comment '手机',
  email varchar(500)  comment '邮箱',
  birthday date comment '生日',
  head_img varchar(500) comment '头像' ,
  sex varchar(2) comment '性别' ,
  identity_card varchar(20) comment '身份证号',
  family_residence varchar(600) comment '家庭居住地' ,
  post varchar(100) comment '职务',
  political_landscape varchar(10) comment '政治面貌' ,
  religious_belief varchar(500) comment '宗教信仰',
  nation varchar(200) comment '民族',
  is_check_mobile boolean not null default false comment '已验证手机',
  is_check_email boolean not null default false  comment '已验证邮箱',
  mobile_check_key varchar(20) comment '手机验证码',
  email_check_key varchar(20) comment '邮箱验证码',
  password_reset_key varchar(20) comment '密码重置key',
  mobile_check_key_validity_period datetime  comment '手机验证有效期',
  email_check_key_validity_period datetime comment '邮箱验证有效期',
  password_reset_key__validity_period datetime comment '密码重置key有效期',
  lang_key varchar(5) comment 'message source',
  persona_introduction varchar(200) comment '个人介绍',
  foreign key(user_type_id) references user_type(id)
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

create table article_type(
  id int not null primary key auto_increment,
  name varchar(100) not null
);

create table article_info(
  id int not null primary key auto_increment,
  big_title varchar(50),
  article_writer varchar(64) not null,
  date timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  article_type_id int not null,
  article_content varchar(2000),
  article_photo_url varchar(500),
  foreign key(article_writer) references users(username),
  foreign key(article_type_id) references article_type(id)
);

create table article_sub(
  id int not null primary key auto_increment,
  sub_title varchar(50),
  sub_content varchar(1000),
  article_info_id int not null,
  foreign key(article_info_id) references article_info(id)
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
  tie_principal_article_info_id int,
  tie_introduce_article_info_id int,
  tie_training_goal_article_info_id int,
  tie_trait_article_info_id int,
  yard_id int not null,
  foreign key(yard_id) references yard(id)
);

create table major(
  id int not null primary key auto_increment,
  tie_id int not null,
  major_name varchar(150) not null,
  major_introduce_article_info_id int,
  major_training_goal_article_info_id int,
  major_trait_article_info_id int,
  major_foregoer_article_info_id int,
  is_show boolean default 0,
  foreign key(tie_id) references tie(id)
);

create table grade(
  id int not null primary key auto_increment,
  major_id int not null,
  year varchar(20) not null,
  grade_name varchar(70) not null,
  grade_head varchar(50) not null,
  foreign key(major_id) references major(id),
  foreign key(grade_head) references users(username)
);

create table student(
  id int not null primary key auto_increment,
  student_number varchar(25) not null,
  grade_id int not null,
  dormitory_number varchar(50) comment '宿舍号',
  parent_name varchar(10) comment '家长姓名',
  parent_contact_phone varchar(15) comment '家长联系电话',
  place_origin varchar(500) comment '生源地',
  problem_situation varchar(500) comment '问题情况',
  student_introduce_article_info_id int,
  foreign key(grade_id) references grade(id)
);

create table student_poor(
  id int not null primary key auto_increment,
  title varchar(200) comment '是每年系统自动生成一条记录?(如:2015-2016学年贫困程度)',
  content varchar(100),
  student_id int not null,
  foreign key(student_id) references student(id)
);

create table teacher(
  id int not null primary key auto_increment,
  tie_id int not null,
  teacher_job_number varchar(25) not null,
  teacher_introduce_article_info_id int,
  foreign key(tie_id) references tie(id)
);

create table tie_elegant_time(
  id int not null primary key auto_increment,
  time varchar(10) not null
);

create table tie_elegant(
  id int not null primary key auto_increment,
  tie_id int not null,
  tie_elegant_article_info_id int,
  tie_elegant_time_id int not null,
  is_show boolean default 0,
  foreign key(tie_id) references tie(id)
);

create table tie_notice_time(
  id int not null primary key auto_increment,
  time varchar(10) not null
);

create table tie_notice(
  id int not null primary key auto_increment,
  tie_id int not null,
  tie_notice_article_info_id int,
  tie_notice_time_id int not null,
  is_show boolean default 0,
  foreign key(tie_id) references tie(id)
);

create table tie_notice_affix(
  id int not null primary key auto_increment,
  tie_notice_file_url varchar(500) not null,
  tie_notice_file_size varchar(50),
  tie_notice_file_name varchar(30) not null,
  tie_notice_file_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  article_info_id int not null,
  file_user varchar(64) not null,
  file_type varchar(15) not null,
  foreign key(file_user) references users(username)
);

create table system_inform(
  id int not null primary key auto_increment,
  system_inform varchar(1000),
  system_inform_show int
);

create table bring_in(
  id int not null primary key auto_increment,
  release_article_article_info_id int,
  HR_email varchar(100),
  tie_id int not null,
  foreign key(tie_id) references tie(id)
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
  teach_task_file_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  teach_task_term varchar(20) not null,
  teach_task_down_times int ,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not null,
  file_user varchar(64) not null,
  file_type varchar(15),
  foreign key(tie_id) references tie(id),
  foreign key(teach_type_id) references teach_type(id),
  foreign key(file_user) references users(username)
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
  four_items_file_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  file_user varchar(64) not null,
  file_type varchar(15),
  foreign key(teach_task_info_id) references teach_task_info(id),
  foreign key(four_items_type_id) references four_items_type(id),
  foreign key(file_user) references users(username)
);

create table place_file_info(
  id int not null primary key auto_increment,
  teach_task_info_id int not null,
  place_file_info_url varchar(500) not null,
  place_file_info_size varchar(50),
  place_file_info_name varchar(30) not null,
  place_file_info_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  file_user varchar(64) not null,
  file_type varchar(15),
  foreign key (teach_task_info_id) references teach_task_info(id),
  foreign key(file_user) references users(username)
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
  teach_course_info_file_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  teach_course_info_file_down_times int,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not null,
  file_user varchar(64) not null,
  file_type varchar(15),
  foreign key (tie_id) references tie(id),
  foreign key (teach_type_id) references teach_type(id),
  foreign key (file_user) references users(username)
);

create table student_course_timetable_info(
  id int not null primary key auto_increment,
  grade_id int not null,
  timetable_info_term varchar(20) not null,
  timetable_info_file_url varchar(500) not null,
  timetable_info_file_pdf varchar(500) ,
  timetable_info_file_size varchar(50),
  timetable_info_file_name varchar(30) not null,
  timetable_info_file_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  timetable_info_file_down_times int ,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not null,
  file_user varchar(64) not null,
  file_type varchar(15),
  foreign key(grade_id) references grade(id),
  foreign key(teach_type_id) references teach_type(id),
  foreign key(file_user) references users(username)
);

create table teacher_course_timetable_info(
  id int not null primary key auto_increment,
  tie_id int not null,
  timetable_info_term varchar(20) not null,
  timetable_info_file_url varchar(500) not null,
  timetable_info_file_pdf varchar(500),
  timetable_info_file_name varchar(30) not null,
  timetable_info_file_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  timetable_info_file_down_times int,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not  null,
  file_user varchar(64) not null,
  file_type varchar(15),
  foreign key(tie_id) references tie(id),
  foreign key(teach_type_id) references teach_type(id),
  foreign key(file_user) references users(username)
);

create table classroom_course_timetable_info(
  id int not null primary key auto_increment,
  tie_id int not null,
  timetable_info_term varchar(20) not null,
  timetable_info_file_url varchar(500) not null,
  timetable_info_file_pdf varchar(500),
  timetable_info_file_size varchar(50),
  timetable_info_file_name varchar(30) not null,
  timetable_info_file_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  timetable_info_file_down_times int,
  teach_type_id int not null,
  term_start_time date not null,
  term_end_time date not null,
  file_user varchar(64) not null,
  file_type varchar(15),
  foreign key(tie_id) references tie(id),
  foreign key(teach_type_id) references teach_type(id),
  foreign key(file_user) references users(username)
);

create table autonomous_practice_template(
  id int not null primary key auto_increment,
  autonomous_practice_template_title varchar(50) not null,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  users_id varchar(64) not null,
  tie_id int not null,
  foreign key(tie_id) references tie(id),
  foreign key(users_id) references users(username)
);

create table autonomous_practice_info(
  id int not null primary key auto_increment,
  autonomous_practice_title varchar(100) not null,
  create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  grade_year varchar(20) not null,
  autonomous_practice_template_id int,
  start_time datetime not null,
  end_time datetime not null,
  users_id varchar(64) not null,
  tie_id int not null,
  foreign key(users_id) references users(username),
  foreign key(tie_id) references tie(id),
  foreign key(autonomous_practice_template_id) references autonomous_practice_template(id)
);

create table head_type(
  id int not null primary key auto_increment,
  type_value varchar(30) not null,
  type_name varchar(30) not null
);

create table autonomous_practice_head(
  id int not null primary key auto_increment,
  title varchar(50) not null,
  title_variable varchar(50) not null,
  database_table varchar(100),
  database_table_field varchar(100),
  authority varchar(2000) not null,
  is_show_highly_active boolean not null default true,
  is_database boolean not null default false,
  is_required boolean not null default true,
  content varchar(2000),
  sort int not null,
  head_type_id int,
  autonomous_practice_template_id int not null,
  foreign key(autonomous_practice_template_id) references autonomous_practice_template(id),
  foreign key(head_type_id) references head_type(id)
);

create table autonomous_practice_content(
  id int not null primary key auto_increment,
  content varchar(500),
  autonomous_practice_head_id int not null,
  student_id int not null,
  autonomous_practice_info_id int not null,
  foreign key(autonomous_practice_head_id) references autonomous_practice_head(id),
  foreign key(student_id) references student(id),
  foreign key(autonomous_practice_info_id) references autonomous_practice_info(id)
);

insert into user_type(name) values('学生');
insert into user_type(name) values('教师');

insert into users(username, password, enabled, user_type_id, real_name, mobile, email, birthday, head_img, sex, identity_card, family_residence, post, political_landscape, religious_belief, nation, is_check_mobile, is_check_email, mobile_check_key, email_check_key, password_reset_key, mobile_check_key_validity_period, email_check_key_validity_period, password_reset_key__validity_period,lang_key, persona_introduction)
values('10000','e10adc3949ba59abbe56e057f20f883e',true,2,'zbeboy','13987614709','863052317@qq.com','1994-01-07',null,'男','530181199401073015','昆明市','普通学生','预备党员','无','汉',true,true,null,null,null,null,null,null,'zh_cn','系统管理员');
insert into authorities values('10000','ROLE_ADMIN');

insert into article_type(name) values('系简介');
insert into article_type(name) values('系培养目标');
insert into article_type(name) values('系特色');
insert into article_type(name) values('专业简介');
insert into article_type(name) values('专业培养目标');
insert into article_type(name) values('专业特色');
insert into article_type(name) values('专业带头人');
insert into article_type(name) values('学生简介');
insert into article_type(name) values('教师简介');
insert into article_type(name) values('系风采');
insert into article_type(name) values('系公告');
insert into article_type(name) values('系主任');

insert into article_info(id,article_writer,article_type_id) values(1,'10000',1);

insert into yard(yard_name,yard_address) values('城市学院','云南省昆明盘龙区新迎校区');

insert into tie(tie_name,yard_id) values('信息工程系',1);

insert into major(tie_id,major_name) values(1,'计算机科学与技术');

insert into teacher(tie_id,teacher_job_number) values(1,'10000');

insert into grade(major_id,year,grade_name,grade_head) values(1,'2012','计科1211','10000');

insert into teach_type(name) values('理论');
insert into teach_type(name) values('实践');

insert into four_items_type(name) values('大纲');
insert into four_items_type(name) values('计划');
insert into four_items_type(name) values('日程');
insert into four_items_type(name) values('ppt');

insert into head_type(type_value, type_name) values('text','文本');
insert into head_type(type_value, type_name) values('textarea','多文本');
insert into head_type(type_value, type_name) values('select','单选');
insert into head_type(type_value, type_name) values('checkbox','复选');
insert into head_type(type_value, type_name) values('switch','开关');
insert into head_type(type_value, type_name) values('date','日期');
insert into head_type(type_value, type_name) values('time','时间');
insert into head_type(type_value, type_name) values('email','邮箱');
insert into head_type(type_value, type_name) values('number','数字');
insert into head_type(type_value, type_name) values('mobile','手机');
insert into head_type(type_value, type_name) values('telephone','电话');
insert into head_type(type_value, type_name) values('postcode','邮政编码');
insert into head_type(type_value, type_name) values('qq','腾讯qq');
insert into head_type(type_value, type_name) values('ID_card','身份证号');
insert into head_type(type_value, type_name) values('database','数据库字段');