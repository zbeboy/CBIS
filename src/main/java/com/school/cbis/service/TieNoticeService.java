package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TieElegant;
import com.school.cbis.domain.tables.pojos.TieNotice;
import com.school.cbis.domain.tables.pojos.TieNoticeAffix;
import com.school.cbis.domain.tables.records.TieNoticeAffixRecord;
import com.school.cbis.vo.tie.TieNoticeVo;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-03-09.
 */
public interface TieNoticeService {
    /**
     * 保存文章信息到系公告表
     *
     * @param tieNotice
     */
    void save(TieNotice tieNotice);

    /**
     * 分页模糊搜索
     *
     * @param tieNoticeVo
     * @param tie_id
     * @return
     */
    Result<Record5<Integer, String, String, Timestamp,Byte>> findByTieIdWithBigTitleAndPage(TieNoticeVo tieNoticeVo, int tie_id);

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
     *
     * @param tieNoticeTimeId
     * @return
     */
    Result<Record3<Integer, String, Timestamp>> findByTieNoticeTimeIdOrBigTitleWithArticleOrderByDateDesc(int tieNoticeTimeId, String bigTitle);

    /**
     * 通过系id分页查询
     *
     * @param tieId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result<Record3<Integer, String, Timestamp>> findByTieIdAndPage(int tieId, int pageNum, int pageSize);

    /**
     * 通过公告id查询
     * @param id
     * @return
     */
    TieNotice findById(int id);

    /**
     * 更新
     * @param tieNotice
     */
    void update(TieNotice tieNotice);

    /**
     * 通过show字段查询
     * @param bytes
     * @return
     */
    List<TieNotice> findByShow(Byte bytes);
}
