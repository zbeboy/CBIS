package com.school.cbis.web.mail;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.service.MailboxCountService;
import com.school.cbis.vo.mail.MailListVo;
import com.school.cbis.vo.major.MajorListVo;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016-05-19.
 */
@Controller
public class MailController {

    private final Logger log = LoggerFactory.getLogger(MailController.class);

    @Resource
    private MailboxCountService mailboxCountService;

    @Resource
    private Wordbook wordbook;

    /**
     * 邮箱管理列表数据
     * @param mailListVo
     * @return
     */
    @RequestMapping("/maintainer/mail/mailListData")
    @ResponseBody
    public AjaxData<MailListVo> mailListData(MailListVo mailListVo){
        Result<Record> records = mailboxCountService.findAllAndPage(mailListVo);//数据
        List<MailListVo> mailListVos = new ArrayList<>();
        if(records.isNotEmpty()){
            mailListVos = records.into(MailListVo.class);
        }
        //分页参数
        PaginationData paginationData = new PaginationData();
        paginationData.setPageNum(mailListVo.getPageNum());
        paginationData.setPageSize(mailListVo.getPageSize());
        paginationData.setTotalDatas(mailboxCountService.findAllAndPageCount(mailListVo));
        //当日已发送数
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("currentDate",sdf.format(new Date()));
        map.put("dailySendNum",mailboxCountService.dailySendNum());
        map.put("dailyLimit",wordbook.dailyLimit);
        return new AjaxData<MailListVo>().success().mapData(map).listData(mailListVos).paginationData(paginationData);
    }
}
