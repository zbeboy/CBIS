package com.school.cbis.service;

import com.school.cbis.domain.tables.records.ArticleInfoRecord;
import com.school.cbis.domain.tables.records.ArticleSubRecord;
import com.school.cbis.domain.tables.records.TieElegantRecord;
import org.jooq.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-01-12.
 */
public interface ArticleService {

    /**
     * 保存文章信息
     *
     * @param record
     * @return 返回文章信息表id
     */
    int saveArticleInfo(ArticleInfoRecord record);

    /**
     * 保存文章子标题信息
     *
     * @param record
     * @return 正确插入数据
     */
    boolean saveArticleSub(List<ArticleSubRecord> record);

    /**
     * 通过article_info id查询
     * @param id 文章id
     * @return
     */
    ArticleInfoRecord findById(int id);

    /**
     * 通过article_info id查询 子内容
     * @param id 文章id
     * @return
     */
    Result<ArticleSubRecord> getArticleSubs(int id);

    /**
     * 通过子标题id删除
     * @param id 子标题id
     * @return
     */
    boolean deleteArticleSub(int id);

    /**
     * 通过文章id删除
     * @param id 文章id
     * @return
     */
    boolean deleteArticleInfo(int id);

    /**
     * 根据文章id更新
     * @param id 文章id
     * @return
     */
    boolean updateArticleInfo(ArticleInfoRecord articleInfoRecord,int id);

    /**
     * 根据文章id删除
     * @param id 文章id
     * @return
     */
    boolean deleteArticleSubByArticleInfoId(int id);
}
