package com.school.cbis.service;

import com.school.cbis.domain.tables.records.TieElegantRecord;
import com.school.cbis.domain.tables.records.TieElegantTimeRecord;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-01-24.
 */
public interface TieElegantService {

    /**
     * 从系风采时间表中查询是否存在该时间
     * @param time xxxx年xx月
     * @return
     */
    Result<TieElegantTimeRecord> findByTime(String time);

    /**
     * 若时间组表中不存在该时间则插入该时间并获取id
     * @param tieElegantTimeRecord
     * @return id
     */
    int saveTime(TieElegantTimeRecord tieElegantTimeRecord);

    /**
     * 保存文章信息到系风采表
     *
     * @param record
     * @return id
     */
    boolean saveTieElegant(TieElegantRecord record);

    /**
     * 查询所有系风采标题和内容
     * @param tie_id 系id
     * @return 系风采标题和内容
     */
    Result<Record3<Integer, String, String>> searchItems(String big_title, int tie_id);


    /**
     * 分页查询所有系风采文章信息
     * @param bigTitle 为空时查询所有
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @param tie_id 系id
     * @return 文章数据
     */
    Result<Record4<Integer, String, String, Timestamp>> getTieElegantInfoByPage(String bigTitle, int pageNum, int pageSize, int tie_id);

    /**
     * 条件查询总数
     * @param bigTitle 标题
     * @param tie_id 系id
     * @return
     */
    int tieElegantInfoCount(String bigTitle,int tie_id);

    /**
     * 通过文章id删除
     * @param id 文章id
     * @return
     */
    boolean deleteTieElegant(int id);

    /**
     * 通过文章id更新img信息
     * @param id 文章id
     * @return
     */
    boolean deleteTieElegantImg(int id);
}
