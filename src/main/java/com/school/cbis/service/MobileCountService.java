package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.MobileCount;
import com.school.cbis.vo.mobile.MobileListVo;
import org.jooq.Record;
import org.jooq.Result;

/**
 * Created by lenovo on 2016-05-19.
 */
public interface MobileCountService {

    /**
     * 保存发送
     * @param mobileCount
     */
    void save(MobileCount mobileCount);

    /**
     * 分页查询全部
     * @param MobileListVo
     * @return
     */
    Result<Record> findAllAndPage(MobileListVo MobileListVo);

    /**
     * 分页查询全部 总数
     * @param MobileListVo
     * @return
     */
    int findAllAndPageCount(MobileListVo MobileListVo);
}
