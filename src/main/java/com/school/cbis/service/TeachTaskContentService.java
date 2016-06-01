package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachTaskContent;
import com.school.cbis.domain.tables.records.TeachTaskContentRecord;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-05-28.
 */
public interface TeachTaskContentService {

    /**
     * 保存
     * @param teachTaskContent
     */
    void save(TeachTaskContent teachTaskContent);

    /**
     * 根据标题id查询
     * @param teachTaskTitleId
     * @return
     */
    Result<TeachTaskContentRecord> findInTeachTaskTitleId(List<Integer> teachTaskTitleId);
}
