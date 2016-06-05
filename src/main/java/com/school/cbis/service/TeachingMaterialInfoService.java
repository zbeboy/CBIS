package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskInfo;
import com.school.cbis.domain.tables.pojos.TeachingMaterialInfo;
import com.school.cbis.vo.eadmin.TeacherFillTaskInfoListVo;
import com.school.cbis.vo.eadmin.TeacherReportListVo;
import com.school.cbis.vo.eadmin.TeachingMaterialInfoListVo;
import com.school.cbis.vo.eadmin.TeachingMaterialListVo;
import org.jooq.Record7;
import org.jooq.Record9;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-04.
 */
public interface TeachingMaterialInfoService {

    /**
     * 根据系主键分页查询
     * @param tieId
     * @return
     */
    Result<Record7<Integer,String,Timestamp,String,Timestamp,Timestamp,String>> findByTieIdAndPage(TeachingMaterialInfoListVo teachingMaterialInfoListVo, int tieId);

    /**
     * 根据系主键分页查询总数
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(TeachingMaterialInfoListVo teachingMaterialInfoListVo,int tieId);

    /**
     * 保存
     * @param teachingMaterialInfo
     */
    void save(TeachingMaterialInfo teachingMaterialInfo);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TeachingMaterialInfo findById(int id);

    /**
     * 更新
     * @param teachingMaterialInfo
     */
    void update(TeachingMaterialInfo teachingMaterialInfo);

    /**
     * 查询相关全部信息
     * @param teachingMaterialListVo
     * @return
     */
    Result<Record9<Integer,Integer,Integer,String,Timestamp, Timestamp,String,String,String>> findAllAndPage(TeachingMaterialListVo teachingMaterialListVo, int tieId, int teachTypeId);

    /**
     * 查询相关全部信息
     * @param teachingMaterialListVo
     * @return
     */
    int findAllAndPageCount(TeachingMaterialListVo teachingMaterialListVo,int tieId,int teachTypeId);

}
