package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachTaskInfo;
import com.school.cbis.domain.tables.pojos.Users;
import com.school.cbis.vo.eadmin.AssignmentBookAddVo;

/**
 * Created by lenovo on 2016-05-30.
 */
public interface FileService {

    /**
     * 读取教学任务书
     * @param realPath 文件路径
     * @param ext 文件后缀
     * @param assignmentBookAddVo 填写字段
     * @param teachTaskInfo 需要保存的信息
     * @param users session users
     * @param baseUrl 邮件需要
     */
    void readFileForTeachTaskInfo(String realPath, String ext, AssignmentBookAddVo assignmentBookAddVo, TeachTaskInfo teachTaskInfo, Users users,String baseUrl);

    /**
     * office 转换成 pdf
     * @param inputFile
     * @param pdfFile
     * @return
     */
    boolean convert2PDF(String inputFile, String pdfFile);
}
