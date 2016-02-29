package com.school.cbis.service;

import com.school.cbis.domain.tables.records.ArticleTypeRecord;
import com.school.cbis.domain.tables.records.FourItemsTypeRecord;
import com.school.cbis.domain.tables.records.TeachTypeRecord;
import com.school.cbis.domain.tables.records.UserTypeRecord;
import org.jooq.Result;

/**
 * Created by lenovo on 2016-01-15.
 */
public interface WordbookService {
    /**
     * 文章类型
     *
     * @return 文章类型
     */
    Result<ArticleTypeRecord> articleType();

    /**
     * 用户类型
     *
     * @return 用户类型
     */
    Result<UserTypeRecord> userType();

    /**
     * 教学类型
     *
     * @return 教学类型
     */
    Result<TeachTypeRecord> teachType();

    /**
     * 四大件类型
     *
     * @return 四大件类型
     */
    Result<FourItemsTypeRecord> fourItemsType();
}
