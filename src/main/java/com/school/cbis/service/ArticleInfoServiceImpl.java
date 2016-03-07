package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.ArticleInfoDao;
import com.school.cbis.domain.tables.pojos.ArticleInfo;
import com.school.cbis.domain.tables.records.ArticleInfoRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record7;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-01-12.
 */
@Service("articleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ArticleInfoServiceImpl implements ArticleInfoService {

    private final DSLContext create;

    private ArticleInfoDao articleInfoDao;

    @Autowired
    public ArticleInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.articleInfoDao = new ArticleInfoDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int save(ArticleInfo articleInfo) {
        ArticleInfoRecord articleInfoRecord1 = create.insertInto(Tables.ARTICLE_INFO)
                .set(Tables.ARTICLE_INFO.BIG_TITLE, articleInfo.getBigTitle())
                .set(Tables.ARTICLE_INFO.ARTICLE_WRITER, articleInfo.getArticleWriter())
                .set(Tables.ARTICLE_INFO.ARTICLE_TYPE_ID, articleInfo.getArticleTypeId())
                .set(Tables.ARTICLE_INFO.ARTICLE_CONTENT, articleInfo.getArticleContent())
                .set(Tables.ARTICLE_INFO.ARTICLE_PHOTO_URL, articleInfo.getArticlePhotoUrl())
                .returning(Tables.ARTICLE_INFO.ID)
                .fetchOne();

        return articleInfoRecord1.getId();
    }

    @Override
    public ArticleInfo findById(int id) {
        ArticleInfo articleInfo = articleInfoDao.findById(id);
        return articleInfo;
    }

    @Override
    public void deleteById(int id) {
        articleInfoDao.deleteById(id);
    }

    @Override
    public void update(ArticleInfo articleInfo) {
        articleInfoDao.update(articleInfo);
    }

    @Override
    public Result<Record7<Integer, String, String, Integer, Timestamp, String, String>> findByIdWithUsers(int id) {
        Result<Record7<Integer, String, String, Integer, Timestamp, String, String>> record7s = create.select(Tables.ARTICLE_INFO.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.USERS.USER_TYPE_ID,
                Tables.ARTICLE_INFO.DATE, Tables.ARTICLE_INFO.ARTICLE_PHOTO_URL, Tables.ARTICLE_INFO.ARTICLE_CONTENT)
                .from(Tables.ARTICLE_INFO)
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(Tables.ARTICLE_INFO.ID.eq(id))
                .orderBy(Tables.ARTICLE_INFO.DATE.desc()).fetch();
        return record7s;
    }
}