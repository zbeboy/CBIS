package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.*;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2016-01-15.
 */
@Service("wordbookService")
public class WordbookServiceImpl implements WordbookService {

    private final Logger log = LoggerFactory.getLogger(WordbookServiceImpl.class);

    private final DSLContext create;

    @Autowired
    public WordbookServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public Result<ArticleTypeRecord> articleType() {
        return create.selectFrom(Tables.ARTICLE_TYPE).fetch();
    }

    @Override
    public Result<UserTypeRecord> userType() {
        return create.selectFrom(Tables.USER_TYPE).fetch();
    }

    @Override
    public Result<TeachTypeRecord> teachType() {
        return create.selectFrom(Tables.TEACH_TYPE).fetch();
    }

    @Override
    public Result<FourItemsTypeRecord> fourItemsType() {
        return create.selectFrom(Tables.FOUR_ITEMS_TYPE).fetch();
    }

    @Override
    public TieRecord findByTieName(String tieName) {
        TieRecord tieRecord = create.selectFrom(Tables.TIE)
                .where(Tables.TIE.TIE_NAME.eq(tieName))
                .fetchOne();
        return tieRecord;
    }
}
