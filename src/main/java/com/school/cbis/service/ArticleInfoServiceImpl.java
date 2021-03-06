package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.ArticleInfoDao;
import com.school.cbis.domain.tables.pojos.ArticleInfo;
import com.school.cbis.domain.tables.records.ArticleInfoRecord;

import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-01-12.
 */
@Service("articleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ArticleInfoServiceImpl implements ArticleInfoService {

    private final Logger log = LoggerFactory.getLogger(ArticleInfoServiceImpl.class);

    private final DSLContext create;

    private ArticleInfoDao articleInfoDao;

    @Resource
    private Wordbook wordbook;

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
    public Result<Record8<Integer, String, String, Integer, Timestamp, String, String,String>> findByIdWithUsers(int id) {
        Result<Record8<Integer, String, String, Integer, Timestamp, String, String,String>> record8s = create.select(Tables.ARTICLE_INFO.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.USERS.USER_TYPE_ID,
                Tables.ARTICLE_INFO.DATE, Tables.ARTICLE_INFO.ARTICLE_PHOTO_URL, Tables.ARTICLE_INFO.ARTICLE_CONTENT,Tables.USERS.REAL_NAME)
                .from(Tables.ARTICLE_INFO)
                .leftJoin(Tables.USERS)
                .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.eq(Tables.USERS.USERNAME))
                .where(Tables.ARTICLE_INFO.ID.eq(id))
                .orderBy(Tables.ARTICLE_INFO.DATE.desc()).fetch();
        return record8s;
    }

    @Override
    public Record findByUsername(String username) {
            Record record = create.select()
                    .from(Tables.USERS)
                    .join(Tables.ARTICLE_INFO)
                    .on(Tables.USERS.INTRODUCE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                    .where(Tables.USERS.USERNAME.equal(username))
                    .fetchOne();
            return record;
    }
}
