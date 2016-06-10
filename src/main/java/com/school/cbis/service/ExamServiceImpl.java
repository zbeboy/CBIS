package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.ExamDao;
import com.school.cbis.domain.tables.pojos.Exam;
import com.school.cbis.vo.exam.ExamListVo;
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
 * Created by guipeng on 2016/6/8.
 */
@Service("examService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExamServiceImpl implements ExamService{

    private final Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);

    private final DSLContext create;

    private ExamDao examDao;

    @Autowired
    public ExamServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.examDao = new ExamDao(configuration);
    }


    @Override
    public Result<Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String>> findByTieIdAndPage(ExamListVo examListVo, int tieId) {
        Condition a = Tables.EXAM.TIE_ID.eq(tieId);
        if(StringUtils.hasLength(examListVo.getExamTitle())){
            a = a.and(Tables.EXAM.EXAM_TITLE.like("%"+examListVo.getExamTitle()+"%"));
        }
        if(StringUtils.hasLength(examListVo.getMajorName())){
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%"+ examListVo.getMajorName() +"%"));
        }

        int pageNum = examListVo.getPageNum();
        int pageSize = examListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }
        Result<Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String>> examRecords = create.select(Tables.EXAM.ID, Tables.EXAM.TIE_ID,
                Tables.EXAM.EXAM_TIME, Tables.EXAM.EXAM_ADDRESS, Tables.EXAM.EXAM_CONTENT,
                Tables.EXAM.EXAM_TITLE, Tables.EXAM.MAJOR_ID, Tables.MAJOR.MAJOR_NAME,
                Tables.EXAM.USERNAME, Tables.EXAM.CREATE_TIME, Tables.USERS.REAL_NAME)
                .from(Tables.EXAM)
                .join(Tables.MAJOR)
                .on(Tables.EXAM.MAJOR_ID.eq(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.EXAM.USERNAME.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.EXAM.CREATE_TIME)
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return examRecords;
    }

    @Override
    public int findByTieIdAndPageCount(ExamListVo examListVo, int tieId) {
        Condition a = Tables.EXAM.TIE_ID.eq(tieId);
        if(StringUtils.hasLength(examListVo.getExamTitle())){
            a = a.and(Tables.EXAM.EXAM_TITLE.like("%"+examListVo.getExamTitle()+"%"));
        }
        if(StringUtils.hasLength(examListVo.getMajorName())){
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%"+ examListVo.getMajorName() +"%"));
        }
        Record1<Integer> record1 = create.selectCount()
                .from(Tables.EXAM)
                .join(Tables.MAJOR)
                .on(Tables.EXAM.MAJOR_ID.eq(Tables.MAJOR.ID))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Override
    public void deleteById(int id) {
        examDao.deleteById(id);
    }

    @Override
    public Exam findById(int id) {
        Exam exam = examDao.findById(id);
        return exam;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Exam exam) {
        examDao.insert(exam);
    }

    @Override
    public void update(Exam exam) {
        examDao.update(exam);
    }

    @Override
    public Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String> findByIdWithUserAndMajor(int id) {
        Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String> examRecords = create.select(Tables.EXAM.ID, Tables.EXAM.TIE_ID,
                Tables.EXAM.EXAM_TIME, Tables.EXAM.EXAM_ADDRESS, Tables.EXAM.EXAM_CONTENT,
                Tables.EXAM.EXAM_TITLE, Tables.EXAM.MAJOR_ID, Tables.MAJOR.MAJOR_NAME,
                Tables.EXAM.USERNAME, Tables.EXAM.CREATE_TIME, Tables.USERS.REAL_NAME)
                .from(Tables.EXAM)
                .join(Tables.MAJOR)
                .on(Tables.EXAM.MAJOR_ID.eq(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.EXAM.USERNAME.eq(Tables.USERS.USERNAME))
                .where(Tables.EXAM.ID.eq(id))
                .fetchOne();
        return examRecords;
    }
}
