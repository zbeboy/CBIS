package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo;
import com.school.cbis.domain.tables.records.ClassroomCourseTimetableInfoRecord;
import com.school.cbis.vo.eadmin.ClassroomTimetableListVo;
import org.jooq.Result;

/**
 * Created by lenovo on 2016-06-06.
 */
public interface ClassroomCourseTimetableInfoService {

    /**
     * 根据系id查询
     * @param tieId
     * @return
     */
    Result<ClassroomCourseTimetableInfoRecord> findByTieIdAndPage(ClassroomTimetableListVo classroomTimetableListVo, int tieId);

    /**
     * 根据系id查询总数
     * @param classroomTimetableListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(ClassroomTimetableListVo classroomTimetableListVo,int tieId);

    /**
     * 保存
     * @param classroomCourseTimetableInfo
     */
    void save(ClassroomCourseTimetableInfo classroomCourseTimetableInfo);

    /**
     * 更新
     * @param classroomCourseTimetableInfo
     */
    void update(ClassroomCourseTimetableInfo classroomCourseTimetableInfo);

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    ClassroomCourseTimetableInfo findById(int id);

    /**
     * 根据主键查询
     * @param id
     */
    void deleteById(int id);
}
