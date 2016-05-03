package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.UsersDao;
import com.school.cbis.domain.tables.pojos.Users;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by lenovo on 2016-01-05.
 */
@Service("usersService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

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
        if (principal instanceof MyUserImpl) {
            username = StringUtils.trimWhitespace(((MyUserImpl) principal).getUsername());
        }
        return username;
    }

    @Override
    public String getPassword() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = null;
        if (principal instanceof MyUserImpl) {
            password = StringUtils.trimWhitespace(((MyUserImpl) principal).getPassword());
        }
        return password;
    }

    @Override
    public Integer getUserTypeId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userTypeId = 0;
        if (principal instanceof MyUserImpl) {
            userTypeId = (((MyUserImpl) principal).getUserTypeId());
        }
        return userTypeId;
    }

    @Override
    public Users findByUsername(String username) {
        Users users = usersDao.fetchOneByUsername(username);
        return users;
    }

    @Override
    public boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_USER")) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getAuthorities().contains(new SimpleGrantedAuthority(authority));
            }
        }
        return false;
    }

    @Override
    public void update(Users users) {
        usersDao.update(users);
    }

    @Override
    public Record findAll(String username) {
        //获取用户类型
        Integer userTypeId = getUserTypeId();

        if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER) == userTypeId) {//教师类型
            Record record = create.select()
                    .from(Tables.TEACHER)
                    .join(Tables.TIE)
                    .on(Tables.TEACHER.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.YARD)
                    .on(Tables.TIE.YARD_ID.equal(Tables.YARD.ID))
                    .join(Tables.USERS)
                    .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                    .where(Tables.TEACHER.TEACHER_JOB_NUMBER.equal(username))
                    .fetchOne();
            return record;
        } else if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == userTypeId) {//学生类型
            Record record = create.select()
                    .from(Tables.STUDENT)
                    .join(Tables.GRADE)
                    .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                    .join(Tables.MAJOR)
                    .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                    .join(Tables.TIE)
                    .on(Tables.MAJOR.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.YARD)
                    .on(Tables.TIE.YARD_ID.equal(Tables.YARD.ID))
                    .join(Tables.USERS)
                    .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                    .where(Tables.STUDENT.STUDENT_NUMBER.equal(username))
                    .fetchOne();
            return record;
        }

        return null;
    }

    @Override
    public void save(Users users) {
        usersDao.insert(users);
    }
}
