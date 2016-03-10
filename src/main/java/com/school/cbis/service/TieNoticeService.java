package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TieElegant;
import com.school.cbis.domain.tables.pojos.TieNotice;
import com.school.cbis.vo.tie.TieNoticeVo;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-03-09.
 */
public interface TieNoticeService {
    /**
     * 保存文章信息到系公告表
     *
     * @param tieNotice
     * @return id
     */
    void save(TieNotice tieNotice);

    /**
     * 分页模糊搜索
     *
     * @param tieNoticeVo
     * @param tie_id
     * @return
     */
    Result<Record4<Integer, String, String, Timestamp>> findByTieIdWithBigTitleAndPage(TieNoticeVo tieNoticeVo, int tie_id);

    /**
     * 查询总数
     *
     * @param tieNoticeVo
     * @param tie_id
     * @return
     */
    int findByTieIdWithBigTitleAndCount(TieNoticeVo tieNoticeVo, int tie_id);

    /**
     * 通过文章id删除
     *
     * @param id 文章id
     * @return
     */
    void deleteById(int id);

    /**
     * 通过系风采时间组查询
     * @param tieNoticeTimeId
     * @return
     */
    Result<Record3<Integer, String,Timestamp>> findByTieNoticeTimeIdOrBigTitleWithArticleOrderByDateDesc(int tieNoticeTimeId, String bigTitle);
}
