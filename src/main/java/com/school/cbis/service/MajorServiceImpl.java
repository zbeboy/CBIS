package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.MajorDao;
import com.school.cbis.domain.tables.pojos.Major;
import com.school.cbis.domain.tables.records.MajorRecord;
import com.school.cbis.vo.major.*;
import org.apache.poi.ss.formula.functions.T;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(MajorServiceImpl.class);

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

        int pageNum = majorVo.getPageNum();
        int pageSize = majorVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
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
        return d.limit((pageNum - 1) * pageSize, pageSize).fetch();
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
    public MajorRecord findByMajorNameAndTieId(String majorName,int tieId) {
        MajorRecord majorRecord = create.selectFrom(Tables.MAJOR)
                .where(Tables.MAJOR.MAJOR_NAME.eq(majorName).and(Tables.MAJOR.TIE_ID.eq(tieId)))
                .fetchOne();
        return majorRecord;
    }

    @Override
    public MajorRecord findByMajorNameAndIdAndTieId(String majorName, int tieId,int id) {
        MajorRecord majorRecord = create.selectFrom(Tables.MAJOR)
                .where(Tables.MAJOR.MAJOR_NAME.eq(majorName).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.MAJOR.ID.ne(id)))
                .fetchOne();
        return majorRecord;
    }

    @Override
    public List<Major> findAllByTieId(int tieId) {
        List<Major> list = majorDao.fetchByTieId(tieId);
        return list;
    }

    @Override
    public Result<Record6<Integer,String, String, String, Timestamp,Byte>> findAllWithIntroduceByPage(MajorIntroduceVo majorIntroduceVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorIntroduceVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorIntroduceVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + majorIntroduceVo.getRealName() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorIntroduceVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorIntroduceVo.getDate() + "%"));
        }

        if (majorIntroduceVo.getId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(majorIntroduceVo.getId()));
        }

        if( !StringUtils.isEmpty(majorIntroduceVo.getIsShow()) ){
            if(majorIntroduceVo.getIsShow() == 1){
                a = a.and(Tables.MAJOR.IS_SHOW.eq(majorIntroduceVo.getIsShow()));
            } else {
                a = a.and(Tables.MAJOR.IS_SHOW.eq(majorIntroduceVo.getIsShow()));
            }
        }

        int pageNum = majorIntroduceVo.getPageNum();
        int pageSize = majorIntroduceVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        SelectConditionStep<Record6<Integer,String, String, String, Timestamp,Byte>> b = create
                .select(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME,
                        Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.REAL_NAME, Tables.ARTICLE_INFO.DATE,Tables.MAJOR.IS_SHOW)
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
            } else if (majorIntroduceVo.getSortField().equals("realName")) {
                if (majorIntroduceVo.getSortOrder().equals("desc")) {
                    d = Tables.USERS.REAL_NAME.desc();
                } else {
                    d = Tables.USERS.REAL_NAME.asc();
                }
            } else if (majorIntroduceVo.getSortField().equals("date")) {
                if (majorIntroduceVo.getSortOrder().equals("desc")) {
                    e = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    e = Tables.ARTICLE_INFO.DATE.asc();
                }
            } else if (majorIntroduceVo.getSortField().equals("id")) {
                if (majorIntroduceVo.getSortOrder().equals("desc")) {
                    c = Tables.MAJOR.ID.desc();
                } else {
                    c = Tables.MAJOR.ID.asc();
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

        return b.limit((pageNum - 1) * pageSize, pageSize).fetch();
    }

    @Override
    public int findAllWithIntroduceByPageCount(MajorIntroduceVo majorIntroduceVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorIntroduceVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorIntroduceVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + majorIntroduceVo.getRealName() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorIntroduceVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(majorIntroduceVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorIntroduceVo.getDate() + "%"));
        }

        if (majorIntroduceVo.getId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(majorIntroduceVo.getId()));
        }

        if( !StringUtils.isEmpty(majorIntroduceVo.getIsShow()) ){
            if(majorIntroduceVo.getIsShow() == 1){
                a = a.and(Tables.MAJOR.IS_SHOW.eq(majorIntroduceVo.getIsShow()));
            } else {
                a = a.and(Tables.MAJOR.IS_SHOW.eq(majorIntroduceVo.getIsShow()));
            }
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
    public Result<Record5<Integer,String , String, String, Timestamp>> findAllWithHeadByPage(MajorHeadVo majorHeadVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorHeadVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorHeadVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + majorHeadVo.getRealName() + "%"));
        }

        if(StringUtils.hasLength(majorHeadVo.getMajorName())){
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorHeadVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorHeadVo.getDate() + "%"));
        }

        if (majorHeadVo.getId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(majorHeadVo.getId()));
        }

        int pageNum = majorHeadVo.getPageNum();
        int pageSize = majorHeadVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        SelectConditionStep<Record5<Integer,String, String, String, Timestamp>> b = create
                .select(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME,
                        Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.REAL_NAME, Tables.ARTICLE_INFO.DATE)
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
            } else if (majorHeadVo.getSortField().equals("realName")) {
                if (majorHeadVo.getSortOrder().equals("desc")) {
                    d = Tables.USERS.REAL_NAME.desc();
                } else {
                    d = Tables.USERS.REAL_NAME.asc();
                }
            } else if (majorHeadVo.getSortField().equals("date")) {
                if (majorHeadVo.getSortOrder().equals("desc")) {
                    e = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    e = Tables.ARTICLE_INFO.DATE.asc();
                }
            } else if (majorHeadVo.getSortField().equals("id")) {
                if (majorHeadVo.getSortOrder().equals("desc")) {
                    c = Tables.MAJOR.ID.desc();
                } else {
                    c = Tables.MAJOR.ID.asc();
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

        return b.limit((pageNum - 1) * pageSize, pageSize).fetch();
    }

    @Override
    public int findAllWithHeadByPageCount(MajorHeadVo majorHeadVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorHeadVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorHeadVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + majorHeadVo.getRealName() + "%"));
        }

        if(StringUtils.hasLength(majorHeadVo.getMajorName())){
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorHeadVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(majorHeadVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorHeadVo.getDate() + "%"));
        }

        if (majorHeadVo.getId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(majorHeadVo.getId()));
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
    public Result<Record5<Integer,String, String, String, Timestamp>> findAllWithTrainingGoalByPage(MajorTrainingGoalVo majorTrainingGoalVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorTrainingGoalVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorTrainingGoalVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + majorTrainingGoalVo.getRealName() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorTrainingGoalVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorTrainingGoalVo.getDate() + "%"));
        }

        if (majorTrainingGoalVo.getId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(majorTrainingGoalVo.getId()));
        }

        int pageNum = majorTrainingGoalVo.getPageNum();
        int pageSize = majorTrainingGoalVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        SelectConditionStep<Record5<Integer,String, String, String, Timestamp>> b = create
                .select(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME,
                        Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.REAL_NAME, Tables.ARTICLE_INFO.DATE)
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
            } else if (majorTrainingGoalVo.getSortField().equals("realName")) {
                if (majorTrainingGoalVo.getSortOrder().equals("desc")) {
                    d = Tables.USERS.REAL_NAME.desc();
                } else {
                    d = Tables.USERS.REAL_NAME.asc();
                }
            } else if (majorTrainingGoalVo.getSortField().equals("date")) {
                if (majorTrainingGoalVo.getSortOrder().equals("desc")) {
                    e = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    e = Tables.ARTICLE_INFO.DATE.asc();
                }
            } else if (majorTrainingGoalVo.getSortField().equals("id")) {
                if (majorTrainingGoalVo.getSortOrder().equals("desc")) {
                    c = Tables.MAJOR.ID.desc();
                } else {
                    c = Tables.MAJOR.ID.asc();
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

        return b.limit((pageNum - 1) * pageSize, pageSize).fetch();
    }

    @Override
    public int findAllWithTrainingGoalByPageCount(MajorTrainingGoalVo majorTrainingGoalVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorTrainingGoalVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorTrainingGoalVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + majorTrainingGoalVo.getRealName() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorTrainingGoalVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(majorTrainingGoalVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorTrainingGoalVo.getDate() + "%"));
        }

        if (majorTrainingGoalVo.getId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(majorTrainingGoalVo.getId()));
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
    public Result<Record5<Integer,String, String, String, Timestamp>> findAllWithTraitByPage(MajorTraitVo majorTraitVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorTraitVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorTraitVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + majorTraitVo.getRealName() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorTraitVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorTraitVo.getDate() + "%"));
        }

        if (majorTraitVo.getId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(majorTraitVo.getId()));
        }

        int pageNum = majorTraitVo.getPageNum();
        int pageSize = majorTraitVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        SelectConditionStep<Record5<Integer,String, String, String, Timestamp>> b = create
                .select(Tables.MAJOR.ID,Tables.MAJOR.MAJOR_NAME,
                        Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.REAL_NAME, Tables.ARTICLE_INFO.DATE)
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
            } else if (majorTraitVo.getSortField().equals("realName")) {
                if (majorTraitVo.getSortOrder().equals("desc")) {
                    d = Tables.USERS.REAL_NAME.desc();
                } else {
                    d = Tables.USERS.REAL_NAME.asc();
                }
            } else if (majorTraitVo.getSortField().equals("date")) {
                if (majorTraitVo.getSortOrder().equals("desc")) {
                    e = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    e = Tables.ARTICLE_INFO.DATE.asc();
                }
            } else if (majorTraitVo.getSortField().equals("id")) {
                if (majorTraitVo.getSortOrder().equals("desc")) {
                    c = Tables.MAJOR.ID.desc();
                } else {
                    c = Tables.MAJOR.ID.asc();
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

        return b.limit((pageNum - 1) * pageSize, pageSize).fetch();
    }

    @Override
    public int findAllWithTraitByPageCount(MajorTraitVo majorTraitVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(majorTraitVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + majorTraitVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + majorTraitVo.getRealName() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getMajorName())) {
            a = a.and(Tables.MAJOR.MAJOR_NAME.like("%" + majorTraitVo.getMajorName() + "%"));
        }

        if (StringUtils.hasLength(majorTraitVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + majorTraitVo.getDate() + "%"));
        }

        if (majorTraitVo.getId() > 0) {
            a = a.and(Tables.MAJOR.ID.eq(majorTraitVo.getId()));
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

    @Override
    public Result<Record2<Integer, String>> findByTieIdToList(int tieId) {
        Result<Record2<Integer, String>> record2s = create.select(Tables.MAJOR.ID, Tables.MAJOR.MAJOR_NAME)
                .from(Tables.MAJOR)
                .where(Tables.MAJOR.TIE_ID.eq(tieId)).fetch();
        return record2s;
    }

    @Override
    public Result<Record4<Integer, String, Integer, String>> findByTieIdWithArticleAndPage(int tieId, int pageNum, int pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        Result<Record4<Integer, String, Integer, String>> record4s = create.select(Tables.MAJOR.ID.as("majorId"), Tables.MAJOR.MAJOR_NAME, Tables.ARTICLE_INFO.ID.as("articleInfoId"), Tables.ARTICLE_INFO.ARTICLE_CONTENT)
                .from(Tables.MAJOR)
                .leftJoin(Tables.ARTICLE_INFO)
                .on(Tables.MAJOR.MAJOR_INTRODUCE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .where(Tables.MAJOR.TIE_ID.eq(tieId))
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();
        return record4s;
    }

    @Override
    public List<Major> findByShow(Byte bytes) {
        List<Major> majors = majorDao.fetchByIsShow(bytes);
        return majors;
    }

    @Override
    public Result<Record3<String, String, String>> findByIdWithTeacher(int id) {
        Result<Record3<String, String, String>> record3s = create.select(Tables.USERS.USERNAME,Tables.USERS.REAL_NAME,Tables.USERS.HEAD_IMG)
                .from(Tables.MAJOR)
                .join(Tables.TEACHER)
                .on(Tables.MAJOR.TIE_ID.eq(Tables.TEACHER.TIE_ID))
                .join(Tables.USERS)
                .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                .where(Tables.MAJOR.ID.eq(id))
                .fetch();
        return record3s;
    }
}
