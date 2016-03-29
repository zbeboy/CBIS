package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TieNoticeDao;
import com.school.cbis.domain.tables.pojos.TieNotice;
import com.school.cbis.vo.tie.TieNoticeVo;
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
 * Created by lenovo on 2016-03-09.
 */
@Service("tieNoticeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieNoticeServiceImpl implements TieNoticeService {

    private final Logger log = LoggerFactory.getLogger(TieNoticeServiceImpl.class);

    private final DSLContext create;

    private TieNoticeDao tieNoticeDao;

    @Autowired
    public TieNoticeServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.tieNoticeDao = new TieNoticeDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TieNotice tieNotice) {
        tieNoticeDao.insert(tieNotice);
    }

    @Override
    public Result<Record5<Integer, String, String, Timestamp,Byte>> findByTieIdWithBigTitleAndPage(TieNoticeVo tieNoticeVo, int tie_id) {
        Condition a = Tables.TIE_NOTICE.TIE_ID.eq(tie_id);

        SortField<Integer> b = Tables.TIE_NOTICE.ID.desc();

        SortField<String> c = null;

        SortField<Timestamp> d = null;


        if (StringUtils.hasLength(tieNoticeVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + tieNoticeVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(tieNoticeVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + tieNoticeVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(tieNoticeVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + tieNoticeVo.getDate() + "%"));
        }

        if (!StringUtils.isEmpty(tieNoticeVo.getShow())) {
            if (tieNoticeVo.getShow()) {
                Byte bytes = 1;
                a = a.and(Tables.TIE_NOTICE.IS_SHOW.eq(bytes));
            } else {
                Byte bytes = 0;
                a = a.and(Tables.TIE_NOTICE.IS_SHOW.eq(bytes));
            }
        }

        SelectConditionStep<Record5<Integer, String, String, Timestamp,Byte>> e = create.select(Tables.TIE_NOTICE.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.ARTICLE_INFO.DATE,Tables.TIE_NOTICE.IS_SHOW)
                .from(Tables.TIE_NOTICE)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_NOTICE.TIE_NOTICE_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                .join(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.equal(Tables.USERS.USERNAME))
                .where(a);

        if (StringUtils.hasLength(tieNoticeVo.getSortField())) {
            if (tieNoticeVo.getSortField().equals("bigTitle")) {
                if (tieNoticeVo.getSortOrder().equals("desc")) {
                    c = Tables.ARTICLE_INFO.BIG_TITLE.desc();
                } else {
                    c = Tables.ARTICLE_INFO.BIG_TITLE.asc();
                }
            } else if (tieNoticeVo.getSortField().equals("username")) {
                if (tieNoticeVo.getSortOrder().equals("desc")) {
                    c = Tables.USERS.USERNAME.desc();
                } else {
                    c = Tables.USERS.USERNAME.asc();
                }
            } else if (tieNoticeVo.getSortField().equals("date")) {
                if (tieNoticeVo.getSortOrder().equals("desc")) {
                    d = Tables.ARTICLE_INFO.DATE.desc();
                } else {
                    d = Tables.ARTICLE_INFO.DATE.asc();
                }
            }

            if (!StringUtils.isEmpty(c)) {
                e.orderBy(c);
            } else if (!StringUtils.isEmpty(d)) {
                e.orderBy(d);
            } else {
                e.orderBy(b);
            }

        } else {
            e.orderBy(b);
        }

        return e.limit((tieNoticeVo.getPageIndex() - 1) * tieNoticeVo.getPageSize(), tieNoticeVo.getPageSize()).fetch();
    }

    @Override
    public int findByTieIdWithBigTitleAndCount(TieNoticeVo tieNoticeVo, int tie_id) {
        Condition a = Tables.TIE_NOTICE.TIE_ID.equal(tie_id);

        if (StringUtils.hasLength(tieNoticeVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + tieNoticeVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(tieNoticeVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + tieNoticeVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(tieNoticeVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + tieNoticeVo.getDate() + "%"));
        }

        if (!StringUtils.isEmpty(tieNoticeVo.getShow())) {
            if (tieNoticeVo.getShow()) {
                Byte bytes = 1;
                a = a.and(Tables.TIE_NOTICE.IS_SHOW.eq(bytes));
            } else {
                Byte bytes = 0;
                a = a.and(Tables.TIE_NOTICE.IS_SHOW.eq(bytes));
            }
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.TIE_NOTICE)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_NOTICE.TIE_NOTICE_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                .join(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.equal(Tables.USERS.USERNAME))
                .where(a).fetchOne();

        return count.value1();
    }

    @Override
    public void deleteById(int id) {
        create.deleteFrom(Tables.TIE_NOTICE).where(Tables.TIE_NOTICE.TIE_NOTICE_ARTICLE_INFO_ID.eq(id)).execute();
    }

    @Override
    public Result<Record3<Integer, String, Timestamp>> findByTieNoticeTimeIdOrBigTitleWithArticleOrderByDateDesc(int tieNoticeTimeId, String bigTitle) {
        Condition a = Tables.TIE_NOTICE.TIE_NOTICE_TIME_ID.eq(tieNoticeTimeId);

        if (StringUtils.hasLength(bigTitle)) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + bigTitle + "%"));
        }

        Result<Record3<Integer, String, Timestamp>> record3s = create.select(Tables.ARTICLE_INFO.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.ARTICLE_INFO.DATE)
                .from(Tables.TIE_NOTICE)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_NOTICE.TIE_NOTICE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .where(a)
                .orderBy(Tables.ARTICLE_INFO.DATE.desc())
                .fetch();
        return record3s;
    }

    @Override
    public Result<Record3<Integer, String, Timestamp>> findByTieIdAndPage(int tieId, int pageNum, int pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        Result<Record3<Integer, String, Timestamp>> record3s = create.select(Tables.ARTICLE_INFO.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.ARTICLE_INFO.DATE)
                .from(Tables.TIE_NOTICE)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_NOTICE.TIE_NOTICE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .where(Tables.TIE_NOTICE.TIE_ID.eq(tieId))
                .orderBy(Tables.ARTICLE_INFO.DATE.desc())
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();
        return record3s;
    }

    @Override
    public TieNotice findById(int id) {
        TieNotice tieNotice = tieNoticeDao.findById(id);
        return tieNotice;
    }

    @Override
    public void update(TieNotice tieNotice) {
        tieNoticeDao.update(tieNotice);
    }

    @Override
    public List<TieNotice> findByShow(Byte bytes) {
        List<TieNotice> tieNotices = tieNoticeDao.fetchByIsShow(bytes);
        return tieNotices;
    }
}
