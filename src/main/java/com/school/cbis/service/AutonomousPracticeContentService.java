package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.AutonomousPracticeContent;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeHead;
import com.school.cbis.domain.tables.records.AutonomousPracticeContentRecord;
import com.school.cbis.domain.tables.records.AutonomousPracticeHeadRecord;
import org.jooq.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lenovo on 2016-04-12.
 */
public interface AutonomousPracticeContentService {

    /**
     * 通过自主实习标题表主键删除
     *
     * @param autonomousPracticeHeadId
     */
    void deleteByAutonomousPracticeHeadId(int autonomousPracticeHeadId);

    /**
     * 通过自主实习标题表主键与学生id查询
     *
     * @param autonomousPracticeTemplateId
     * @param studentId
     * @return
     */
    Result<Record4<Integer,String,Integer,Integer>> findByAutonomousPracticeTemplateIdAndStudentId(int autonomousPracticeTemplateId, int studentId);

    /**
     * 保存内容
     * @param autonomousPracticeContent
     */
    void save(AutonomousPracticeContent autonomousPracticeContent);

    /**
     * 通过自主实习标题表主键与学生主键删除
     * @param autonomousPracticeHeadId
     * @param studentId
     */
    void deleteByAutonomousPracticeHeadIdAndStudentId(int autonomousPracticeHeadId,int studentId);

    /**
     * distinct studentid查询并分页所有数据
     * @param request
     * @return
     */
    Result<Record1<Integer>> findByAutonomousPracticeTemplateIdDistinctAndPage(HttpServletRequest request);

    /**
     * distinct studentid查询并分页所有数据总数
     * @param request
     * @return
     */
    int findByAutonomousPracticeTemplateIdDistinctCount(HttpServletRequest request);

    /**
     * 查询标题内容包括权限在内
     * @param autonomousPracticeTemplateId
     * @param studentId
     * @return
     */
    Result<Record3<String, String, String>> findByAutonomousPracticeTemplateIdAndStudentIdWithAuthority(int autonomousPracticeTemplateId, int studentId);

    /**
     * 根据id更新
     * @param autonomousPracticeContent
     */
    void update(AutonomousPracticeContent autonomousPracticeContent);
}
