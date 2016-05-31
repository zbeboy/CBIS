package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachTaskInfo;
import com.school.cbis.vo.eadmin.AssignmentBookListVo;
import org.jooq.Record;
import org.jooq.Record6;
import org.jooq.Result;

import java.sql.Date;
import java.util.List;

/**
 * Created by lenovo on 2016-05-28.
 */
public interface TeachTaskInfoService {

    /**
     * 保存
     * @param teachTaskInfo
     */
    int saveAndReturnId(TeachTaskInfo teachTaskInfo);

    /**
     * 通过标题查找
     * @param teachTaskInfoTitle
     * @return
     */
    List<TeachTaskInfo> findByTeachTaskInfoTitle(String teachTaskInfoTitle);

    /**
     * 分页查询全部
     * @param tieId
     * @return
     */
    Result<Record6<Integer,String,String,String,Date,Date>> findAllByTieIdAndPage(AssignmentBookListVo assignmentBookListVo, int tieId);

    /**
     * 分页查询全部总数
     * @param assignmentBookListVo
     * @param tieId
     * @return
     */
    int findAllByTieIdAndPageCount(AssignmentBookListVo assignmentBookListVo,int tieId);
}
