package com.school.cbis.web.mobile;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.service.MailboxCountService;
import com.school.cbis.service.MobileCountService;
import com.school.cbis.service.MobileService;
import com.school.cbis.vo.mail.MailListVo;
import com.school.cbis.vo.mobile.MobileListVo;
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
public class MobileController {
    private final Logger log = LoggerFactory.getLogger(MobileController.class);

    @Resource
    private MobileCountService mobileCountService;

    @Resource
    private Wordbook wordbook;

    /**
     * 手机管理列表数据
     * @param mobileListVo
     * @return
     */
    @RequestMapping("/maintainer/mobile/mobileListData")
    @ResponseBody
    public AjaxData<MobileListVo> mobileListData(MobileListVo mobileListVo){
        Result<Record> records = mobileCountService.findAllAndPage(mobileListVo);//数据
        List<MobileListVo> mobileListVos = new ArrayList<>();
        if(records.isNotEmpty()){
            mobileListVos = records.into(MobileListVo.class);
        }
        //分页参数
        PaginationData paginationData = new PaginationData();
        paginationData.setPageNum(mobileListVo.getPageNum());
        paginationData.setPageSize(mobileListVo.getPageSize());
        paginationData.setTotalDatas(mobileCountService.findAllAndPageCount(mobileListVo));
        return new AjaxData<MobileListVo>().success().listData(mobileListVos).paginationData(paginationData);
    }
}
