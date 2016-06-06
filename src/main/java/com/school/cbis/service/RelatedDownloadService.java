package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.RelatedDownload;
import com.school.cbis.vo.eadmin.RelatedDownloadListVo;
import org.jooq.Record;
import org.jooq.Record10;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-06.
 */
public interface RelatedDownloadService {

    /**
     * 根据系id查询
     * @param tieId
     * @return
     */
    Result<Record10<Integer,String,String,String,Timestamp,Integer,Integer,String,String,String>> findByTieIdAndPage(RelatedDownloadListVo relatedDownloadListVo, int tieId);

    /**
     * 根据系id查询
     * @param relatedDownloadListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(RelatedDownloadListVo relatedDownloadListVo,int tieId);

    /**
     * 保存
     * @param relatedDownload
     */
    void save(RelatedDownload relatedDownload);

    /**
     * 更新
     * @param relatedDownload
     */
    void update(RelatedDownload relatedDownload);

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    RelatedDownload findById(int id);

    /**
     * 通过主键删除
     * @param id
     */
    void deleteById(int id);
}
