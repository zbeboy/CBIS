package com.school.cbis.commons;

import com.school.cbis.data.SelectData;
import com.school.cbis.domain.tables.records.ArticleTypeRecord;
import com.school.cbis.domain.tables.records.FourItemsTypeRecord;
import com.school.cbis.domain.tables.records.TeachTypeRecord;
import com.school.cbis.domain.tables.records.UserTypeRecord;
import com.school.cbis.service.WordbookService;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

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
    public static final String USER_SUMMARY = "用户简介";

    private Map<String, Integer> articleTypeMap;

    //用户类型
    public static final String USER_TYPE_TEACHER = "教师";
    public static final String USER_TYPE_STUDENT = "学生";

    private Map<String, Integer> userTypeMap;

    //教学类型
    public static final String TEACH_TYPE_THEORY = "理论";
    public static final String TEACH_TYPE_PRACTICE = "实践";

    private Map<String, Integer> teachTypeMap;

    //四大件类型
    public static final String FOUR_ITEMS_TYPE_OUTLINE = "大纲";
    public static final String FOUR_ITEMS_TYPE_PLAN = "计划";
    public static final String FOUR_ITEMS_TYPE_SCHEDULE = "日程";
    public static final String FOUR_ITEMS_TYPE_PPT = "ppt";

    private Map<String, Integer> fourItemsTypeMap;

    //权限
    public static final String CBIS_ADMIN = "ROLE_ADMIN";
    public static final String CBIS_MAI = "ROLE_MAI";
    public static final String CBIS_SEMI = "ROLE_SEMI";
    public static final String CBIS_TEA = "ROLE_TEA";
    public static final String CBIS_STU = "ROLE_STU";

    private static final Map<String, String> roleMap;

    static{
        roleMap = new HashMap<>();
        roleMap.put(CBIS_ADMIN, "超级管理员");
        roleMap.put(CBIS_MAI, "管理员");
        roleMap.put(CBIS_SEMI, "教管");
        roleMap.put(CBIS_TEA, "教师");
        roleMap.put(CBIS_STU, "学生");
    }

    //邮箱开启
    @Value("${cbis.mail.switch}")
    public boolean mailSwitch;

    //每日限额
    @Value("${cbis.mail.limit}")
    public int dailyLimit;

    //阿里云邮箱服务
    @Value("${cbis.mail.user}")
    public String aliyunMailUser;

    @Value("${cbis.mail.password}")
    public String aliyunMailPassword;

    @Value("${cbis.mail.host}")
    public String aliyunSmtpHost;

    @Value("${cbis.mail.port}")
    public int aliyunSmtpPort;

    //短信接口 apikey  目前仅做测试用
    @Value("${cbis.mobile.apikey}")
    public String mobileApikey;

    //手机开启
    @Value("${cbis.mobile.switch}")
    public boolean mobileSwitch;

    //手机流量
    @Value("${cbis.mobile.flow}")
    public int mobileFlow;

    //rest 连接地址
    @Value("${cbis.server.address}")
    public String serverAddress;

    // security user
    @Value("${security.user.name}")
    public String securityUsername;

    @Value("${security.user.password}")
    public String securityUserPassword;

    //用户默认头像
    public static final String USER_DEFAULT_HEAD_IMG = "/images/placeholder_800x280.svg";

    private static final String mail_form = "863052317@qq.com";

    private static final int corePoolSize = 2;

    private static final int maxPoolSize = 50;

    private static final int queueCapacity = 10000;

    public static String getMail_form() {
        return mail_form;
    }

    public static int getCorePoolSize() {
        return corePoolSize;
    }

    public static int getMaxPoolSize() {
        return maxPoolSize;
    }

    public static int getQueueCapacity() {
        return queueCapacity;
    }

    public Map<String, String> getRoleMap() {
        return roleMap;
    }

    public String getRoleString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Set<Map.Entry<String, String>> set = getRoleMap().entrySet();
        set.forEach(m -> {
            builder.append("{");
            builder.append("\"authority\":\"" + m.getKey() + "\",");
            builder.append("\"role\":\"" + m.getValue() + "\"");
            builder.append("},");
        });
        return builder.toString().substring(0, builder.toString().lastIndexOf(",")) + "]";
    }

    public Map<String, Integer> getArticleTypeMap() {
        articleTypeMap = new HashMap<>();
        Result<ArticleTypeRecord> articleTypeRecordResult = wordbookService.articleType();
        articleTypeRecordResult.forEach(r -> {
            articleTypeMap.put(r.getName(), r.getId());
        });
        return articleTypeMap;
    }

    public Map<String, Integer> getUserTypeMap() {
        userTypeMap = new HashMap<>();
        Result<UserTypeRecord> userTypeRecordResult = wordbookService.userType();
        userTypeRecordResult.forEach(r -> {
            userTypeMap.put(r.getName(), r.getId());
        });
        return userTypeMap;
    }

    public Map<String, Integer> getTeachTypeMap() {
        teachTypeMap = new HashMap<>();
        Result<TeachTypeRecord> teachTypeRecordResult = wordbookService.teachType();
        teachTypeRecordResult.forEach(r -> {
            teachTypeMap.put(r.getName(), r.getId());
        });
        return teachTypeMap;
    }

    public Map<String, Integer> getFourItemsTypeMap() {
        fourItemsTypeMap = new HashMap<>();
        Result<FourItemsTypeRecord> fourItemsTypeRecordResult = wordbookService.fourItemsType();
        fourItemsTypeRecordResult.forEach(r -> {
            fourItemsTypeMap.put(r.getName(), r.getId());
        });
        return fourItemsTypeMap;
    }
}
