package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.ArticleSub;

import java.util.List;

/**
 * Created by lenovo on 2016-02-21.
 */
public interface ArticleSubService {
    /**
     * 保存文章子标题信息
     *
     * @param articleSubs 文章子标题内容
     * @return 正确插入数据
     */
    void save(List<ArticleSub> articleSubs);

    /**
     * 通过article_info id查询 子内容
     *
     * @param articleInfoId 文章id
     * @return 文章子标题信息
     */
    List<ArticleSub> findByArticleInfoId(int articleInfoId);

    /**
     * 根据文章id删除
     *
     * @param id 文章id
     */
    void deleteByArticleInfoId(int id);
}
