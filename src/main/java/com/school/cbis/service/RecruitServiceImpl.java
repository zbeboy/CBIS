package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.RecruitDao;
import com.school.cbis.domain.tables.daos.StudentDao;
import com.school.cbis.domain.tables.pojos.Recruit;
import com.school.cbis.domain.tables.records.RecruitRecord;
import com.school.cbis.vo.recruit.RecruitListVo;
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
 * Created by lenovo on 2016-05-26.
 */
@Service("recruitService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RecruitServiceImpl implements RecruitService{

    private final Logger log = LoggerFactory.getLogger(RecruitServiceImpl.class);

    private final DSLContext create;

    private RecruitDao recruitDao;

    @Autowired
    public RecruitServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.recruitDao = new RecruitDao(configuration);
    }

    @Override
    public Result<Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String>> findByTieIdAndPage(RecruitListVo recruitListVo, int tieId) {
        Condition a = Tables.RECRUIT.TIE_ID.eq(tieId);

        if(StringUtils.hasLength(recruitListVo.getRecruitTitle())){
            a = a.and(Tables.RECRUIT.RECRUIT_TITLE.like("%"+recruitListVo.getRecruitTitle()+"%"));
        }

        if(StringUtils.hasLength(recruitListVo.getFitMajor())){
            a = a.and(Tables.RECRUIT.FIT_MAJOR.like("%"+recruitListVo.getFitMajor()+"%"));
        }

        int pageNum = recruitListVo.getPageNum();
        int pageSize = recruitListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String>> recruitRecords = create.select(Tables.RECRUIT.ID,Tables.RECRUIT.TIE_ID,
                Tables.RECRUIT.RECRUIT_TIME,Tables.RECRUIT.RECRUIT_ADDRESS,Tables.RECRUIT.RECRUIT_CONTENT,
                Tables.RECRUIT.TEXT_LINK,Tables.RECRUIT.RECRUIT_TITLE,Tables.RECRUIT.FIT_MAJOR,Tables.RECRUIT.USERNAME,
                Tables.RECRUIT.CREATE_TIME,Tables.USERS.REAL_NAME)
                .from(Tables.RECRUIT)
                .join(Tables.USERS)
                .on(Tables.RECRUIT.USERNAME.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.RECRUIT.CREATE_TIME.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return recruitRecords;
    }

    @Override
    public int findByTieIdAndPageCount(RecruitListVo recruitListVo, int tieId) {
        Condition a = Tables.RECRUIT.TIE_ID.eq(tieId);

        if(StringUtils.hasLength(recruitListVo.getRecruitTitle())){
            a = a.and(Tables.RECRUIT.RECRUIT_TITLE.like("%"+recruitListVo.getRecruitTitle()+"%"));
        }

        if(StringUtils.hasLength(recruitListVo.getFitMajor())){
            a = a.and(Tables.RECRUIT.FIT_MAJOR.like("%"+recruitListVo.getFitMajor()+"%"));
        }

        Record1<Integer> record1 = create.selectCount()
                .from(Tables.RECRUIT)
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Override
    public void deleteById(int id) {
        recruitDao.deleteById(id);
    }

    @Override
    public Recruit findById(int id) {
        Recruit recruit = recruitDao.findById(id);
        return recruit;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Recruit recruit) {
        recruitDao.insert(recruit);
    }

    @Override
    public void update(Recruit recruit) {
        recruitDao.update(recruit);
    }

    @Override
    public Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String> findByIdWithUser(int id) {
        Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String> recruitRecords = create.select(Tables.RECRUIT.ID,Tables.RECRUIT.TIE_ID,
                Tables.RECRUIT.RECRUIT_TIME,Tables.RECRUIT.RECRUIT_ADDRESS,Tables.RECRUIT.RECRUIT_CONTENT,
                Tables.RECRUIT.TEXT_LINK,Tables.RECRUIT.RECRUIT_TITLE,Tables.RECRUIT.FIT_MAJOR,Tables.RECRUIT.USERNAME,
                Tables.RECRUIT.CREATE_TIME,Tables.USERS.REAL_NAME)
                .from(Tables.RECRUIT)
                .join(Tables.USERS)
                .on(Tables.RECRUIT.USERNAME.eq(Tables.USERS.USERNAME))
                .where(Tables.RECRUIT.ID.eq(id))
                .fetchOne();
        return recruitRecords;
    }
}
