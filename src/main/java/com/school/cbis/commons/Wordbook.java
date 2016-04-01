package com.school.cbis.commons;

import com.school.cbis.domain.tables.records.ArticleTypeRecord;
import com.school.cbis.domain.tables.records.FourItemsTypeRecord;
import com.school.cbis.domain.tables.records.TeachTypeRecord;
import com.school.cbis.domain.tables.records.UserTypeRecord;
import com.school.cbis.service.WordbookService;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016-01-12.
 * 全局常用变量配置
 */
@Component
public class Wordbook {

    private final Logger log = LoggerFactory.getLogger(Wordbook.class);

    @Resource
    private WordbookService wordbookService;

    //文章类型表
    public static final String TIE_ELEGANT = "系风采";
    public static final String TIE_BRING_IN_GOAL = "系培养目标";
    public static final String TIE_ITEM = "系特色";
    public static final String TIE_NOTICE = "系公告";
    public static final String TIE_SUMMARY = "系简介";
    public static final String TIE_LEAD = "系主任";
    public static final String MAJOR_SUMMARY = "专业简介";
    public static final String MAJOR_BRING_IN_GOAL = "专业培养目标";
    public static final String MAJOR_ITEM = "专业特色";
    public static final String MAJOR_LEAD = "专业带头人";
    public static final String STUDENT_SUMMARY = "学生简介";
    public static final String TEACHER_SUMMARY = "教师简介";
    public static final String RECRUIT_MOULD = "招聘模板";

    private Map<String ,Integer> articleTypeMap = new HashMap<>();

    //用户类型
    public static final String USER_TYPE_TEACHER = "教师";
    public static final String USER_TYPE_STUDENT = "学生";

    private Map<String ,Integer> userTypeMap = new HashMap<>();

    //教学类型
    public static final String TEACH_TYPE_THEORY = "理论";
    public static final String TEACH_TYPE_PRACTICE = "实践";

    private Map<String ,Integer> teachTypeMap = new HashMap<>();

    //四大件类型
    public static final String FOUR_ITEMS_TYPE_OUTLINE = "大纲";
    public static final String FOUR_ITEMS_TYPE_PLAN = "计划";
    public static final String FOUR_ITEMS_TYPE_SCHEDULE = "日程";
    public static final String FOUR_ITEMS_TYPE_PPT = "ppt";

    private Map<String ,Integer> fourItemsTypeMap = new HashMap<>();

    //权限
    public static final String CBIS_ADMIN = "ROLE_ADMIN";
    public static final String CBIS_MAI = "ROLE_MAI";
    public static final String CBIS_TM = "ROLE_SEMI";
    public static final String CBIS_TEA = "ROLE_TEA";
    public static final String CBIS_STU = "ROLE_STU";

    private Map<String,String> roleMap = new HashMap<>();

    public Map<String, String> getRoleMap() {
        roleMap.put(CBIS_ADMIN,"超级管理员");
        roleMap.put(CBIS_MAI,"管理员");
        roleMap.put(CBIS_TM,"教管");
        roleMap.put(CBIS_TEA,"教师");
        roleMap.put(CBIS_STU,"学生");
        return roleMap;
    }

    public Map<String, Integer> getArticleTypeMap() {
        Result<ArticleTypeRecord> articleTypeRecordResult = wordbookService.articleType();
        articleTypeRecordResult.forEach(r->{
            articleTypeMap.put(r.getName(),r.getId());
        });
        return articleTypeMap;
    }

    public Map<String, Integer> getUserTypeMap() {
        Result<UserTypeRecord> userTypeRecordResult = wordbookService.userType();
        userTypeRecordResult.forEach(r->{
            userTypeMap.put(r.getName(),r.getId());
        });
        return userTypeMap;
    }

    public Map<String, Integer> getTeachTypeMap() {
        Result<TeachTypeRecord> teachTypeRecordResult = wordbookService.teachType();
        teachTypeRecordResult.forEach(r->{
            teachTypeMap.put(r.getName(),r.getId());
        });
        return teachTypeMap;
    }

    public Map<String, Integer> getFourItemsTypeMap() {
        Result<FourItemsTypeRecord> fourItemsTypeRecordResult  = wordbookService.fourItemsType();
        fourItemsTypeRecordResult.forEach(r->{
            fourItemsTypeMap.put(r.getName(),r.getId());
        });
        return fourItemsTypeMap;
    }
}
