package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.ArticleTypeRecord;
import com.school.cbis.domain.tables.records.FourItemsTypeRecord;
import com.school.cbis.domain.tables.records.TeachTypeRecord;
import com.school.cbis.domain.tables.records.UserTypeRecord;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2016-01-15.
 */
@Service("wordbookService")
public class WordbookServiceImpl implements WordbookService {

    private static Logger logger = Logger.getLogger(WordbookServiceImpl.class);

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
}
