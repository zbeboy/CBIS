package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.ArticleInfo;
import org.jooq.Record7;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-01-12.
 */
public interface ArticleInfoService {
    /**
     * 保存文章信息
     *
     * @param articleInfo 文章信息
     * @return 返回文章信息表id
     */
    int save(ArticleInfo articleInfo);

    /**
     * 通过article_info id查询
     *
     * @param id 文章id
     * @return 文章信息数据
     */
    ArticleInfo findById(int id);

    /**
     * 通过文章id删除
     *
     * @param id 文章id
     */
    void deleteById(int id);

    /**
     * 根据文章id更新
     *
     * @param articleInfo
     */
    void update(ArticleInfo articleInfo);

    /**
     * 关联Users表查询
     * @param id
     * @return
     */
    Result<Record7<Integer, String, String, Integer, Timestamp, String, String>> findByIdWithUsers(int id);

}