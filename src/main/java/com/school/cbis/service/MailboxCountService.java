package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.MailboxCount;
import com.school.cbis.vo.mail.MailListVo;
import org.jooq.Record;
import org.jooq.Result;

/**
 * Created by lenovo on 2016-05-18.
 */
public interface MailboxCountService {

    /**
     * 保存发送
     * @param mailboxCount
     */
    void save(MailboxCount mailboxCount);

    /**
     * 是否已超过每日上限
     * @return
     */
    boolean isExceedDailyLimit();

    /**
     * 分页查询全部
     * @param mailListVo
     * @return
     */
    Result<Record> findAllAndPage(MailListVo mailListVo);

    /**
     * 分页查询全部 总数
     * @param mailListVo
     * @return
     */
    int findAllAndPageCount(MailListVo mailListVo);

    /**
     * 当日已发送数
     * @return
     */
    int dailySendNum();
}
