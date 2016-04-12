/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain;


import com.school.cbis.domain.tables.ArticleInfo;
import com.school.cbis.domain.tables.ArticleSub;
import com.school.cbis.domain.tables.ArticleType;
import com.school.cbis.domain.tables.Authorities;
import com.school.cbis.domain.tables.AutonomousPracticeContent;
import com.school.cbis.domain.tables.AutonomousPracticeHead;
import com.school.cbis.domain.tables.AutonomousPracticeInfo;
import com.school.cbis.domain.tables.AutonomousPracticeTemplate;
import com.school.cbis.domain.tables.BringIn;
import com.school.cbis.domain.tables.ClassroomCourseTimetableInfo;
import com.school.cbis.domain.tables.FourItems;
import com.school.cbis.domain.tables.FourItemsType;
import com.school.cbis.domain.tables.Grade;
import com.school.cbis.domain.tables.HeadType;
import com.school.cbis.domain.tables.Major;
import com.school.cbis.domain.tables.PersistentLogins;
import com.school.cbis.domain.tables.PlaceFileContent;
import com.school.cbis.domain.tables.PlaceFileInfo;
import com.school.cbis.domain.tables.PlaceFileTitle;
import com.school.cbis.domain.tables.SchemaVersion;
import com.school.cbis.domain.tables.Student;
import com.school.cbis.domain.tables.StudentCourseTimetableInfo;
import com.school.cbis.domain.tables.SystemInform;
import com.school.cbis.domain.tables.TeachCourseInfo;
import com.school.cbis.domain.tables.TeachTaskContent;
import com.school.cbis.domain.tables.TeachTaskInfo;
import com.school.cbis.domain.tables.TeachTaskTitle;
import com.school.cbis.domain.tables.TeachType;
import com.school.cbis.domain.tables.Teacher;
import com.school.cbis.domain.tables.TeacherCourseTimetableInfo;
import com.school.cbis.domain.tables.Tie;
import com.school.cbis.domain.tables.TieElegant;
import com.school.cbis.domain.tables.TieElegantTime;
import com.school.cbis.domain.tables.TieNotice;
import com.school.cbis.domain.tables.TieNoticeAffix;
import com.school.cbis.domain.tables.TieNoticeTime;
import com.school.cbis.domain.tables.UserType;
import com.school.cbis.domain.tables.Users;
import com.school.cbis.domain.tables.Yard;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in cbis
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

	/**
	 * The table cbis.article_info
	 */
	public static final ArticleInfo ARTICLE_INFO = com.school.cbis.domain.tables.ArticleInfo.ARTICLE_INFO;

	/**
	 * The table cbis.article_sub
	 */
	public static final ArticleSub ARTICLE_SUB = com.school.cbis.domain.tables.ArticleSub.ARTICLE_SUB;

	/**
	 * The table cbis.article_type
	 */
	public static final ArticleType ARTICLE_TYPE = com.school.cbis.domain.tables.ArticleType.ARTICLE_TYPE;

	/**
	 * The table cbis.authorities
	 */
	public static final Authorities AUTHORITIES = com.school.cbis.domain.tables.Authorities.AUTHORITIES;

	/**
	 * The table cbis.autonomous_practice_content
	 */
	public static final AutonomousPracticeContent AUTONOMOUS_PRACTICE_CONTENT = com.school.cbis.domain.tables.AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT;

	/**
	 * The table cbis.autonomous_practice_head
	 */
	public static final AutonomousPracticeHead AUTONOMOUS_PRACTICE_HEAD = com.school.cbis.domain.tables.AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD;

	/**
	 * The table cbis.autonomous_practice_info
	 */
	public static final AutonomousPracticeInfo AUTONOMOUS_PRACTICE_INFO = com.school.cbis.domain.tables.AutonomousPracticeInfo.AUTONOMOUS_PRACTICE_INFO;

	/**
	 * The table cbis.autonomous_practice_template
	 */
	public static final AutonomousPracticeTemplate AUTONOMOUS_PRACTICE_TEMPLATE = com.school.cbis.domain.tables.AutonomousPracticeTemplate.AUTONOMOUS_PRACTICE_TEMPLATE;

	/**
	 * The table cbis.bring_in
	 */
	public static final BringIn BRING_IN = com.school.cbis.domain.tables.BringIn.BRING_IN;

	/**
	 * The table cbis.classroom_course_timetable_info
	 */
	public static final ClassroomCourseTimetableInfo CLASSROOM_COURSE_TIMETABLE_INFO = com.school.cbis.domain.tables.ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO;

	/**
	 * The table cbis.four_items
	 */
	public static final FourItems FOUR_ITEMS = com.school.cbis.domain.tables.FourItems.FOUR_ITEMS;

	/**
	 * The table cbis.four_items_type
	 */
	public static final FourItemsType FOUR_ITEMS_TYPE = com.school.cbis.domain.tables.FourItemsType.FOUR_ITEMS_TYPE;

	/**
	 * The table cbis.grade
	 */
	public static final Grade GRADE = com.school.cbis.domain.tables.Grade.GRADE;

	/**
	 * The table cbis.head_type
	 */
	public static final HeadType HEAD_TYPE = com.school.cbis.domain.tables.HeadType.HEAD_TYPE;

	/**
	 * The table cbis.major
	 */
	public static final Major MAJOR = com.school.cbis.domain.tables.Major.MAJOR;

	/**
	 * The table cbis.persistent_logins
	 */
	public static final PersistentLogins PERSISTENT_LOGINS = com.school.cbis.domain.tables.PersistentLogins.PERSISTENT_LOGINS;

	/**
	 * The table cbis.place_file_content
	 */
	public static final PlaceFileContent PLACE_FILE_CONTENT = com.school.cbis.domain.tables.PlaceFileContent.PLACE_FILE_CONTENT;

	/**
	 * The table cbis.place_file_info
	 */
	public static final PlaceFileInfo PLACE_FILE_INFO = com.school.cbis.domain.tables.PlaceFileInfo.PLACE_FILE_INFO;

	/**
	 * The table cbis.place_file_title
	 */
	public static final PlaceFileTitle PLACE_FILE_TITLE = com.school.cbis.domain.tables.PlaceFileTitle.PLACE_FILE_TITLE;

	/**
	 * The table cbis.schema_version
	 */
	public static final SchemaVersion SCHEMA_VERSION = com.school.cbis.domain.tables.SchemaVersion.SCHEMA_VERSION;

	/**
	 * The table cbis.student
	 */
	public static final Student STUDENT = com.school.cbis.domain.tables.Student.STUDENT;

	/**
	 * The table cbis.student_course_timetable_info
	 */
	public static final StudentCourseTimetableInfo STUDENT_COURSE_TIMETABLE_INFO = com.school.cbis.domain.tables.StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO;

	/**
	 * The table cbis.system_inform
	 */
	public static final SystemInform SYSTEM_INFORM = com.school.cbis.domain.tables.SystemInform.SYSTEM_INFORM;

	/**
	 * The table cbis.teacher
	 */
	public static final Teacher TEACHER = com.school.cbis.domain.tables.Teacher.TEACHER;

	/**
	 * The table cbis.teacher_course_timetable_info
	 */
	public static final TeacherCourseTimetableInfo TEACHER_COURSE_TIMETABLE_INFO = com.school.cbis.domain.tables.TeacherCourseTimetableInfo.TEACHER_COURSE_TIMETABLE_INFO;

	/**
	 * The table cbis.teach_course_info
	 */
	public static final TeachCourseInfo TEACH_COURSE_INFO = com.school.cbis.domain.tables.TeachCourseInfo.TEACH_COURSE_INFO;

	/**
	 * The table cbis.teach_task_content
	 */
	public static final TeachTaskContent TEACH_TASK_CONTENT = com.school.cbis.domain.tables.TeachTaskContent.TEACH_TASK_CONTENT;

	/**
	 * The table cbis.teach_task_info
	 */
	public static final TeachTaskInfo TEACH_TASK_INFO = com.school.cbis.domain.tables.TeachTaskInfo.TEACH_TASK_INFO;

	/**
	 * The table cbis.teach_task_title
	 */
	public static final TeachTaskTitle TEACH_TASK_TITLE = com.school.cbis.domain.tables.TeachTaskTitle.TEACH_TASK_TITLE;

	/**
	 * The table cbis.teach_type
	 */
	public static final TeachType TEACH_TYPE = com.school.cbis.domain.tables.TeachType.TEACH_TYPE;

	/**
	 * The table cbis.tie
	 */
	public static final Tie TIE = com.school.cbis.domain.tables.Tie.TIE;

	/**
	 * The table cbis.tie_elegant
	 */
	public static final TieElegant TIE_ELEGANT = com.school.cbis.domain.tables.TieElegant.TIE_ELEGANT;

	/**
	 * The table cbis.tie_elegant_time
	 */
	public static final TieElegantTime TIE_ELEGANT_TIME = com.school.cbis.domain.tables.TieElegantTime.TIE_ELEGANT_TIME;

	/**
	 * The table cbis.tie_notice
	 */
	public static final TieNotice TIE_NOTICE = com.school.cbis.domain.tables.TieNotice.TIE_NOTICE;

	/**
	 * The table cbis.tie_notice_affix
	 */
	public static final TieNoticeAffix TIE_NOTICE_AFFIX = com.school.cbis.domain.tables.TieNoticeAffix.TIE_NOTICE_AFFIX;

	/**
	 * The table cbis.tie_notice_time
	 */
	public static final TieNoticeTime TIE_NOTICE_TIME = com.school.cbis.domain.tables.TieNoticeTime.TIE_NOTICE_TIME;

	/**
	 * The table cbis.users
	 */
	public static final Users USERS = com.school.cbis.domain.tables.Users.USERS;

	/**
	 * The table cbis.user_type
	 */
	public static final UserType USER_TYPE = com.school.cbis.domain.tables.UserType.USER_TYPE;

	/**
	 * The table cbis.yard
	 */
	public static final Yard YARD = com.school.cbis.domain.tables.Yard.YARD;
}
