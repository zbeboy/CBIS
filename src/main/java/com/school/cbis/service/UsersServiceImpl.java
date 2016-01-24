package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.StudentRecord;
import com.school.cbis.domain.tables.records.TeacherRecord;
import com.school.cbis.domain.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2016-01-05.
 */
@Service("usersService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final DSLContext create;

    @Resource
    private Wordbook wordbook;

    @Autowired
    public UsersServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public String getUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = StringUtils.trimWhitespace(((UserDetails) principal).getUsername());
        }
        return username;
    }

    @Override
    public String getPassword() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = null;
        if (principal instanceof UserDetails) {
            password = StringUtils.trimWhitespace(((UserDetails) principal).getPassword());
        }
        return password;
    }

    @Override
    public UsersRecord getUsersInfo(String username) {
        UsersRecord usersRecord = create.selectFrom(Tables.USERS).where(Tables.USERS.USERNAME.equal(username)).fetchAny();
        return usersRecord;
    }

    @Override
    public boolean updateUsers(UsersRecord usersRecord) {
        int count = create.update(Tables.USERS)
                .set(Tables.USERS.PASSWORD, usersRecord.getPassword())
                .set(Tables.USERS.ENABLED, usersRecord.getEnabled())
                .set(Tables.USERS.USER_TYPE_ID, usersRecord.getUserTypeId())
                .where(Tables.USERS.USERNAME.equal(usersRecord.getUsername())).execute();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Result<Record> getUsersInfoAll(String username) {
        //获取用户类型
        UsersRecord usersRecord = create.selectFrom(Tables.USERS).where(Tables.USERS.USERNAME.equal(username)).fetchAny();

        if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER) == usersRecord.getUserTypeId()) {//教师类型
            Result<Record> records = create.select()
                    .from(Tables.TEACHER)
                    .join(Tables.TIE)
                    .on(Tables.TEACHER.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.YARD)
                    .on(Tables.TIE.YARD_ID.equal(Tables.YARD.ID))
                    .where(Tables.TEACHER.TEACHER_JOB_NUMBER.equal(username))
                    .fetch();
            return records;
        } else if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == usersRecord.getUserTypeId()) {//学生类型
            Result<Record> records = create.select()
                    .from(Tables.STUDENT)
                    .join(Tables.GRADE)
                    .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                    .join(Tables.MAJOR)
                    .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                    .join(Tables.TIE)
                    .on(Tables.MAJOR.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.YARD)
                    .on(Tables.TIE.YARD_ID.equal(Tables.YARD.ID))
                    .where(Tables.STUDENT.STUDENT_NUMBER.equal(username))
                    .fetch();
            return records;
        }

        return null;
    }
}
