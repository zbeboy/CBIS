package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachTaskInfo;
import com.school.cbis.domain.tables.records.TeachTaskInfoRecord;
import com.school.cbis.vo.eadmin.AssignmentBookListVo;
import org.jooq.Record;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Result;

import java.sql.Date;
import java.util.List;

/**
 * Created by lenovo on 2016-05-28.
 */
public interface TeachTaskInfoService {

    /**
     * 保存
     *
     * @param teachTaskInfo
     */
    int saveAndReturnId(TeachTaskInfo teachTaskInfo);

    /**
     * 通过标题查找
     *
     * @param teachTaskInfoTitle
     * @return
     */
    List<TeachTaskInfo> findByTeachTaskInfoTitle(String teachTaskInfoTitle);

    /**
     * 分页查询全部
     *
     * @param tieId
     * @return
     */
    Result<Record7<Integer, String, String, String, Date, Date, Byte>> findAllByTieIdAndPageAndTeachTypeId(AssignmentBookListVo assignmentBookListVo, int tieId, int teachTypeId);

    /**
     * 分页查询全部总数
     *
     * @param assignmentBookListVo
     * @param tieId
     * @return
     */
    int findAllByTieIdAndPageAndTeachTypeIdCount(AssignmentBookListVo assignmentBookListVo, int tieId, int teachTypeId);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    TeachTaskInfo findById(int id);

    /**
     * 更新
     *
     * @param teachTaskInfo
     */
    void update(TeachTaskInfo teachTaskInfo);

    /**
     * 更新检验标题用 注:是不等于id
     *
     * @param id
     * @param teachTaskInfoTitle
     * @return
     */
    Result<TeachTaskInfoRecord> findByIdAndTeachTaskInfoTitle(int id, String teachTaskInfoTitle);
}
