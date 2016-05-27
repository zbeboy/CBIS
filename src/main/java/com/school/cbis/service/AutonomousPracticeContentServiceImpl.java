package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeContentDao;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeContent;
import com.school.cbis.vo.autonomicpractice.*;
import org.apache.poi.ss.formula.functions.T;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2016-04-12.
 */
@Service("autonomousPracticeContentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AutonomousPracticeContentServiceImpl implements AutonomousPracticeContentService {

    private final Logger log = LoggerFactory.getLogger(AutonomousPracticeContentServiceImpl.class);

    private final DSLContext create;

    private AutonomousPracticeContentDao autonomousPracticeContentDao;

    @Autowired
    public AutonomousPracticeContentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.autonomousPracticeContentDao = new AutonomousPracticeContentDao(configuration);
    }

    @Override
    public void deleteByAutonomousPracticeHeadId(int autonomousPracticeHeadId) {
        create.deleteFrom(Tables.AUTONOMOUS_PRACTICE_CONTENT).where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(autonomousPracticeHeadId)).execute();
    }

    @Override
    public Result<Record> findByAutonomousPracticeInfoIdAndStudentId(int autonomousPracticeInfoId, int studentId) {
        Result<Record> records = create.select()
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeInfoId).and(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(studentId)))
                .fetch();
        return records;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(AutonomousPracticeContent autonomousPracticeContent) {
        autonomousPracticeContentDao.insert(autonomousPracticeContent);
    }

    @Override
    public void deleteByAutonomousPracticeHeadIdAndStudentId(int autonomousPracticeHeadId, int studentId) {
        create.deleteFrom(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(autonomousPracticeHeadId).and(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(studentId)))
                .execute();
    }

    @Override
    public void update(AutonomousPracticeContent autonomousPracticeContent) {
        autonomousPracticeContentDao.update(autonomousPracticeContent);
    }

    @Override
    public int findByAutonomousPracticeInfoIdDistinctStudentIdAndYearCount(int autonomousPracticeInfoId, String year) {
        Result<Record1<Integer>> count = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .join(Tables.STUDENT)
                .on(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(Tables.STUDENT.ID))
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeInfoId)
                .and(Tables.GRADE.YEAR.eq(year)))
                .fetch();
        return count.size();
    }

    @Override
    public Result<Record1<Integer>> findByAutonomousPracticeInfoIdDistinctStudentIdAndYear(AutonomousPracticeParamVo autonomousPracticeParamVo) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeParamVo.getAutonomousPracticeInfoId())
                .and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear()));
        Result<Record1<Integer>> count = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .join(Tables.STUDENT)
                .on(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(Tables.STUDENT.ID))
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .where(a)
                .fetch();
        return count;
    }

    @Override
    public int findByAutonomousPracticeInfoIdDistinctStudentIdAndGradeIdCount(int autonomousPracticeInfoId, int gradeId) {
        Result<Record1<Integer>> count = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .join(Tables.STUDENT)
                .on(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(Tables.STUDENT.ID))
                .where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeInfoId)
                        .and(Tables.STUDENT.GRADE_ID.eq(gradeId)))
                .fetch();
        return count.size();
    }

    @Override
    public int findByAutonomousPracticeInfoIdDistinctStudentIdAndMajorIdCount(int autonomousPracticeInfoId, int majorId) {
        Result<Record1<Integer>> count = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .join(Tables.STUDENT)
                .on(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(Tables.STUDENT.ID))
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeInfoId)
                        .and(Tables.GRADE.MAJOR_ID.eq(majorId)))
                .fetch();
        return count.size();
    }

    @Override
    public Result<Record1<Integer>> findByAutonomousPracticeInfoIdDistinctStudentIdAndMajorIdAndYear(AutonomousPracticeParamVo autonomousPracticeParamVo) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeParamVo.getAutonomousPracticeInfoId())
                .and(Tables.GRADE.MAJOR_ID.eq(autonomousPracticeParamVo.getMajorId()).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        Result<Record1<Integer>> count = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .join(Tables.STUDENT)
                .on(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(Tables.STUDENT.ID))
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .where(a)
                .fetch();
        return count;
    }

    @Override
    public Result<Record1<Integer>> findByAutonomousPracticeInfoIdDistinctStudentIdAndGradeIdAndYear(AutonomousPracticeParamVo autonomousPracticeParamVo) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeParamVo.getAutonomousPracticeInfoId())
                .and(Tables.GRADE.ID.eq(autonomousPracticeParamVo.getGradeId()).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        Result<Record1<Integer>> count = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .join(Tables.STUDENT)
                .on(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(Tables.STUDENT.ID))
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .where(a)
                .fetch();
        return count;
    }

    @Override
    public List<AutonomousPracticeContent> findByAutonomousPracticeInfoId(int autonomousPracticeInfoId) {
        List<AutonomousPracticeContent> autonomousPracticeContents = autonomousPracticeContentDao.fetchByAutonomousPracticeInfoId(autonomousPracticeInfoId);
        return autonomousPracticeContents;
    }

    @Override
    public Result<Record1<Integer>> findByAutonomousPracticeInfoIdDistinctStudentIdAndPage(AutonomicPracticeTeacherListVo autonomicPracticeTeacherListVo) {
        int pageNum = autonomicPracticeTeacherListVo.getPageNum();
        int pageSize = autonomicPracticeTeacherListVo.getPageSize();
        Condition a = Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomicPracticeTeacherListVo.getAutonomousPracticeInfoId());

        if(autonomicPracticeTeacherListVo.getAutonomousPracticeHeadId() > 0){
            a = a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(autonomicPracticeTeacherListVo.getAutonomousPracticeHeadId()));
        }

        if(StringUtils.hasLength(autonomicPracticeTeacherListVo.getContent())){
            a = a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT.like("%"+autonomicPracticeTeacherListVo.getContent()+"%"));
        }

        if (pageNum <= 0) {
            pageNum = 1;
        }
        Result<Record1<Integer>> record1s = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .join(Tables.AUTONOMOUS_PRACTICE_HEAD)
                .on(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(Tables.AUTONOMOUS_PRACTICE_HEAD.ID))
                .where(a)
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();
        return record1s;
    }

    @Override
    public Result<Record1<Integer>> findByAutonomousPracticeInfoIdDistinctStudentId(AutonomicPracticeTeacherListVo autonomicPracticeTeacherListVo) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomicPracticeTeacherListVo.getAutonomousPracticeInfoId());

        if(autonomicPracticeTeacherListVo.getAutonomousPracticeHeadId() > 0){
            a = a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(autonomicPracticeTeacherListVo.getAutonomousPracticeHeadId()));
        }

        if(StringUtils.hasLength(autonomicPracticeTeacherListVo.getContent())){
            a = a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT.like("%"+autonomicPracticeTeacherListVo.getContent()+"%"));
        }

        Result<Record1<Integer>> record1s = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .join(Tables.AUTONOMOUS_PRACTICE_HEAD)
                .on(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(Tables.AUTONOMOUS_PRACTICE_HEAD.ID))
                .where(a)
                .fetch();
        return record1s;
    }

    @Override
    public int findByAutonomousPracticeInfoIdDistinctStudentIdAndPageCount(AutonomicPracticeTeacherListVo autonomicPracticeTeacherListVo) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomicPracticeTeacherListVo.getAutonomousPracticeInfoId());

        if(autonomicPracticeTeacherListVo.getAutonomousPracticeHeadId() > 0){
            a = a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(autonomicPracticeTeacherListVo.getAutonomousPracticeHeadId()));
        }

        if(StringUtils.hasLength(autonomicPracticeTeacherListVo.getContent())){
            a = a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT.like("%"+autonomicPracticeTeacherListVo.getContent()+"%"));
        }
        Result<Record1<Integer>> count = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .where(a)
                .fetch();
        return count.size();
    }

    @Override
    public Result<Record> findByAutonomousPracticeInfoIdAndStudentIdAndIsShowHighlyActive(int autonomousPracticeInfoId, int studentId, Byte isShowHighlyActive) {
        Result<Record> records = create.select()
                .from(Tables.AUTONOMOUS_PRACTICE_HEAD)
                .join(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .on(Tables.AUTONOMOUS_PRACTICE_HEAD.ID.eq(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID))
                .join(Tables.HEAD_TYPE)
                .on(Tables.AUTONOMOUS_PRACTICE_HEAD.HEAD_TYPE_ID.eq(Tables.HEAD_TYPE.ID))
                .where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeInfoId).and(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(studentId))
                .and(Tables.AUTONOMOUS_PRACTICE_HEAD.IS_SHOW_HIGHLY_ACTIVE.eq(isShowHighlyActive)))
                .fetch();
        return records;
    }
}
