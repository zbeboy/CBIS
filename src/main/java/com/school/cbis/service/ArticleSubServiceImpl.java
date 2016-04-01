package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.ArticleSubDao;
import com.school.cbis.domain.tables.pojos.ArticleSub;
import org.jooq.BatchBindStep;
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
 * Created by lenovo on 2016-02-21.
 */
@Service("articleSubService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ArticleSubServiceImpl implements ArticleSubService {

    private final Logger log = LoggerFactory.getLogger(ArticleSubServiceImpl.class);

    private final DSLContext create;

    private ArticleSubDao articleSubDao;

    @Autowired
    public ArticleSubServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.articleSubDao = new ArticleSubDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(List<ArticleSub> articleSubs) {
        BatchBindStep bindStep = create.batch(create.insertInto(Tables.ARTICLE_SUB,
                Tables.ARTICLE_SUB.SUB_TITLE,
                Tables.ARTICLE_SUB.SUB_CONTENT,
                Tables.ARTICLE_SUB.ARTICLE_INFO_ID).values(
                null, null, (Integer) null
        ));
        articleSubs.forEach(r->{
            bindStep.bind(r.getSubTitle(), r.getSubContent(), r.getArticleInfoId());
        });
        bindStep.execute();
    }

    @Override
    public List<ArticleSub> findByArticleInfoId(int id) {
        List<ArticleSub> articleSubs = articleSubDao.fetchByArticleInfoId(id);
        return articleSubs;
    }

    @Override
    public void deleteByArticleInfoId(int id) {
        create.deleteFrom(Tables.ARTICLE_SUB).where(Tables.ARTICLE_SUB.ARTICLE_INFO_ID.eq(id)).execute();
    }
}
