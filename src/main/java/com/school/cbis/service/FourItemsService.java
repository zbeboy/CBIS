package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.FourItems;
import com.school.cbis.domain.tables.records.FourItemsRecord;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-06-07.
 */
public interface FourItemsService {

    /**
     * 根据教学教务书查询 in 行x
     * @param teachTaskInfoId
     * @param contentX
     * @return
     */
    Result<FourItemsRecord> findByTeachTaskInfoIdInContentX(int teachTaskInfoId,List<Integer> contentX);

    /**
     * 保存
     * @param fourItems
     */
    void save(FourItems fourItems);

    /**
     * 更新
     * @param fourItems
     */
    void update(FourItems fourItems);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    FourItems findById(int id);

    /**
     * 通过id删除
     * @param id
     */
    void deleteById(int id);
}
