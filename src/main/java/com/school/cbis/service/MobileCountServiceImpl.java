package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.MobileCountDao;
import com.school.cbis.domain.tables.pojos.MobileCount;
import com.school.cbis.vo.mobile.MobileListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2016-05-19.
 */
@Service("mobileCountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MobileCountServiceImpl implements MobileCountService {

    private final Logger log = LoggerFactory.getLogger(MailboxCountServiceImpl.class);

    private final DSLContext create;

    private MobileCountDao mobileCountDao;

    @Resource
    private Wordbook wordbook;

    @Autowired
    public MobileCountServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.mobileCountDao = new MobileCountDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(MobileCount mobileCount) {
        mobileCountDao.insert(mobileCount);
    }

    @Override
    public Result<Record> findAllAndPage(MobileListVo MobileListVo) {
        Condition a = Tables.MOBILE_COUNT.ID.gt(0);
        if(StringUtils.hasLength(MobileListVo.getUsername())){
            a = a.and(Tables.MOBILE_COUNT.ACCEPT_USER.like("%"+ MobileListVo.getUsername()+"%"));
        }

        if(StringUtils.hasLength(MobileListVo.getStartDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(MobileListVo.getStartDate());
                Timestamp ts = new Timestamp(date.getTime());
                a = a.and(Tables.MOBILE_COUNT.SEND_TIME.ge(ts));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.hasLength(MobileListVo.getEndDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(MobileListVo.getEndDate());
                Timestamp ts = new Timestamp(date.getTime());
                a = a.and(Tables.MOBILE_COUNT.SEND_TIME.le(ts));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int pageNum = MobileListVo.getPageNum();
        int pageSize = MobileListVo.getPageSize();
        if(pageNum <=0){
            pageNum = 1;
        }
        Result<Record> records = create.select()
                .from(Tables.MOBILE_COUNT)
                .where(a)
                .orderBy(Tables.MOBILE_COUNT.SEND_TIME.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return records;
    }

    @Override
    public int findAllAndPageCount(MobileListVo MobileListVo) {
        Condition a = Tables.MOBILE_COUNT.ID.gt(0);
        if(StringUtils.hasLength(MobileListVo.getUsername())){
            a = a.and(Tables.MOBILE_COUNT.ACCEPT_USER.like("%"+ MobileListVo.getUsername()+"%"));
        }

        if(StringUtils.hasLength(MobileListVo.getStartDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(MobileListVo.getStartDate());
                Timestamp ts = new Timestamp(date.getTime());
                a = a.and(Tables.MOBILE_COUNT.SEND_TIME.ge(ts));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.hasLength(MobileListVo.getEndDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(MobileListVo.getEndDate());
                Timestamp ts = new Timestamp(date.getTime());
                a = a.and(Tables.MOBILE_COUNT.SEND_TIME.le(ts));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Record1<Integer> record1 = create.selectCount()
                .from(Tables.MOBILE_COUNT)
                .where(a)
                .fetchOne();
        return record1.value1();
    }
}
