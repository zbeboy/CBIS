package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeacherFillTaskInfoDao;
import com.school.cbis.domain.tables.pojos.TeacherFillTaskInfo;
import com.school.cbis.vo.eadmin.TeacherFillTaskInfoListVo;
import com.school.cbis.vo.eadmin.TeacherReportListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-02.
 */
@Service("teacherFillTaskInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeacherFillTaskInfoServiceImpl implements TeacherFillTaskInfoService {

    private final Logger log = LoggerFactory.getLogger(TeacherFillTaskInfoServiceImpl.class);

    private final DSLContext create;

    private TeacherFillTaskInfoDao teacherFillTaskInfoDao;

    @Autowired
    public TeacherFillTaskInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teacherFillTaskInfoDao = new TeacherFillTaskInfoDao(configuration);
    }

    @Override
    public Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> findByTieIdAndPage(TeacherFillTaskInfoListVo teacherFillTaskInfoListVo, int tieId) {
        Condition a = Tables.TEACHER_FILL_TASK_INFO.TIE_ID.eq(tieId);
        if(StringUtils.hasLength(teacherFillTaskInfoListVo.getTitle())){
            a = a.and(Tables.TEACHER_FILL_TASK_INFO.TITLE.like("%"+teacherFillTaskInfoListVo.getTitle()+"%"));
        }

        if(StringUtils.hasLength(teacherFillTaskInfoListVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+teacherFillTaskInfoListVo.getRealName()+"%"));
        }

        int pageNum = teacherFillTaskInfoListVo.getPageNum();
        int pageSize = teacherFillTaskInfoListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> record7s = create.select(Tables.TEACHER_FILL_TASK_INFO.ID,Tables.TEACHER_FILL_TASK_INFO.TITLE,
                Tables.TEACHER_FILL_TASK_INFO.CREATE_TIME,Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE.as("templateTitle"),
                Tables.TEACHER_FILL_TASK_INFO.START_TIME,Tables.TEACHER_FILL_TASK_INFO.END_TIME,
                Tables.USERS.REAL_NAME)
                .from(Tables.TEACHER_FILL_TASK_INFO)
                .leftJoin(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .on(Tables.TEACHER_FILL_TASK_INFO.TEACHER_FILL_TASK_TEMPLATE_ID.eq(Tables.TEACHER_FILL_TASK_TEMPLATE.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHER_FILL_TASK_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.TEACHER_FILL_TASK_INFO.CREATE_TIME.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return record7s;
    }

    @Override
    public int findByTieIdAndPageCount(TeacherFillTaskInfoListVo teacherFillTaskInfoListVo, int tieId) {
        Condition a = Tables.TEACHER_FILL_TASK_INFO.TIE_ID.eq(tieId);
        if(StringUtils.hasLength(teacherFillTaskInfoListVo.getTitle())){
            a = a.and(Tables.TEACHER_FILL_TASK_INFO.TITLE.like("%"+teacherFillTaskInfoListVo.getTitle()+"%"));
        }

        if(StringUtils.hasLength(teacherFillTaskInfoListVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+teacherFillTaskInfoListVo.getRealName()+"%"));
        }

       Record1<Integer> record1 = create.selectCount()
                .from(Tables.TEACHER_FILL_TASK_INFO)
                .leftJoin(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .on(Tables.TEACHER_FILL_TASK_INFO.TEACHER_FILL_TASK_TEMPLATE_ID.eq(Tables.TEACHER_FILL_TASK_TEMPLATE.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHER_FILL_TASK_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Override
    public void save(TeacherFillTaskInfo teacherFillTaskInfo) {
        teacherFillTaskInfoDao.insert(teacherFillTaskInfo);
    }

    @Override
    public TeacherFillTaskInfo findById(int id) {
        TeacherFillTaskInfo teacherFillTaskInfo = teacherFillTaskInfoDao.findById(id);
        return teacherFillTaskInfo;
    }

    @Override
    public void update(TeacherFillTaskInfo teacherFillTaskInfo) {
        teacherFillTaskInfoDao.update(teacherFillTaskInfo);
    }

    @Override
    public Result<Record9<Integer, Integer, Integer, String, Timestamp, Timestamp, String, String, String>> findAllAndPage(TeacherReportListVo teacherReportListVo,int tieId,int teachTypeId) {
        Condition a = Tables.TEACHER_FILL_TASK_INFO.TIE_ID.eq(tieId).and(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID.eq(teachTypeId));

        int pageNum = teacherReportListVo.getPageNum();
        int pageSize = teacherReportListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record9<Integer, Integer, Integer, String, Timestamp, Timestamp, String, String, String>> record9s = create.select(Tables.TEACHER_FILL_TASK_INFO.ID,Tables.TEACHER_FILL_TASK_TEMPLATE.ID.as("templateId"),
                Tables.TEACH_TASK_INFO.ID.as("taskInfoId"),Tables.TEACHER_FILL_TASK_INFO.TITLE,
                Tables.TEACHER_FILL_TASK_INFO.START_TIME,Tables.TEACHER_FILL_TASK_INFO.END_TIME,
                Tables.USERS.REAL_NAME,Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE.as("templateTitle"),
                Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE.as("teachTaskTitle"))
                .from(Tables.TEACHER_FILL_TASK_INFO)
                .leftJoin(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .on(Tables.TEACHER_FILL_TASK_INFO.TEACHER_FILL_TASK_TEMPLATE_ID.eq(Tables.TEACHER_FILL_TASK_TEMPLATE.ID))
                .leftJoin(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACHER_FILL_TASK_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHER_FILL_TASK_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.TEACHER_FILL_TASK_INFO.CREATE_TIME.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();

        return record9s;
    }

    @Override
    public int findAllAndPageCount(TeacherReportListVo teacherReportListVo,int tieId,int teachTypeId) {
        Condition a = Tables.TEACHER_FILL_TASK_INFO.TIE_ID.eq(tieId).and(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID.eq(teachTypeId));
       Record1<Integer> record1 = create.selectCount()
                .from(Tables.TEACHER_FILL_TASK_INFO)
                .leftJoin(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .on(Tables.TEACHER_FILL_TASK_INFO.TEACHER_FILL_TASK_TEMPLATE_ID.eq(Tables.TEACHER_FILL_TASK_TEMPLATE.ID))
                .leftJoin(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACHER_FILL_TASK_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHER_FILL_TASK_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record1.value1();
    }
}
