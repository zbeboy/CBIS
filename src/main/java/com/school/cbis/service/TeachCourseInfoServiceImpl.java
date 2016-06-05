package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.SystemLogDao;
import com.school.cbis.domain.tables.daos.TeachCourseInfoDao;
import com.school.cbis.domain.tables.pojos.TeachCourseInfo;
import com.school.cbis.vo.eadmin.TeachingProcessListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-05.
 */
@Service("teachCourseInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachCourseInfoServiceImpl implements TeachCourseInfoService {

    private final Logger log = LoggerFactory.getLogger(TeachCourseInfoServiceImpl.class);

    private final DSLContext create;

    private TeachCourseInfoDao teachCourseInfoDao;

    @Autowired
    public TeachCourseInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachCourseInfoDao = new TeachCourseInfoDao(configuration);
    }

    @Override
    public Result<Record13<Integer, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date, String, String>>  findByTieIdAndPageWithUsers(TeachingProcessListVo teachingProcessListVo, int tieId) {
        Condition a  = Tables.TEACH_COURSE_INFO.TIE_ID.eq(tieId);

        if(StringUtils.hasLength(teachingProcessListVo.getTeachCourseInfoFileName())){
            a = a.and(Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_NAME.like("%"+teachingProcessListVo.getTeachCourseInfoFileName()+"%"));
        }

        if(StringUtils.hasLength(teachingProcessListVo.getTeachCourseInfoTerm())){
            a = a.and(Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_TERM.like("%"+teachingProcessListVo.getTeachCourseInfoTerm()+"%"));
        }

        int pageNum = teachingProcessListVo.getPageNum();
        int pageSize = teachingProcessListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record13<Integer, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date, String, String>> record13s = create.select(Tables.TEACH_COURSE_INFO.ID,Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_TERM,
                Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_URL,Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_PDF,
                Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_SIZE,Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_NAME,
                Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_DATE,Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_DOWN_TIMES,
                Tables.TEACH_COURSE_INFO.TEACH_TYPE_ID,Tables.TEACH_COURSE_INFO.TERM_START_TIME,Tables.TEACH_COURSE_INFO.TERM_END_TIME,
                Tables.USERS.REAL_NAME,Tables.TEACH_COURSE_INFO.FILE_TYPE)
                .from(Tables.TEACH_COURSE_INFO)
                .join(Tables.USERS)
                .on(Tables.TEACH_COURSE_INFO.FILE_USER.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_DATE.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return record13s;
    }

    @Override
    public int findByTieIdAndPageWithUsersCount(TeachingProcessListVo teachingProcessListVo, int tieId) {
        Condition a  = Tables.TEACH_COURSE_INFO.TIE_ID.eq(tieId);

        if(StringUtils.hasLength(teachingProcessListVo.getTeachCourseInfoFileName())){
            a = a.and(Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_NAME.like("%"+teachingProcessListVo.getTeachCourseInfoFileName()+"%"));
        }

        if(StringUtils.hasLength(teachingProcessListVo.getTeachCourseInfoTerm())){
            a = a.and(Tables.TEACH_COURSE_INFO.TEACH_COURSE_INFO_TERM.like("%"+teachingProcessListVo.getTeachCourseInfoTerm()+"%"));
        }

      Record1<Integer> record = create.selectCount()
                .from(Tables.TEACH_COURSE_INFO)
                .join(Tables.USERS)
                .on(Tables.TEACH_COURSE_INFO.FILE_USER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TeachCourseInfo teachCourseInfo) {
        teachCourseInfoDao.insert(teachCourseInfo);
    }

    @Override
    public TeachCourseInfo findById(int id) {
        TeachCourseInfo teachCourseInfo = teachCourseInfoDao.findById(id);
        return teachCourseInfo;
    }

    @Override
    public void update(TeachCourseInfo teachCourseInfo) {
        teachCourseInfoDao.update(teachCourseInfo);
    }

    @Override
    public void deleteById(int id) {
        teachCourseInfoDao.deleteById(id);
    }
}
