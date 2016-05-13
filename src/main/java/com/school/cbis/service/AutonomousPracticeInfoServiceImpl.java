package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeInfoDao;
import com.school.cbis.domain.tables.daos.AutonomousPracticeTemplateDao;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeInfo;
import com.school.cbis.domain.tables.records.AutonomousPracticeInfoRecord;
import com.school.cbis.vo.autonomicpractice.ReportSettingVo;
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
 * Created by Administrator on 2016/4/5.
 */
@Service("autonomousPracticeInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AutonomousPracticeInfoServiceImpl implements AutonomousPracticeInfoService {

    private final Logger log = LoggerFactory.getLogger(AutonomousPracticeInfoServiceImpl.class);

    private final DSLContext create;

    private AutonomousPracticeInfoDao autonomousPracticeInfoDao;

    @Autowired
    public AutonomousPracticeInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.autonomousPracticeInfoDao = new AutonomousPracticeInfoDao(configuration);
    }

    @Override
    public Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> findByTieIdAndPage(ReportSettingVo reportSettingVo,int tieId) {

        Condition a = Tables.AUTONOMOUS_PRACTICE_INFO.TIE_ID.eq(tieId);

        SortField<Timestamp> b = Tables.AUTONOMOUS_PRACTICE_INFO.CREATE_TIME.desc();

        SortField<String> c = null;

        if (StringUtils.hasLength(reportSettingVo.getAutonomousPracticeTitle())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TITLE.like("%" + reportSettingVo.getAutonomousPracticeTitle() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getCreateTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.CREATE_TIME.like("%" + reportSettingVo.getCreateTime() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getGradeYear())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.GRADE_YEAR.like("%" + reportSettingVo.getGradeYear() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getStartTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.START_TIME.like("%" + reportSettingVo.getStartTime() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getEndTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.END_TIME.like("%" + reportSettingVo.getEndTime() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + reportSettingVo.getUsername() + "%"));
        }
        SelectConditionStep<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> e =
        create.select(Tables.AUTONOMOUS_PRACTICE_INFO.ID,Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TITLE,
                Tables.AUTONOMOUS_PRACTICE_INFO.CREATE_TIME, Tables.AUTONOMOUS_PRACTICE_INFO.GRADE_YEAR,
                Tables.AUTONOMOUS_PRACTICE_INFO.START_TIME, Tables.AUTONOMOUS_PRACTICE_INFO.END_TIME,Tables.USERS.USERNAME)
                .from(Tables.AUTONOMOUS_PRACTICE_INFO)
                .join(Tables.USERS)
                .on(Tables.AUTONOMOUS_PRACTICE_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a);

        if (StringUtils.hasLength(reportSettingVo.getSortField())) {
            if (reportSettingVo.getSortField().equals("createTime")) {
                if (reportSettingVo.getSortOrder().equals("desc")) {
                    b = Tables.AUTONOMOUS_PRACTICE_INFO.CREATE_TIME.desc();
                } else {
                    b = Tables.AUTONOMOUS_PRACTICE_INFO.CREATE_TIME.asc();
                }
            } else if (reportSettingVo.getSortField().equals("startTime")) {
                if (reportSettingVo.getSortOrder().equals("desc")) {
                    b = Tables.AUTONOMOUS_PRACTICE_INFO.START_TIME.desc();
                } else {
                    b = Tables.AUTONOMOUS_PRACTICE_INFO.START_TIME.asc();
                }
            } else if (reportSettingVo.getSortField().equals("endTime")) {
                if (reportSettingVo.getSortOrder().equals("desc")) {
                    b = Tables.AUTONOMOUS_PRACTICE_INFO.END_TIME.desc();
                } else {
                    b = Tables.AUTONOMOUS_PRACTICE_INFO.END_TIME.asc();
                }
            } else if (reportSettingVo.getSortField().equals("autonomousPracticeTitle")) {
                if (reportSettingVo.getSortOrder().equals("desc")) {
                    c = Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TITLE.desc();
                } else {
                    c = Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TITLE.asc();
                }
            } else if (reportSettingVo.getSortField().equals("username")) {
                if (reportSettingVo.getSortOrder().equals("desc")) {
                    c = Tables.USERS.USERNAME.desc();
                } else {
                    c = Tables.USERS.USERNAME.asc();
                }
            }

            if (!StringUtils.isEmpty(b)) {
                e.orderBy(b);
            } else {
                e.orderBy(c);
            }

        } else {
            e.orderBy(b);
        }
        return e.limit((reportSettingVo.getPageIndex() - 1) * reportSettingVo.getPageSize(), reportSettingVo.getPageSize()).fetch();
    }

    @Override
    public int findByTieIdAndCount(ReportSettingVo reportSettingVo, int tieId) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_INFO.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(reportSettingVo.getAutonomousPracticeTitle())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TITLE.like("%" + reportSettingVo.getAutonomousPracticeTitle() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getCreateTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.CREATE_TIME.like("%" + reportSettingVo.getCreateTime() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getGradeYear())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.GRADE_YEAR.like("%" + reportSettingVo.getGradeYear() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getStartTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.START_TIME.like("%" + reportSettingVo.getStartTime() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getEndTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_INFO.END_TIME.like("%" + reportSettingVo.getEndTime() + "%"));
        }

        if (StringUtils.hasLength(reportSettingVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + reportSettingVo.getUsername() + "%"));
        }
        Record1<Integer> count = create.selectCount()
                .from(Tables.AUTONOMOUS_PRACTICE_INFO)
                .join(Tables.USERS)
                .on(Tables.AUTONOMOUS_PRACTICE_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a).fetchOne();
        return count.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int save(AutonomousPracticeInfo autonomousPracticeInfo) {
        AutonomousPracticeInfoRecord autonomousPracticeInfoRecord =  create.insertInto(Tables.AUTONOMOUS_PRACTICE_INFO)
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TITLE, autonomousPracticeInfo.getAutonomousPracticeTitle())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.GRADE_YEAR, autonomousPracticeInfo.getGradeYear())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TEMPLATE_ID, autonomousPracticeInfo.getAutonomousPracticeTemplateId())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.START_TIME, autonomousPracticeInfo.getStartTime())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.END_TIME, autonomousPracticeInfo.getEndTime())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.USERS_ID, autonomousPracticeInfo.getUsersId())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.TIE_ID, autonomousPracticeInfo.getTieId())
                .returning(Tables.ARTICLE_INFO.ID)
                .fetchOne();
        return autonomousPracticeInfoRecord.getId();
    }

    @Override
    public void deleteById(int id) {
        autonomousPracticeInfoDao.deleteById(id);
    }

    @Override
    public Result<Record11<Integer, String, Timestamp, String, String, Timestamp, Timestamp, String, Integer,Integer,String>> findByTieIdAndPage(int tieId,int pageNum,int pageSize) {
        if(pageNum<=0){
            pageNum = 1;
        }
        Result<Record11<Integer, String, Timestamp, String, String, Timestamp, Timestamp, String, Integer,Integer,String>> record11s =
        create.select(Tables.AUTONOMOUS_PRACTICE_INFO.ID,Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TITLE,
                Tables.AUTONOMOUS_PRACTICE_INFO.CREATE_TIME,Tables.AUTONOMOUS_PRACTICE_INFO.GRADE_YEAR,
                Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE,
                Tables.AUTONOMOUS_PRACTICE_INFO.START_TIME,Tables.AUTONOMOUS_PRACTICE_INFO.END_TIME,
                Tables.USERS.USERNAME,Tables.USERS.USER_TYPE_ID,Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TEMPLATE_ID,
                Tables.USERS.REAL_NAME)
                .from(Tables.AUTONOMOUS_PRACTICE_INFO)
                .join(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                .on(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.ID))
                .join(Tables.USERS)
                .on(Tables.AUTONOMOUS_PRACTICE_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(Tables.AUTONOMOUS_PRACTICE_INFO.TIE_ID.eq(tieId))
                .orderBy(Tables.AUTONOMOUS_PRACTICE_INFO.CREATE_TIME.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return record11s;
    }

    @Override
    public int findByTieIdAndPageCount(int tieId) {
        Record1<Integer> count = create.selectCount()
                .from(Tables.AUTONOMOUS_PRACTICE_INFO)
                .join(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                .on(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.ID))
                .join(Tables.USERS)
                .on(Tables.AUTONOMOUS_PRACTICE_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(Tables.AUTONOMOUS_PRACTICE_INFO.TIE_ID.eq(tieId))
                .fetchOne();
        return count.value1();
    }

    @Override
    public AutonomousPracticeInfo findById(int id) {
        AutonomousPracticeInfo autonomousPracticeInfo = autonomousPracticeInfoDao.findById(id);
        return autonomousPracticeInfo;
    }

    @Override
    public void update(AutonomousPracticeInfo autonomousPracticeInfo) {
        autonomousPracticeInfoDao.update(autonomousPracticeInfo);
    }

    @Override
    public void deleteByAutonomousPracticeTemplateId(int autonomousPracticeTemplateId) {
        create.deleteFrom(Tables.AUTONOMOUS_PRACTICE_INFO).where(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(autonomousPracticeTemplateId)).execute();
    }
}
