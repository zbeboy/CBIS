package com.school.cbis.web.system;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.SystemLog;
import com.school.cbis.domain.tables.records.SystemLogRecord;
import com.school.cbis.service.SystemLogService;
import com.school.cbis.service.UsersService;
import com.school.cbis.vo.system.EmailInfoVo;
import com.school.cbis.vo.system.MobileInfoVo;
import com.school.cbis.vo.system.SystemLogListVo;
import com.school.cbis.vo.system.SystemLogVo;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-05-25.
 */
@Controller
public class SystemController {

    private final Logger log = LoggerFactory.getLogger(SystemController.class);

    @Resource
    private Wordbook wordbook;

    @Resource
    private UsersService usersService;

    @Resource
    private SystemLogService systemLogService;

    /**
     * 系统管理界面
     * @return
     */
    @RequestMapping("/maintainer/system/systemManager")
    public String systemManager() {
        return "/maintainer/system/systeminfo";
    }

    /**
     * 系统管理界面数据
     * @return
     */
    @RequestMapping("/maintainer/system/systemManagerData")
    @ResponseBody
    public AjaxData systemManagerData() {
        Map<String, Object> map = new HashMap<>();
        // health
        String health = sendHttpGet("/health");
        map.put("health", health);

        //env
        String env = sendHttpGet("/env");
        map.put("env", env);

        //info
        String info = sendHttpGet("/info");
        map.put("info", info);

        //email
        EmailInfoVo emailInfoVo = new EmailInfoVo();
        emailInfoVo.setSwitch(wordbook.mailSwitch);
        emailInfoVo.setUsername(wordbook.aliyunMailUser);
        emailInfoVo.setPassword(wordbook.aliyunMailPassword);
        emailInfoVo.setHost(wordbook.aliyunSmtpHost);
        emailInfoVo.setPort(wordbook.aliyunSmtpPort + "");
        map.put("email", emailInfoVo);

        //mobile
        MobileInfoVo mobileInfoVo = new MobileInfoVo();
        mobileInfoVo.setSwitch(wordbook.mobileSwitch);
        mobileInfoVo.setApikey(wordbook.mobileApikey);
        map.put("mobile", mobileInfoVo);
        return new AjaxData().success().mapData(map);
    }

    /**
     * 系统指标界面
     * @return
     */
    @RequestMapping("/maintainer/system/systemMetrics")
    public String systemMetrics() {
        return "/maintainer/system/systemmetrics";
    }

    /**
     * 系统指标界面数据
     * @return
     */
    @RequestMapping("/maintainer/system/systemMetricsData")
    @ResponseBody
    public AjaxData systemMetricsData() {
        Map<String, Object> map = new HashMap<>();
        //metrics
        String metrics = sendHttpGet("/metrics");
        map.put("metrics", metrics);
        return new AjaxData().success().mapData(map);
    }

    @RequestMapping("/maintainer/system/systemLog")
    public String systemLog(SystemLogListVo systemLogListVo, ModelMap modelMap){
        modelMap.addAttribute("systemLogListVo",systemLogListVo);
        return "/maintainer/system/systemlog";
    }

    @RequestMapping("/maintainer/system/systemLogData")
    @ResponseBody
    public AjaxData<SystemLogVo> systemLogData(SystemLogListVo systemLogListVo){
        AjaxData<SystemLogVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }

        List<SystemLogVo> systemLogs = new ArrayList<>();
        if(tieId>0){
            Result<SystemLogRecord> records = systemLogService.findByTieIdAndPage(systemLogListVo,tieId);
            if(records.isNotEmpty()){
                systemLogs = records.into(SystemLogVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(systemLogListVo.getPageNum());
                paginationData.setPageSize(systemLogListVo.getPageSize());
                paginationData.setTotalDatas(systemLogService.findByTieIdAndPageCount(systemLogListVo,tieId));
                ajaxData.success().listData(systemLogs).paginationData(paginationData);
            } else {
                ajaxData.fail().listData(systemLogs);
            }
        } else {
            ajaxData.fail().listData(systemLogs);
        }
       return ajaxData;
    }

    /**
     * spring boot actuator http send
     *
     * @param url
     * @return
     */
    public String sendHttpGet(String url) {
        String content = "";
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(wordbook.serverAddress + url);
        log.debug(" to connection address : {} ", wordbook.serverAddress + url);
        String header = "Basic " + new String(Base64.encode((wordbook.securityUsername + ":" + wordbook.securityUserPassword).getBytes()));
        log.debug(" http client request header is {} ", header);
        getMethod.setRequestHeader("Authorization", header);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                log.debug("Method failed: {} ", getMethod.getStatusLine());
            } else {
                content = getMethod.getResponseBodyAsString();
                log.debug(" content : {} ", content);
            }
        } catch (HttpException e) {
            log.error(" http send exception is {} ", e.getMessage());
        } catch (IOException e) {
            log.error(" http connection exception is {} ", e.getMessage());
        } finally {
            // 释放连接
            getMethod.releaseConnection();
            log.debug(" http release connection success ! ");
        }
        return content;
    }
}
