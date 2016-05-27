package com.school.cbis.service;

import com.school.cbis.domain.tables.records.*;
import org.jooq.Result;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by lenovo on 2016-01-15.
 */
@CacheConfig(cacheNames = "wordbook")
public interface WordbookService {
    /**
     * 文章类型
     *
     * @return 文章类型
     */
    @Cacheable(cacheNames = "articleType")
    Result<ArticleTypeRecord> articleType();

    /**
     * 用户类型
     *
     * @return 用户类型
     */
    @Cacheable(cacheNames = "userType")
    Result<UserTypeRecord> userType();

    /**
     * 教学类型
     *
     * @return 教学类型
     */
    @Cacheable(cacheNames = "teachType")
    Result<TeachTypeRecord> teachType();

    /**
     * 四大件类型
     *
     * @return 四大件类型
     */
    @Cacheable(cacheNames = "fourItemsType")
    Result<FourItemsTypeRecord> fourItemsType();

    /**
     * 用于默认系使用
     * @return
     */
    @Cacheable(cacheNames = "findTieInfo")
    TieRecord findTieInfo();
}
