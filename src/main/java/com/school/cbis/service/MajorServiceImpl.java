package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.MajorDao;
import com.school.cbis.domain.tables.pojos.Major;
import com.school.cbis.vo.major.*;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-02-07.
 */
@Service("majorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MajorServiceImpl implements MajorService {

    private final DSLContext create;


    private MajorDao majorDao;

    @Autowired
    public MajorServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.majorDao = new MajorDao(configuration);
    }


    @Override
    public Result<Record2<Integer, String>> findByTieIdByPage(MajorVo majorVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorVo.getMajorName() + "%"));
        }

        SelectConditionStep<Record2<Integer, String>> d = create.select(Tables.MAJOR.ID, Tables.MAJOR.MAJOR_NAME).from(Tables.MAJOR)
                .where(a);

        SortField<String> b = null;
        SortField<Integer> c = Tables.MAJOR.ID.desc();
        if (StringUtils.hasLength(majorVo.getSortField())) {
            if (majorVo.getSortField().equals("majorName")) {
                if (majorVo.getSortOrder().equals("desc")) {
                    b = Tables.MAJOR.MAJOR_NAME.desc();
                } else {
                    b = Tables.MAJOR.MAJOR_NAME.asc();
                }
            }
            d.orderBy(b);
        } else {
            d.orderBy(c);
        }
        return d.limit((majorVo.getPageIndex() - 1) * majorVo.getPageSize(), majorVo.getPageSize()).fetch();
    }

    @Override
    public int findByTieIdCount(MajorVo majorVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorVo.getMajorName() + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.MAJOR)
                .where(a).fetchOne();
        return count.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void saveMajor(Major major) {
        majorDao.insert(major);
    }

    @Override
    public void update(Major major) {
        majorDao.update(major);
    }

    @Override
    public Major findById(int id) {
        Major major = majorDao.findById(id);
        return major;
    }

    @Override
    public void deleteById(int id) {
        majorDao.deleteById(id);
    }

    @Override
    public List<Major> findByMajorName(String majorName) {
        List<Major> list = majorDao.fetchByMajorName(majorName);
        return list;
    }

    @Override
    public List<Major> findAll() {
        List<Major> list = majorDao.findAll();
        return list;
    }

    @Override
    public Result<Record5< Integer, String, String, String, Timestamp>> findAllWithIntroduceByPage(MajorIntroduceVo majorIntroduceVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorIntroduceVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorIntroduceVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + majorIntroduceVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorIntroduceVo.getDate() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorIntroduceVo.getMajorName() + "%"));
        }

        SelectConditionStep<Record5<Integer, String, String, String, Timestamp>> b = create
                .select(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME,
                        Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.ARTICLE_INFO.DATE)
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_INTRODUCE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(a);

        SortField<Integer> c = Tables.MAJOR.ID.desc();

        SortField<String> d = null;

        SortField<Timestamp> e = null;

        if (StringUtils.hasLength(majorIntroduceVo.getSortField())) {
            if (majorIntroduceVo.getSortField().equals("bigTitle")) {
                if (majorIntroduceVo.getSortOrder().equals("desc")) {
                    d = Tables.ARTICLE_INFO.BIG_TITLE.desc();
                } else {
                    d = Tables.ARTICLE_INFO.BIG_TITLE.asc();
                }
            } else if (majorIntroduceVo.getSortField().equals("username")) {
                if (majorIntroduceVo.getSortOrder().equals("desc")) {
                    d = Tables.USERS.USERNAME.desc();
                } else {
                    d = Tables.USERS.USERNAME.asc();
                }
            } else if (majorIntroduceVo.getSortField().equals("date")) {
                if (majorIntroduceVo.getSortOrder().equals("desc")) {
                    e = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    e = Tables.ARTICLE_INFO.DATE.asc();
                }
            } else if(majorIntroduceVo.getMajorName().equals("majorName")){
                if (majorIntroduceVo.getSortOrder().equals("desc")) {
                    d = Tables.MAJOR.MAJOR_NAME.desc();
                } else {
                    d = Tables.MAJOR.MAJOR_NAME.asc();
                }
            }

            if (!StringUtils.isEmpty(d)) {
                b.orderBy(d);
            } else if (!StringUtils.isEmpty(e)) {
                b.orderBy(e);
            } else {
                b.orderBy(c);
            }
        } else {
            b.orderBy(c);
        }

        return  b.limit((majorIntroduceVo.getPageIndex() - 1) * majorIntroduceVo.getPageSize(), majorIntroduceVo.getPageSize()).fetch();
    }

    @Override
    public int findAllWithIntroduceByPageCount(MajorIntroduceVo majorIntroduceVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorIntroduceVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorIntroduceVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + majorIntroduceVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorIntroduceVo.getDate() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorIntroduceVo.getMajorName() + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_INTRODUCE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(a).fetchOne();
        return count.value1();
    }

    @Override
    public Result<Record5<Integer, String, String, String, Timestamp>> findAllWithHeadByPage(MajorHeadVo majorHeadVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorHeadVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorHeadVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + majorHeadVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorHeadVo.getDate() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorHeadVo.getMajorName() + "%"));
        }

        SelectConditionStep<Record5<Integer, String, String, String, Timestamp>> b = create
                .select(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME,
                        Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.ARTICLE_INFO.DATE)
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_FOREGOER_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(a);

        SortField<Integer> c = Tables.MAJOR.ID.desc();

        SortField<String> d = null;

        SortField<Timestamp> e = null;

        if (StringUtils.hasLength(majorHeadVo.getSortField())) {
            if (majorHeadVo.getSortField().equals("bigTitle")) {
                if (majorHeadVo.getSortOrder().equals("desc")) {
                    d = Tables.ARTICLE_INFO.BIG_TITLE.desc();
                } else {
                    d = Tables.ARTICLE_INFO.BIG_TITLE.asc();
                }
            } else if (majorHeadVo.getSortField().equals("username")) {
                if (majorHeadVo.getSortOrder().equals("desc")) {
                    d = Tables.USERS.USERNAME.desc();
                } else {
                    d = Tables.USERS.USERNAME.asc();
                }
            } else if (majorHeadVo.getSortField().equals("date")) {
                if (majorHeadVo.getSortOrder().equals("desc")) {
                    e = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    e = Tables.ARTICLE_INFO.DATE.asc();
                }
            } else if(majorHeadVo.getMajorName().equals("majorName")){
                if (majorHeadVo.getSortOrder().equals("desc")) {
                    d = Tables.MAJOR.MAJOR_NAME.desc();
                } else {
                    d = Tables.MAJOR.MAJOR_NAME.asc();
                }
            }

            if (!StringUtils.isEmpty(d)) {
                b.orderBy(d);
            } else if (!StringUtils.isEmpty(e)) {
                b.orderBy(e);
            } else {
                b.orderBy(c);
            }
        } else {
            b.orderBy(c);
        }

        return  b.limit((majorHeadVo.getPageIndex() - 1) * majorHeadVo.getPageSize(), majorHeadVo.getPageSize()).fetch();
    }

    @Override
    public int findAllWithHeadByPageCount(MajorHeadVo majorHeadVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorHeadVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorHeadVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + majorHeadVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorHeadVo.getDate() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorHeadVo.getMajorName() + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_FOREGOER_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(a).fetchOne();
        return count.value1();
    }

    @Override
    public Result<Record5<Integer, String, String, String, Timestamp>> findAllWithTrainingGoalByPage(MajorTrainingGoalVo majorTrainingGoalVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorTrainingGoalVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorTrainingGoalVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + majorTrainingGoalVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorTrainingGoalVo.getDate() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorTrainingGoalVo.getMajorName() + "%"));
        }

        SelectConditionStep<Record5<Integer, String, String, String, Timestamp>> b = create
                .select(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME,
                        Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.ARTICLE_INFO.DATE)
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_TRAINING_GOAL_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(a);

        SortField<Integer> c = Tables.MAJOR.ID.desc();

        SortField<String> d = null;

        SortField<Timestamp> e = null;

        if (StringUtils.hasLength(majorTrainingGoalVo.getSortField())) {
            if (majorTrainingGoalVo.getSortField().equals("bigTitle")) {
                if (majorTrainingGoalVo.getSortOrder().equals("desc")) {
                    d = Tables.ARTICLE_INFO.BIG_TITLE.desc();
                } else {
                    d = Tables.ARTICLE_INFO.BIG_TITLE.asc();
                }
            } else if (majorTrainingGoalVo.getSortField().equals("username")) {
                if (majorTrainingGoalVo.getSortOrder().equals("desc")) {
                    d = Tables.USERS.USERNAME.desc();
                } else {
                    d = Tables.USERS.USERNAME.asc();
                }
            } else if (majorTrainingGoalVo.getSortField().equals("date")) {
                if (majorTrainingGoalVo.getSortOrder().equals("desc")) {
                    e = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    e = Tables.ARTICLE_INFO.DATE.asc();
                }
            } else if(majorTrainingGoalVo.getMajorName().equals("majorName")){
                if (majorTrainingGoalVo.getSortOrder().equals("desc")) {
                    d = Tables.MAJOR.MAJOR_NAME.desc();
                } else {
                    d = Tables.MAJOR.MAJOR_NAME.asc();
                }
            }

            if (!StringUtils.isEmpty(d)) {
                b.orderBy(d);
            } else if (!StringUtils.isEmpty(e)) {
                b.orderBy(e);
            } else {
                b.orderBy(c);
            }
        } else {
            b.orderBy(c);
        }

        return  b.limit((majorTrainingGoalVo.getPageIndex() - 1) * majorTrainingGoalVo.getPageSize(), majorTrainingGoalVo.getPageSize()).fetch();
    }

    @Override
    public int findAllWithTrainingGoalByPageCount(MajorTrainingGoalVo majorTrainingGoalVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorTrainingGoalVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorTrainingGoalVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + majorTrainingGoalVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorTrainingGoalVo.getDate() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorTrainingGoalVo.getMajorName() + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_TRAINING_GOAL_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(a).fetchOne();
        return count.value1();
    }

    @Override
    public Result<Record5<Integer, String, String, String, Timestamp>> findAllWithTraitByPage(MajorTraitVo majorTraitVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorTraitVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorTraitVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + majorTraitVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorTraitVo.getDate() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorTraitVo.getMajorName() + "%"));
        }

        SelectConditionStep<Record5<Integer, String, String, String, Timestamp>> b = create
                .select(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME,
                        Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.ARTICLE_INFO.DATE)
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_TRAIT_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(a);

        SortField<Integer> c = Tables.MAJOR.ID.desc();

        SortField<String> d = null;

        SortField<Timestamp> e = null;

        if (StringUtils.hasLength(majorTraitVo.getSortField())) {
            if (majorTraitVo.getSortField().equals("bigTitle")) {
                if (majorTraitVo.getSortOrder().equals("desc")) {
                    d = Tables.ARTICLE_INFO.BIG_TITLE.desc();
                } else {
                    d = Tables.ARTICLE_INFO.BIG_TITLE.asc();
                }
            } else if (majorTraitVo.getSortField().equals("username")) {
                if (majorTraitVo.getSortOrder().equals("desc")) {
                    d = Tables.USERS.USERNAME.desc();
                } else {
                    d = Tables.USERS.USERNAME.asc();
                }
            } else if (majorTraitVo.getSortField().equals("date")) {
                if (majorTraitVo.getSortOrder().equals("desc")) {
                    e = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    e = Tables.ARTICLE_INFO.DATE.asc();
                }
            } else if(majorTraitVo.getMajorName().equals("majorName")){
                if (majorTraitVo.getSortOrder().equals("desc")) {
                    d = Tables.MAJOR.MAJOR_NAME.desc();
                } else {
                    d = Tables.MAJOR.MAJOR_NAME.asc();
                }
            }

            if (!StringUtils.isEmpty(d)) {
                b.orderBy(d);
            } else if (!StringUtils.isEmpty(e)) {
                b.orderBy(e);
            } else {
                b.orderBy(c);
            }
        } else {
            b.orderBy(c);
        }

        return  b.limit((majorTraitVo.getPageIndex() - 1) * majorTraitVo.getPageSize(), majorTraitVo.getPageSize()).fetch();
    }

    @Override
    public int findAllWithTraitByPageCount(MajorTraitVo majorTraitVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorTraitVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorTraitVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + majorTraitVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorTraitVo.getDate() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorTraitVo.getMajorName() + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_TRAIT_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(a).fetchOne();
        return count.value1();
    }

    @Override
    public List<Major> findByTieId(int tieId) {
        List<Major> majors = majorDao.fetchByTieId(tieId);
        return majors;
    }
}
