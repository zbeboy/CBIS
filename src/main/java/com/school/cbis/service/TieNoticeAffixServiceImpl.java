package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TieNoticeAffixDao;
import com.school.cbis.domain.tables.pojos.TieNoticeAffix;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2016-03-15.
 */
@Service("tieNoticeAffixService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieNoticeAffixServiceImpl implements TieNoticeAffixService {

    private final Logger log = LoggerFactory.getLogger(TieNoticeAffixServiceImpl.class);

    private final DSLContext create;

    private TieNoticeAffixDao tieNoticeAffixDao;

    @Autowired
    public TieNoticeAffixServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.tieNoticeAffixDao = new TieNoticeAffixDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TieNoticeAffix tieNoticeAffix) {
        create.insertInto(Tables.TIE_NOTICE_AFFIX)
                .set(Tables.TIE_NOTICE_AFFIX.TIE_NOTICE_FILE_SIZE, tieNoticeAffix.getTieNoticeFileSize())
                .set(Tables.TIE_NOTICE_AFFIX.TIE_NOTICE_FILE_URL, tieNoticeAffix.getTieNoticeFileUrl())
                .set(Tables.TIE_NOTICE_AFFIX.TIE_NOTICE_FILE_NAME, tieNoticeAffix.getTieNoticeFileName())
                .set(Tables.TIE_NOTICE_AFFIX.ARTICLE_INFO_ID, tieNoticeAffix.getArticleInfoId())
                .set(Tables.TIE_NOTICE_AFFIX.FILE_USER, tieNoticeAffix.getFileUser())
                .set(Tables.TIE_NOTICE_AFFIX.FILE_TYPE, tieNoticeAffix.getFileType())
                .execute();
    }

    @Override
    public List<TieNoticeAffix> findByArticleInfoId(int articleInfoId) {
        List<TieNoticeAffix> tieNoticeAffices = tieNoticeAffixDao.fetchByArticleInfoId(articleInfoId);
        return tieNoticeAffices;
    }

    @Override
    public void deleteByArticleInfoId(int articleInfoId) {
        create.deleteFrom(Tables.TIE_NOTICE_AFFIX)
                .where(Tables.TIE_NOTICE_AFFIX.ARTICLE_INFO_ID.eq(articleInfoId)).execute();
    }

    @Override
    public TieNoticeAffix findById(int id) {
        TieNoticeAffix tieNoticeAffix = tieNoticeAffixDao.findById(id);
        return tieNoticeAffix;
    }
}
