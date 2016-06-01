package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeachTaskInfoDao;
import com.school.cbis.domain.tables.daos.TeacherDao;
import com.school.cbis.domain.tables.pojos.TeachTaskInfo;
import com.school.cbis.domain.tables.records.ArticleInfoRecord;
import com.school.cbis.domain.tables.records.TeachTaskInfoRecord;
import com.school.cbis.vo.eadmin.AssignmentBookListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lenovo on 2016-05-28.
 */
@Service("teachTaskInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachTaskInfoServiceImpl implements TeachTaskInfoService {

    private final Logger log = LoggerFactory.getLogger(TeachTaskInfoServiceImpl.class);

    private final DSLContext create;

    private TeachTaskInfoDao teachTaskInfoDao;

    @Autowired
    public TeachTaskInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachTaskInfoDao = new TeachTaskInfoDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int saveAndReturnId(TeachTaskInfo teachTaskInfo) {
        TeachTaskInfoRecord teachTaskInfoRecord = create.insertInto(Tables.TEACH_TASK_INFO)
                .set(Tables.TEACH_TASK_INFO.TIE_ID, teachTaskInfo.getTieId())
                .set(Tables.TEACH_TASK_INFO.TEACH_TASK_FILE_URL, teachTaskInfo.getTeachTaskFileUrl())
                .set(Tables.TEACH_TASK_INFO.TEACH_TASK_FILE_SIZE, teachTaskInfo.getTeachTaskFileSize())
                .set(Tables.TEACH_TASK_INFO.TEACH_TASK_FILE_DATE, teachTaskInfo.getTeachTaskFileDate())
                .set(Tables.TEACH_TASK_INFO.TEACH_TASK_TERM, teachTaskInfo.getTeachTaskTerm())
                .set(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID, teachTaskInfo.getTeachTypeId())
                .set(Tables.TEACH_TASK_INFO.TERM_START_TIME, teachTaskInfo.getTermStartTime())
                .set(Tables.TEACH_TASK_INFO.TERM_END_TIME, teachTaskInfo.getTermEndTime())
                .set(Tables.TEACH_TASK_INFO.FILE_USER, teachTaskInfo.getFileUser())
                .set(Tables.TEACH_TASK_INFO.FILE_TYPE, teachTaskInfo.getFileType())
                .set(Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE, teachTaskInfo.getTeachTaskTitle())
                .set(Tables.TEACH_TASK_INFO.YEAR_X,teachTaskInfo.getYearX())
                .set(Tables.TEACH_TASK_INFO.YEAR_Y,teachTaskInfo.getYearY())
                .set(Tables.TEACH_TASK_INFO.GRADE_X,teachTaskInfo.getGradeX())
                .set(Tables.TEACH_TASK_INFO.GRADE_Y,teachTaskInfo.getGradeY())
                .set(Tables.TEACH_TASK_INFO.GRADE_NUM_X,teachTaskInfo.getGradeNumX())
                .set(Tables.TEACH_TASK_INFO.GRADE_NUM_Y,teachTaskInfo.getGradeNumY())
                .returning(Tables.TEACH_TASK_INFO.ID)
                .fetchOne();
        return teachTaskInfoRecord.getId();
    }

    @Override
    public List<TeachTaskInfo> findByTeachTaskInfoTitle(String teachTaskInfoTitle) {
        List<TeachTaskInfo> teachTaskInfos = teachTaskInfoDao.fetchByTeachTaskTitle(teachTaskInfoTitle);
        return teachTaskInfos;
    }

    @Override
    public Result<Record7<Integer, String, String, String, Date, Date,Byte>> findAllByTieIdAndPage(AssignmentBookListVo assignmentBookListVo, int tieId) {
        Condition a = Tables.TEACH_TASK_INFO.TIE_ID.eq(tieId);
        if(StringUtils.hasLength(assignmentBookListVo.getTeachTaskTitle())){
            a = a.and(Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE.like("%"+assignmentBookListVo.getTeachTaskTitle()+"%"));
        }

        log.debug(" teachTerm : {} ",assignmentBookListVo.getTeachTaskTerm());
        if(StringUtils.hasLength(assignmentBookListVo.getTeachTaskTerm())){
            a = a.and(Tables.TEACH_TASK_INFO.TEACH_TASK_TERM.like("%"+assignmentBookListVo.getTeachTaskTerm()+"%"));
        }

        if(StringUtils.hasLength(assignmentBookListVo.getTermStartTime())){
            a = a.and(Tables.TEACH_TASK_INFO.TERM_START_TIME.like("%"+assignmentBookListVo.getTermStartTime()+"%"));
        }

        if(StringUtils.hasLength(assignmentBookListVo.getTermEndTime())){
            a = a.and(Tables.TEACH_TASK_INFO.TERM_END_TIME.like("%"+assignmentBookListVo.getTermEndTime()+"%"));
        }

        int pageNum = assignmentBookListVo.getPageNum();
        int pageSize = assignmentBookListVo.getPageSize();

        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record7<Integer, String, String, String, Date, Date,Byte>> record7s = create.select( Tables.TEACH_TASK_INFO.ID,Tables.USERS.REAL_NAME,Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE,
                Tables.TEACH_TASK_INFO.TEACH_TASK_TERM,
                Tables.TEACH_TASK_INFO.TERM_START_TIME,Tables.TEACH_TASK_INFO.TERM_END_TIME,Tables.TEACH_TASK_INFO.IS_USE)
                .from(Tables.TEACH_TASK_INFO)
                .join(Tables.USERS)
                .on(Tables.TEACH_TASK_INFO.FILE_USER.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.TEACH_TASK_INFO.ID.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();

        return record7s;
    }

    @Override
    public int findAllByTieIdAndPageCount(AssignmentBookListVo assignmentBookListVo, int tieId) {
        Condition a = Tables.TEACH_TASK_INFO.TIE_ID.eq(tieId);
        if(StringUtils.hasLength(assignmentBookListVo.getTeachTaskTitle())){
            a = a.and(Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE.like("%"+assignmentBookListVo.getTeachTaskTitle()+"%"));
        }

        if(StringUtils.hasLength(assignmentBookListVo.getTeachTaskTerm())){
            a = a.and(Tables.TEACH_TASK_INFO.TEACH_TASK_TERM.like("%"+assignmentBookListVo.getTeachTaskTerm()+"%"));
        }

        if(StringUtils.hasLength(assignmentBookListVo.getTermStartTime())){
            a = a.and(Tables.TEACH_TASK_INFO.TERM_START_TIME.like("%"+assignmentBookListVo.getTermStartTime()+"%"));
        }

        if(StringUtils.hasLength(assignmentBookListVo.getTermEndTime())){
            a = a.and(Tables.TEACH_TASK_INFO.TERM_END_TIME.like("%"+assignmentBookListVo.getTermEndTime()+"%"));
        }

        Record1<Integer> record1 = create.selectCount()
                .from(Tables.TEACH_TASK_INFO)
                .join(Tables.USERS)
                .on(Tables.TEACH_TASK_INFO.FILE_USER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Override
    public TeachTaskInfo findById(int id) {
        TeachTaskInfo teachTaskInfo = teachTaskInfoDao.findById(id);
        return teachTaskInfo;
    }

    @Override
    public void update(TeachTaskInfo teachTaskInfo) {
        teachTaskInfoDao.update(teachTaskInfo);
    }

    @Override
    public Result<TeachTaskInfoRecord> findByIdAndTeachTaskInfoTitle(int id, String teachTaskInfoTitle) {
        Result<TeachTaskInfoRecord> records = create.selectFrom(Tables.TEACH_TASK_INFO)
                .where(Tables.TEACH_TASK_INFO.ID.ne(id).and(Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE.eq(teachTaskInfoTitle)))
                .fetch();
        return records;
    }
}
