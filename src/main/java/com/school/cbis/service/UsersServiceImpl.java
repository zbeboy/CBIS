package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.UsersDao;
import com.school.cbis.domain.tables.pojos.Users;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
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

    private UsersDao usersDao;

    @Autowired
    public UsersServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.usersDao = new UsersDao(configuration);
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
    public Users findByUsername(String username) {
        Users users = usersDao.fetchOneByUsername(username);
        return users;
    }

    @Override
    public void update(Users users) {
        usersDao.update(users);
    }

    @Override
    public Result<Record> findAll(String username) {
        //获取用户类型
        Users users = findByUsername(username);

        if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER) == users.getUserTypeId()) {//教师类型
            Result<Record> records = create.select()
                    .from(Tables.TEACHER)
                    .join(Tables.TIE)
                    .on(Tables.TEACHER.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.YARD)
                    .on(Tables.TIE.YARD_ID.equal(Tables.YARD.ID))
                    .where(Tables.TEACHER.TEACHER_JOB_NUMBER.equal(username))
                    .fetch();
            return records;
        } else if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == users.getUserTypeId()) {//学生类型
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
