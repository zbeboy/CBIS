package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TieNoticeAffix;

import java.util.List;

/**
 * Created by lenovo on 2016-03-15.
 */
public interface TieNoticeAffixService {
    /**
     * 保存
     *
     * @param tieNoticeAffix
     */
    void save(TieNoticeAffix tieNoticeAffix);

    /**
     * 通过文章id查询附件
     *
     * @param articleInfoId
     * @return
     */
    List<TieNoticeAffix> findByArticleInfoId(int articleInfoId);

    /**
     * 通过文章id删除
     *
     * @param articleInfoId
     */
    void deleteByArticleInfoId(int articleInfoId);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    TieNoticeAffix findById(int id);

    /**
     * 通过主键删除
     * @param id
     */
    void deleteById(int id);
}
