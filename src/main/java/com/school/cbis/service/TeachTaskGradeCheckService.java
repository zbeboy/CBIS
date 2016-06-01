package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachTaskGradeCheck;
import com.school.cbis.domain.tables.records.TeachTaskGradeCheckRecord;
import org.jooq.Result;

/**
 * Created by lenovo on 2016-05-30.
 */
public interface TeachTaskGradeCheckService {

    /**
     * 保存
     * @param teachTaskGradeCheck
     */
    void save(TeachTaskGradeCheck teachTaskGradeCheck);

    /**
     * 根据教学任务书信息表主键和是否检验状态查询
     * @param taskInfoId
     * @param checkIsRight
     * @return
     */
    Result<TeachTaskGradeCheckRecord> findByTeachTaskInfoAndCheckIsRight(int taskInfoId,Byte checkIsRight);
}
