package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TieElegantDao;
import com.school.cbis.domain.tables.pojos.TieElegant;
import com.school.cbis.vo.tie.TieElegantVo;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-01-24.
 */
@Service("tieElegantService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieElegantServiceImpl implements TieElegantService {

    private final DSLContext create;

    private TieElegantDao tieElegantDao;

    @Autowired
    public TieElegantServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.tieElegantDao = new TieElegantDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TieElegant tieElegant) {
        create.insertInto(Tables.TIE_ELEGANT)
                .set(Tables.TIE_ELEGANT.TIE_ID, tieElegant.getTieId())
                .set(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID, tieElegant.getTieElegantArticleInfoId())
                .set(Tables.TIE_ELEGANT.TIE_ELEGANT_TIME_ID, tieElegant.getTieElegantTimeId())
                .execute();
    }

    @Override
    public Result<Record5<Integer, String, String, Timestamp,Byte>> findByTieIdWithBigTitleAndPage(TieElegantVo tieElegantVo, int tie_id) {
        Condition a = Tables.TIE_ELEGANT.TIE_ID.eq(tie_id);

        SortField<Integer> b = Tables.TIE_ELEGANT.ID.desc();

        SortField<String> c = null;

        SortField<Timestamp> d = null;


        if (StringUtils.hasLength(tieElegantVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + tieElegantVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(tieElegantVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + tieElegantVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(tieElegantVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + tieElegantVo.getDate() + "%"));
        }

        if( !StringUtils.isEmpty(tieElegantVo.getShow()) ){
            if(tieElegantVo.getShow()){
                Byte bytes = 1;
                a = a.and(Tables.TIE_ELEGANT.IS_SHOW.eq(bytes));
            } else {
                Byte bytes = 0;
                a = a.and(Tables.TIE_ELEGANT.IS_SHOW.eq(bytes));
            }
        }

        SelectConditionStep<Record5<Integer, String, String, Timestamp,Byte>> e = create.select(Tables.TIE_ELEGANT.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.ARTICLE_INFO.DATE,Tables.TIE_ELEGANT.IS_SHOW)
                .from(Tables.TIE_ELEGANT)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                .join(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.equal(Tables.USERS.USERNAME))
                .where(a);

        if (StringUtils.hasLength(tieElegantVo.getSortField())) {
            if (tieElegantVo.getSortField().equals("bigTitle")) {
                if (tieElegantVo.getSortOrder().equals("desc")) {
                    c = Tables.ARTICLE_INFO.BIG_TITLE.desc();
                } else {
                    c = Tables.ARTICLE_INFO.BIG_TITLE.asc();
                }
            } else if (tieElegantVo.getSortField().equals("username")) {
                if (tieElegantVo.getSortOrder().equals("desc")) {
                    c = Tables.USERS.USERNAME.desc();
                } else {
                    c = Tables.USERS.USERNAME.asc();
                }
            } else if (tieElegantVo.getSortField().equals("date")) {
                if (tieElegantVo.getSortOrder().equals("desc")) {
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

        return e.limit((tieElegantVo.getPageIndex() - 1) * tieElegantVo.getPageSize(), tieElegantVo.getPageSize()).fetch();

    }

    @Override
    public int findByTieIdWithBigTitleAndCount(TieElegantVo tieElegantVo, int tie_id) {

        Condition a = Tables.TIE_ELEGANT.TIE_ID.equal(tie_id);

        if (StringUtils.hasLength(tieElegantVo.getBigTitle())) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + tieElegantVo.getBigTitle() + "%"));
        }

        if (StringUtils.hasLength(tieElegantVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + tieElegantVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(tieElegantVo.getDate())) {
            a = a.and(Tables.ARTICLE_INFO.DATE.like("%" + tieElegantVo.getDate() + "%"));
        }

        if( !StringUtils.isEmpty(tieElegantVo.getShow()) ){
            if(tieElegantVo.getShow()){
                Byte bytes = 1;
                a = a.and(Tables.TIE_ELEGANT.IS_SHOW.eq(bytes));
            } else {
                Byte bytes = 0;
                a = a.and(Tables.TIE_ELEGANT.IS_SHOW.eq(bytes));
            }
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.TIE_ELEGANT)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                .join(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.equal(Tables.USERS.USERNAME))
                .where(a).fetchOne();

        return count.value1();
    }

    @Override
    public void deleteById(int id) {
        create.deleteFrom(Tables.TIE_ELEGANT).where(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.eq(id)).execute();
    }

    @Override
    public void update(TieElegant tieElegant) {
        tieElegantDao.update(tieElegant);
    }

    @Override
    public TieElegant findById(int id) {
        TieElegant tieElegant = tieElegantDao.findById(id);
        return tieElegant;
    }

    @Override
    public Result<Record4<Integer, String,String,String>> findByTieIdWithArticleOrderByDateDescAndPage(int tieId, int pageNum, int pageSize) {
        Result<Record4<Integer, String,String,String>> record4s = create.select(Tables.ARTICLE_INFO.ID, Tables.ARTICLE_INFO.BIG_TITLE,Tables.ARTICLE_INFO.ARTICLE_PHOTO_URL,Tables.ARTICLE_INFO.ARTICLE_CONTENT)
                .from(Tables.TIE_ELEGANT)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .where(Tables.TIE_ELEGANT.TIE_ID.eq(tieId))
                .orderBy(Tables.ARTICLE_INFO.DATE.desc())
                .limit(pageNum, pageSize).fetch();
        return record4s;
    }

    @Override
    public Result<Record3<Integer, String,Timestamp>> findByTieElegantTimeIdOrBigTitleWithArticleOrderByDateDesc(int tieElegantTimeId, String bigTitle) {

        Condition a = Tables.TIE_ELEGANT.TIE_ELEGANT_TIME_ID.eq(tieElegantTimeId);

        if (StringUtils.hasLength(bigTitle)) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + bigTitle + "%"));
        }

        Result<Record3<Integer, String,Timestamp>> record3s = create.select(Tables.ARTICLE_INFO.ID, Tables.ARTICLE_INFO.BIG_TITLE,Tables.ARTICLE_INFO.DATE)
                .from(Tables.TIE_ELEGANT)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .where(a)
                .orderBy(Tables.ARTICLE_INFO.DATE.desc())
                .fetch();
        return record3s;
    }

    @Override
    public List<TieElegant> findByShow(Byte bytes) {
        List<TieElegant> tieElegants = tieElegantDao.fetchByIsShow(bytes);
        return tieElegants;
    }
}
