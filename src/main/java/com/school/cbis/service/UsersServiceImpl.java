package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.UsersDao;
import com.school.cbis.domain.tables.pojos.Users;
import com.school.cbis.domain.tables.records.UsersRecord;
import com.school.cbis.vo.article.UsersArticleVo;
import org.jooq.*;
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
import java.util.List;

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
    public Users getUserInfoBySession() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = null;
        if (principal instanceof MyUserImpl) {
            users = (((MyUserImpl) principal).getUsers());
        }
        return users;
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
        log.debug(" catch =========================================== catch ");
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
    public void reloadUserAllInfo() {
        /*
        这里清空缓存使用
         */
    }

    @Override
    public void save(Users users) {
        usersDao.insert(users);
    }

    @Override
    public UsersRecord findByEmailAndUsername(String email, String username) {
        Byte b = 1;
        UsersRecord record = create.selectFrom(Tables.USERS)
                .where(Tables.USERS.USERNAME.ne(username).and(Tables.USERS.EMAIL.eq(email)).and(Tables.USERS.IS_CHECK_EMAIL.eq(b)))
                .fetchOne();
        return record;
    }

    @Override
    public UsersRecord findByMobileAndUsername(String mobile, String username) {
        Byte b = 1;
        UsersRecord record = create.selectFrom(Tables.USERS)
                .where(Tables.USERS.USERNAME.ne(username).and(Tables.USERS.MOBILE.eq(mobile)).and(Tables.USERS.IS_CHECK_MOBILE.eq(b)))
                .fetchOne();
        return record;
    }

    @Override
    public Result<Record4<String ,String ,String ,Integer>> findByUserTypeIdAndTieIdWithArticle(UsersArticleVo usersArticleVo,int userTypeId, int tieId) {
        Condition a = Tables.USERS.USER_TYPE_ID.eq(userTypeId).and(Tables.TIE.ID.eq(tieId));
        if(StringUtils.hasLength(usersArticleVo.getUsername())){
            a = a.and(Tables.USERS.USERNAME.like("%"+usersArticleVo.getUsername()+"%"));
        }

        if(StringUtils.hasLength(usersArticleVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+usersArticleVo.getRealName()+"%"));
        }

        if(StringUtils.hasLength(usersArticleVo.getBigTitle())){
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%"+usersArticleVo.getBigTitle()+"%"));
        }

        int pageNum = usersArticleVo.getPageNum();
        int pageSize = usersArticleVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }
        if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER) == userTypeId) {//教师类型
            Result<Record4<String ,String ,String,Integer >> record = create.select(Tables.USERS.USERNAME,Tables.USERS.REAL_NAME,Tables.ARTICLE_INFO.BIG_TITLE,
                    Tables.ARTICLE_INFO.ID)
                    .from(Tables.TEACHER)
                    .join(Tables.TIE)
                    .on(Tables.TEACHER.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.USERS)
                    .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                    .leftJoin(Tables.ARTICLE_INFO)
                    .on(Tables.USERS.INTRODUCE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                    .where(a)
                    .limit((pageNum-1)*pageSize,pageSize)
                    .fetch();
            return record;
        } else if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == userTypeId) {//学生类型
            Result<Record4<String ,String ,String,Integer >> record = create.select(Tables.USERS.USERNAME,Tables.USERS.REAL_NAME,Tables.ARTICLE_INFO.BIG_TITLE,
                    Tables.ARTICLE_INFO.ID)
                    .from(Tables.STUDENT)
                    .join(Tables.GRADE)
                    .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                    .join(Tables.MAJOR)
                    .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                    .join(Tables.TIE)
                    .on(Tables.MAJOR.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.USERS)
                    .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                    .leftJoin(Tables.ARTICLE_INFO)
                    .on(Tables.USERS.INTRODUCE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                    .where(a)
                    .limit((pageNum-1)*pageSize,pageSize)
                    .fetch();
            return record;
        }
        return null;
    }

    @Override
    public int findByUserTypeIdAndTieIdWithArticleCount(UsersArticleVo usersArticleVo, int userTypeId, int tieId) {
        Condition a = Tables.USERS.USER_TYPE_ID.eq(userTypeId).and(Tables.TIE.ID.eq(tieId));
        if(StringUtils.hasLength(usersArticleVo.getUsername())){
            a = a.and(Tables.USERS.USERNAME.like("%"+usersArticleVo.getUsername()+"%"));
        }

        if(StringUtils.hasLength(usersArticleVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+usersArticleVo.getRealName()+"%"));
        }

        if(StringUtils.hasLength(usersArticleVo.getBigTitle())){
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%"+usersArticleVo.getBigTitle()+"%"));
        }

        if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER) == userTypeId) {//教师类型
            Record1<Integer> record1 = create.selectCount()
                    .from(Tables.TEACHER)
                    .join(Tables.TIE)
                    .on(Tables.TEACHER.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.USERS)
                    .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                    .leftJoin(Tables.ARTICLE_INFO)
                    .on(Tables.USERS.INTRODUCE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                    .where(a)
                    .fetchOne();
            return record1.value1();
        } else if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == userTypeId) {//学生类型
            Record1<Integer> record1 = create.selectCount()
                    .from(Tables.STUDENT)
                    .join(Tables.GRADE)
                    .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                    .join(Tables.MAJOR)
                    .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                    .join(Tables.TIE)
                    .on(Tables.MAJOR.TIE_ID.equal(Tables.TIE.ID))
                    .join(Tables.USERS)
                    .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                    .leftJoin(Tables.ARTICLE_INFO)
                    .on(Tables.USERS.INTRODUCE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                    .where(a)
                    .fetchOne();
            return record1.value1();
        }
        return 0;
    }

}
