package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.GradeDao;
import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.domain.tables.records.GradeRecord;
import com.school.cbis.vo.grade.GradeVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(GradeServiceImpl.class);

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
    public Result<Record7<Integer,String, Integer, String, String, String, String>> findAllByPage(GradeVo gradeVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if (gradeVo.getMajorId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(gradeVo.getMajorId()));
        }

        if (StringUtils.hasLength(gradeVo.getGradeName())) {
            a = a.and(Tables.GRADE.GRADE_NAME.like("%" + gradeVo.getGradeName() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getYear())) {
            a = a.and(Tables.GRADE.YEAR.like("%" + gradeVo.getYear() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getGradeHead())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + gradeVo.getGradeHead() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + gradeVo.getMajorName() + "%"));
        }

        int pageNum = gradeVo.getPageNum();
        int pageSize = gradeVo.getPageSize();
        if(pageNum <=0){
            pageNum = 1;
        }

        SelectConditionStep<Record7<Integer,String , Integer, String, String, String, String>> b =
                create.select(Tables.GRADE.ID, Tables.MAJOR.MAJOR_NAME,Tables.MAJOR.ID.as("majorId"), Tables.GRADE.YEAR, Tables.GRADE.GRADE_NAME,
                        Tables.USERS.REAL_NAME.as("gradeHead"), Tables.USERS.USERNAME.as("gradeHeadID"))
                        .from(Tables.GRADE)
                        .leftJoin(Tables.MAJOR)
                        .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                        .leftJoin(Tables.USERS)
                        .on(Tables.GRADE.GRADE_HEAD.eq(Tables.USERS.USERNAME))
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
                    d = Tables.USERS.REAL_NAME.desc();
                } else {
                    d = Tables.USERS.REAL_NAME.asc();
                }
            }
            b.orderBy(d);
        } else {
            b.orderBy(c);
        }

        return b.limit((pageNum - 1) * pageSize, pageSize).fetch();
    }

    @Override
    public Record7<Integer, String, Integer, String, String, String, String> findByGradeIdWithUpdate(int gradeId) {
        Record7<Integer, String, Integer, String, String, String, String> record7 =create.select(Tables.GRADE.ID, Tables.MAJOR.MAJOR_NAME,Tables.MAJOR.ID.as("majorId"), Tables.GRADE.YEAR, Tables.GRADE.GRADE_NAME,
                Tables.USERS.REAL_NAME.as("gradeHead"), Tables.USERS.USERNAME.as("gradeHeadID"))
                .from(Tables.GRADE)
                .leftJoin(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.GRADE.GRADE_HEAD.eq(Tables.USERS.USERNAME))
                .where(Tables.GRADE.ID.eq(gradeId))
                .fetchOne();
        return record7;
    }

    @Override
    public int findAllByPageCount(GradeVo gradeVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if (gradeVo.getMajorId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(gradeVo.getMajorId()));
        }

        if (StringUtils.hasLength(gradeVo.getGradeName())) {
            a = a.and(Tables.GRADE.GRADE_NAME.like("%" + gradeVo.getGradeName() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getYear())) {
            a = a.and(Tables.GRADE.YEAR.like("%" + gradeVo.getYear() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getGradeHead())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + gradeVo.getGradeHead() + "%"));
        }

        if (StringUtils.hasLength(gradeVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + gradeVo.getMajorName() + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.GRADE)
                .leftJoin(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.GRADE.GRADE_HEAD.eq(Tables.USERS.USERNAME))
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
    public List<GradeRecord> findByGradeNameAndId(int id, String gradeName) {
        List<GradeRecord> records = create.selectFrom(Tables.GRADE)
                .where(Tables.GRADE.GRADE_NAME.eq(gradeName).and(Tables.GRADE.ID.ne(id))).fetch();
        return records;
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

    @Override
    public Result<Record1<String>> findAllYearDistinct(int tieId) {
        Result<Record1<String>> record1s = create.selectDistinct(Tables.GRADE.YEAR).from(Tables.GRADE)
                .leftJoin(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .where(Tables.MAJOR.TIE_ID.eq(tieId))
                .orderBy(Tables.GRADE.ID.desc()).fetch();
        return record1s;
    }

    @Override
    public List<Grade> findByYear(String year) {
        List<Grade> grades = gradeDao.fetchByYear(year);
        return grades;
    }

    @Override
    public Result<Record> findByYearAndTieId(String year, int tieId) {
        Result<Record> records = create.select()
                .from(Tables.GRADE)
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .where(Tables.GRADE.YEAR.eq(year).and(Tables.MAJOR.TIE_ID.eq(tieId)))
                .fetch();
        return records;
    }

    @Override
    public Result<Record2<Integer,String >> findByYearDistinctMajorId(String year, int tieId) {
        Result<Record2<Integer,String>> record2s = create.selectDistinct(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME)
                .from(Tables.GRADE)
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .where(Tables.MAJOR.TIE_ID.eq(tieId).and(Tables.GRADE.YEAR.eq(year)))
                .fetch();
        return record2s;
    }

    @Override
    public Result<Record2<Integer, String>> findByMajorIdAndYear(int majorId,String year) {
        Result<Record2<Integer,String>> record2s = create.select(Tables.GRADE.ID,Tables.GRADE.GRADE_NAME)
                .from(Tables.GRADE)
                .where(Tables.GRADE.MAJOR_ID.eq(majorId).and(Tables.GRADE.YEAR.eq(year)))
                .fetch();
        return record2s;
    }

    @Override
    public List<Grade> findByMajorId(int majorId) {
        List<Grade> grades = gradeDao.fetchByMajorId(majorId);
        return grades;
    }
}
