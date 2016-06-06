package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherCourseTimetableInfo;
import com.school.cbis.vo.eadmin.TeacherTimetableListVo;
import org.jooq.Record;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Result;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-06.
 */
public interface TeacherCourseTimetableInfoService {

    /**
     * 根据系id查询
     * @param tieId
     * @return
     */
    Result<Record14<Integer,String,String,String,String,String,Timestamp,Integer,Integer,Date,Date,String,String,String>> findByTieIdAndPage(TeacherTimetableListVo teacherTimetableListVo, int tieId);

    /**
     * 根据系id查询总数
     * @param teacherTimetableListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(TeacherTimetableListVo teacherTimetableListVo,int tieId);

    /**
     * 保存
     * @param teacherCourseTimetableInfo
     */
    void save(TeacherCourseTimetableInfo teacherCourseTimetableInfo);

    /**
     * 更新
     * @param teacherCourseTimetableInfo
     */
    void update(TeacherCourseTimetableInfo teacherCourseTimetableInfo);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TeacherCourseTimetableInfo findById(int id);

    /**
     * 根据主键删除
     * @param id
     */
    void deleteById(int id);
}
