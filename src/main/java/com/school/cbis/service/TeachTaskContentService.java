package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachTaskContent;
import com.school.cbis.domain.tables.records.TeachTaskContentRecord;
import com.school.cbis.vo.eadmin.FourItemsLineVo;
import org.jooq.*;

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

    /**
     * 根据标题表id查询
     * @param teachTaskTitleId
     * @return
     */
    List<TeachTaskContent> findByTeachTaskTitleId(int teachTaskTitleId);

    /**
     * 根据教学任务书id distinct查询内容表x
     * @param fourItemsLineVo
     * @return
     */
    Result<Record1<Integer>> findByTeachTaskInfoIdAndDistinctContentXAndPage(FourItemsLineVo fourItemsLineVo);

    /**
     * 根据教学任务书id distinct查询内容表x 总数
     * @param fourItemsLineVo
     * @return
     */
    int findByTeachTaskInfoIdAndDistinctContentXAndPageCount(FourItemsLineVo fourItemsLineVo);

    /**
     * 通过id查询关联title
     * @param teachTaskInfoId
     * @return
     */
    Result<Record7<Integer, String, String, Integer, Integer,String,Integer>> findByTeachTaskInfoIdWithTeachTaskTitle(int teachTaskInfoId);
}
