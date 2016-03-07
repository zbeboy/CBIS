package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.GradeDao;
import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.vo.grade.GradeVo;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lenovo on 2016-01-17.
 */
@Service("gradeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GradeServiceImpl implements GradeService {

    private final DSLContext create;


    private GradeDao gradeDao;

    @Autowired
    public GradeServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.gradeDao = new GradeDao(configuration);
    }

    @Override
    public Grade findById(int id) {
        Grade grade = gradeDao.findById(id);
        return grade;
    }

    @Override
    public Result<Record7<Integer, Integer, String, String, String, String, String>> findAllByPage(GradeVo gradeVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if (StringUtils.hasLength(gradeVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + gradeVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getGradeName())) {
            a = a.and(Tables.GRADE.GRADE_NAME.like("%" + gradeVo.getGradeName() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getYear())) {
            a = a.and(Tables.GRADE.YEAR.like("%" + gradeVo.getYear() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getGradeHead())) {
            a = a.and(Tables.TEACHER.TEACHER_NAME.like("%" + gradeVo.getGradeHead() + "%"));
        }

        SelectConditionStep<Record7<Integer, Integer, String, String, String, String, String>> b =
                create.select(Tables.GRADE.ID, Tables.TEACHER.ID.as("teacherId"), Tables.MAJOR.MAJOR_NAME, Tables.GRADE.YEAR, Tables.GRADE.GRADE_NAME,
                        Tables.TEACHER.TEACHER_NAME.as("gradeHead"), Tables.TEACHER.TEACHER_JOB_NUMBER.as("gradeHeadID"))
                        .from(Tables.GRADE)
                        .leftJoin(Tables.MAJOR)
                        .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                        .leftJoin(Tables.TEACHER)
                        .on(Tables.GRADE.GRADE_HEAD.eq(Tables.TEACHER.TEACHER_JOB_NUMBER))
                        .where(a);

        SortField<Integer> c = Tables.GRADE.ID.desc();

        SortField<String> d = null;

        if (StringUtils.hasLength(gradeVo.getSortField())) {
            if (gradeVo.getSortField().equals("majorName")) {
                if (gradeVo.getSortOrder().equals("desc")) {
                    d = Tables.MAJOR.MAJOR_NAME.desc();
                } else {
                    d = Tables.MAJOR.MAJOR_NAME.asc();
                }
            } else if (gradeVo.getSortField().equals("year")) {
                if (gradeVo.getSortOrder().equals("desc")) {
                    d = Tables.GRADE.YEAR.desc();
                } else {
                    d = Tables.GRADE.YEAR.asc();
                }
            } else if (gradeVo.getSortField().equals("gradeName")) {
                if (gradeVo.getSortOrder().equals("desc")) {
                    d = Tables.GRADE.GRADE_NAME.desc();
                } else {
                    d = Tables.GRADE.GRADE_NAME.asc();
                }
            } else if (gradeVo.getSortField().equals("gradeHead")) {
                if (gradeVo.getSortOrder().equals("desc")) {
                    d = Tables.TEACHER.TEACHER_NAME.desc();
                } else {
                    d = Tables.TEACHER.TEACHER_NAME.asc();
                }
            }
            b.orderBy(d);
        } else {
            b.orderBy(c);
        }

        return b.limit((gradeVo.getPageIndex() - 1) * gradeVo.getPageSize(), gradeVo.getPageSize()).fetch();
    }

    @Override
    public int findAllByPageCount(GradeVo gradeVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if (StringUtils.hasLength(gradeVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + gradeVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getGradeName())) {
            a = a.and(Tables.GRADE.GRADE_NAME.like("%" + gradeVo.getGradeName() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getYear())) {
            a = a.and(Tables.GRADE.YEAR.like("%" + gradeVo.getYear() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getGradeHead())) {
            a = a.and(Tables.TEACHER.TEACHER_NAME.like("%" + gradeVo.getGradeHead() + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.GRADE)
                .leftJoin(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .leftJoin(Tables.TEACHER)
                .on(Tables.TEACHER.TEACHER_NAME.eq(Tables.TEACHER.TEACHER_JOB_NUMBER))
                .where(a).fetchOne();
        return count.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Grade grade) {
        gradeDao.insert(grade);
    }

    @Override
    public void update(Grade grade) {
        gradeDao.update(grade);
    }

    @Override
    public List<Grade> findByGradeName(String gradeName) {
        List<Grade> grades = gradeDao.fetchByGradeName(gradeName);
        return grades;
    }

    @Override
    public void deleteById(int id) {
        gradeDao.deleteById(id);
    }
}