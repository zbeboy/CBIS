package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.AutonomousPracticeTemplate;
import org.jooq.Record4;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public interface AutonomousPracticeTemplateService {

    /**
     * 根据系id查询
     * @param tieId
     * @return
     */
    Result<Record4<Integer,String,Integer,Timestamp>> findAllByTieId(int tieId);

}
