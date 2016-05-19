package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.GradeDao;
import com.school.cbis.domain.tables.daos.MailboxCountDao;
import com.school.cbis.domain.tables.pojos.MailboxCount;
import com.school.cbis.vo.mail.MailListVo;
import org.apache.poi.ss.formula.functions.T;
import org.joda.time.DateTime;
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
 * Created by lenovo on 2016-05-18.
 */
@Service("mailboxCountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MailboxCountServiceImpl implements MailboxCountService {

    private final Logger log = LoggerFactory.getLogger(MailboxCountServiceImpl.class);

    private final DSLContext create;

    private MailboxCountDao mailboxCountDao;

    @Resource
    private Wordbook wordbook;

    @Autowired
    public MailboxCountServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.mailboxCountDao = new MailboxCountDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(MailboxCount mailboxCount) {
        mailboxCountDao.insert(mailboxCount);
    }

    @Override
    public boolean isExceedDailyLimit() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        Record1<Integer> record1 = create.selectCount()
                .from(Tables.MAILBOX_COUNT)
                .where(Tables.MAILBOX_COUNT.SEND_TIME.like("%"+date+"%"))
                .fetchOne();
        if(record1.value1()>wordbook.dailyLimit){
            return true;
        }
        return false;
    }

    @Override
    public Result<Record> findAllAndPage(MailListVo mailListVo) {
        Condition a = Tables.MAILBOX_COUNT.ID.gt(0);
        if(StringUtils.hasLength(mailListVo.getUsername())){
            a = a.and(Tables.MAILBOX_COUNT.ACCEPT_USER.like("%"+mailListVo.getUsername()+"%"));
        }

        if(StringUtils.hasLength(mailListVo.getStartDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(mailListVo.getStartDate());
                Timestamp ts = new Timestamp(date.getTime());
                a = a.and(Tables.MAILBOX_COUNT.SEND_TIME.ge(ts));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.hasLength(mailListVo.getEndDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(mailListVo.getEndDate());
                Timestamp ts = new Timestamp(date.getTime());
                a = a.and(Tables.MAILBOX_COUNT.SEND_TIME.le(ts));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int pageNum = mailListVo.getPageNum();
        int pageSize = mailListVo.getPageSize();
        if(pageNum <=0){
            pageNum = 1;
        }
        Result<Record> records = create.select()
                .from(Tables.MAILBOX_COUNT)
                .where(a)
                .orderBy(Tables.MAILBOX_COUNT.SEND_TIME.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return records;
    }

    @Override
    public int findAllAndPageCount(MailListVo mailListVo) {
        Condition a = Tables.MAILBOX_COUNT.ID.gt(0);
        if(StringUtils.hasLength(mailListVo.getUsername())){
            a = a.and(Tables.MAILBOX_COUNT.ACCEPT_USER.like("%"+mailListVo.getUsername()+"%"));
        }

        if(StringUtils.hasLength(mailListVo.getStartDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(mailListVo.getStartDate());
                Timestamp ts = new Timestamp(date.getTime());
                a = a.and(Tables.MAILBOX_COUNT.SEND_TIME.ge(ts));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.hasLength(mailListVo.getEndDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(mailListVo.getEndDate());
                Timestamp ts = new Timestamp(date.getTime());
                a = a.and(Tables.MAILBOX_COUNT.SEND_TIME.le(ts));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Record1<Integer> record1 = create.selectCount()
                .from(Tables.MAILBOX_COUNT)
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Override
    public int dailySendNum() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        Record1<Integer> record1 = create.selectCount()
                .from(Tables.MAILBOX_COUNT)
                .where(Tables.MAILBOX_COUNT.SEND_TIME.like("%"+date+"%"))
                .fetchOne();
        return record1.value1();
    }
}
