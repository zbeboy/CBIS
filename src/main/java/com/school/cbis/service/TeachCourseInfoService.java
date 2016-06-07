package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachCourseInfo;
import com.school.cbis.vo.eadmin.TeachingProcessListVo;
import org.jooq.Record;
import org.jooq.Record13;
import org.jooq.Result;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-05.
 */
public interface TeachCourseInfoService {

    /**
     * 根据tieId查询并获取users信息
     * @param teachingProcessListVo
     * @param tieId
     * @return
     */
    Result<Record13<Integer, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date, String, String>>  findByTieIdAndTeachTypeIdAndPageWithUsers(TeachingProcessListVo teachingProcessListVo, int tieId);

    /**
     * 根据tieId查询并获取users信息总数
     * @param teachingProcessListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndTeachTypeIdAndPageWithUsersCount(TeachingProcessListVo teachingProcessListVo,int tieId);

    /**
     * 保存
     * @param teachCourseInfo
     */
    void save(TeachCourseInfo teachCourseInfo);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TeachCourseInfo findById(int id);

    /**
     * 更新
     * @param teachCourseInfo
     */
    void update(TeachCourseInfo teachCourseInfo);

    /**
     * 根据主键删除
     * @param id
     */
    void deleteById(int id);
}
