package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskInfo;
import com.school.cbis.vo.eadmin.TeacherFillTaskInfoListVo;
import org.apache.poi.ss.formula.functions.T;
import org.jooq.Record;
import org.jooq.Record7;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-02.
 */
public interface TeacherFillTaskInfoService {

    /**
     * 根据系主键分页查询
     * @param tieId
     * @return
     */
    Result<Record7<Integer,String,Timestamp,String,Timestamp,Timestamp,String>> findByTieIdAndPage(TeacherFillTaskInfoListVo teacherFillTaskInfoListVo,int tieId);

    /**
     * 根据系主键分页查询总数
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(TeacherFillTaskInfoListVo teacherFillTaskInfoListVo,int tieId);

    /**
     * 保存
     * @param teacherFillTaskInfo
     */
    void save(TeacherFillTaskInfo teacherFillTaskInfo);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TeacherFillTaskInfo findById(int id);

    /**
     * 更新
     * @param teacherFillTaskInfo
     */
    void update(TeacherFillTaskInfo teacherFillTaskInfo);
}
