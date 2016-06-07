package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.StudentCourseTimetableInfo;
import com.school.cbis.vo.eadmin.StudentTimetableListVo;
import org.jooq.Record;
import org.jooq.Record14;
import org.jooq.Result;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-06.
 */
public interface StudentCourseTimetableInfoService {

    /**
     * 根据系id查询分页
     * @param studentTimetableListVo
     * @param tieId
     * @return
     */
    Result<Record14<Integer,String,String,String,String,String,String,Timestamp,Integer,Integer,Date,Date,String,String>> findByTieIdAndTeachTypeIdAndPage(StudentTimetableListVo studentTimetableListVo,int tieId);

    /**
     * 根据系id查询分页总数
     * @param studentTimetableListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndTeachTypeIdAndPageCount(StudentTimetableListVo studentTimetableListVo,int tieId);

    /**
     * 保存
     * @param studentCourseTimetableInfo
     */
    void save(StudentCourseTimetableInfo studentCourseTimetableInfo);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    StudentCourseTimetableInfo findById(int id);

    /**
     * 更新
     * @param studentCourseTimetableInfo
     */
    void update(StudentCourseTimetableInfo studentCourseTimetableInfo);

    /**
     * 根据主键删除
     * @param id
     */
    void deleteById(int id);
}
