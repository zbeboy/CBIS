package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.ArticleInfoRecord;
import com.school.cbis.domain.tables.records.ArticleSubRecord;
import com.school.cbis.domain.tables.records.TieElegantRecord;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-01-12.
 */
@Service("articleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final DSLContext create;

    @Autowired
    public ArticleServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int saveArticleInfo(ArticleInfoRecord articleInfoRecord) {
        ArticleInfoRecord articleInfoRecord1 = create.insertInto(Tables.ARTICLE_INFO)
                .set(Tables.ARTICLE_INFO.BIG_TITLE, articleInfoRecord.getBigTitle())
                .set(Tables.ARTICLE_INFO.ARTICLE_WRITER, articleInfoRecord.getArticleWriter())
                .set(Tables.ARTICLE_INFO.ARTICLE_TYPE_ID, articleInfoRecord.getArticleTypeId())
                .set(Tables.ARTICLE_INFO.ARTICLE_CONTENT, articleInfoRecord.getArticleContent())
                .set(Tables.ARTICLE_INFO.ARTICLE_PHOTO_URL, articleInfoRecord.getArticlePhotoUrl())
                .returning(Tables.ARTICLE_INFO.ID)
                .fetchOne();
        return articleInfoRecord1.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public boolean saveArticleSub(List<ArticleSubRecord> record) {
        BatchBindStep bindStep = create.batch(create.insertInto(Tables.ARTICLE_SUB,
                Tables.ARTICLE_SUB.SUB_TITLE,
                Tables.ARTICLE_SUB.SUB_CONTENT,
                Tables.ARTICLE_SUB.SUB_PHOTO_URL,
                Tables.ARTICLE_SUB.ARTICLE_INFO_ID,
                Tables.ARTICLE_SUB.ROW).values(
                null, null, null, (Integer) null, 0
        ));

        for (ArticleSubRecord r : record) {
            bindStep.bind(r.getSubTitle(), r.getSubContent(), r.getSubPhotoUrl(), r.getArticleInfoId(), r.getRow());
        }

        int[] count = bindStep.execute();

        if (count.length > 0) {
            return true;
        }
        return false;
    }

    @Override
    public ArticleInfoRecord findById(int id) {
        ArticleInfoRecord record = create.selectFrom(Tables.ARTICLE_INFO).where(Tables.ARTICLE_INFO.ID.eq(id)).fetchOne();
        return record;
    }

    @Override
    public Result<ArticleSubRecord> getArticleSubs(int id) {
        Result<ArticleSubRecord> records = create.selectFrom(Tables.ARTICLE_SUB).where(Tables.ARTICLE_SUB.ARTICLE_INFO_ID.eq(id)).fetch();
        return records;
    }

    @Override
    public boolean deleteArticleSub(int id) {
        int count = create.deleteFrom(Tables.ARTICLE_SUB).where(Tables.ARTICLE_SUB.ID.eq(id)).execute();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteArticleInfo(int id) {
        int count = create.deleteFrom(Tables.ARTICLE_INFO).where(Tables.ARTICLE_INFO.ID.eq(id)).execute();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateArticleInfo(ArticleInfoRecord articleInfoRecord, int id) {
        int count = create.update(Tables.ARTICLE_INFO)
                .set(Tables.ARTICLE_INFO.BIG_TITLE, articleInfoRecord.getBigTitle())
                .set(Tables.ARTICLE_INFO.ARTICLE_CONTENT, articleInfoRecord.getArticleContent())
                .set(Tables.ARTICLE_INFO.ARTICLE_PHOTO_URL, articleInfoRecord.getArticlePhotoUrl())
                .where(Tables.ARTICLE_INFO.ID.eq(id)).execute();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteArticleSubByArticleInfoId(int id) {
        int count = create.deleteFrom(Tables.ARTICLE_SUB).where(Tables.ARTICLE_SUB.ARTICLE_INFO_ID.eq(id)).execute();
        if (count > 0) {
            return true;
        }
        return false;
    }
}
