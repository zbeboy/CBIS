package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeachTaskContentDao;
import com.school.cbis.domain.tables.daos.TeachTaskTitleDao;
import com.school.cbis.domain.tables.pojos.TeachTaskContent;
import com.school.cbis.domain.tables.records.TeachTaskContentRecord;
import com.school.cbis.vo.eadmin.FourItemsLineVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lenovo on 2016-05-28.
 */
@Service("teachTaskContentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachTaskContentServiceImpl implements TeachTaskContentService {

    private final Logger log = LoggerFactory.getLogger(TeachTaskContentServiceImpl.class);

    private final DSLContext create;

    private TeachTaskContentDao teachTaskContentDao;

    @Autowired
    public TeachTaskContentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachTaskContentDao = new TeachTaskContentDao(configuration);
    }

    @Override
    public void save(TeachTaskContent teachTaskContent) {
        teachTaskContentDao.insert(teachTaskContent);
    }

    @Override
    public Result<TeachTaskContentRecord> findInTeachTaskTitleId(List<Integer> teachTaskTitleId) {
        Result<TeachTaskContentRecord> records = create.selectFrom(Tables.TEACH_TASK_CONTENT)
                .where(Tables.TEACH_TASK_CONTENT.TEACH_TASK_TITLE_ID.in(teachTaskTitleId))
                .fetch();
        return records;
    }

    @Override
    public List<TeachTaskContent> findByTeachTaskTitleId(int teachTaskTitleId) {
        List<TeachTaskContent> teachTaskContents = teachTaskContentDao.fetchByTeachTaskTitleId(teachTaskTitleId);
        return teachTaskContents;
    }

    @Override
    public Result<Record1<Integer>> findByTeachTaskInfoIdAndDistinctContentXAndPage(FourItemsLineVo fourItemsLineVo) {
        Condition a = Tables.TEACH_TASK_TITLE.TEACH_TASK_INFO_ID.eq(fourItemsLineVo.getTaskInfoId());

        if(fourItemsLineVo.getTaskTitleId()>0){
            a = a.and(Tables.TEACH_TASK_TITLE.ID.eq(fourItemsLineVo.getTaskTitleId()));
        }

        if(StringUtils.hasLength(fourItemsLineVo.getContent())){
            a = a.and(Tables.TEACH_TASK_CONTENT.CONTENT.like("%"+fourItemsLineVo.getContent()+"%"));
        }

        int pageNum = fourItemsLineVo.getPageNum();
        int pageSize = fourItemsLineVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }


        Result<Record1<Integer>> record1s = create.selectDistinct(Tables.TEACH_TASK_CONTENT.CONTENT_X)
                .from(Tables.TEACH_TASK_CONTENT)
                .join(Tables.TEACH_TASK_TITLE)
                .on(Tables.TEACH_TASK_CONTENT.TEACH_TASK_TITLE_ID.eq(Tables.TEACH_TASK_TITLE.ID))
                .where(a)
                .orderBy(Tables.TEACH_TASK_CONTENT.CONTENT_X)
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return record1s;
    }

    @Override
    public int findByTeachTaskInfoIdAndDistinctContentXAndPageCount(FourItemsLineVo fourItemsLineVo) {
        Condition a = Tables.TEACH_TASK_TITLE.TEACH_TASK_INFO_ID.eq(fourItemsLineVo.getTaskInfoId());

        if(fourItemsLineVo.getTaskTitleId()>0){
            a = a.and(Tables.TEACH_TASK_TITLE.ID.eq(fourItemsLineVo.getTaskTitleId()));
        }

        if(StringUtils.hasLength(fourItemsLineVo.getContent())){
            a = a.and(Tables.TEACH_TASK_CONTENT.CONTENT.like("%"+fourItemsLineVo.getContent()+"%"));
        }

        Result<Record1<Integer>> record1s = create.selectDistinct(Tables.TEACH_TASK_CONTENT.CONTENT_X)
                .from(Tables.TEACH_TASK_CONTENT)
                .join(Tables.TEACH_TASK_TITLE)
                .on(Tables.TEACH_TASK_CONTENT.TEACH_TASK_TITLE_ID.eq(Tables.TEACH_TASK_TITLE.ID))
                .where(a)
                .fetch();
        return record1s.size();
    }

    @Override
    public Result<Record7<Integer, String, String, Integer, Integer,String,Integer>> findByTeachTaskInfoIdWithTeachTaskTitle(int id) {
        Result<Record7<Integer, String, String, Integer, Integer,String,Integer>> record7s = create.select(Tables.TEACH_TASK_CONTENT.ID,
                Tables.TEACH_TASK_TITLE.TITLE,
                Tables.TEACH_TASK_CONTENT.CONTENT,
                Tables.TEACH_TASK_CONTENT.CONTENT_X,
                Tables.TEACH_TASK_CONTENT.CONTENT_Y,
                Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE,
                Tables.TEACH_TASK_TITLE.ID.as("taskTitleId"))
                .from(Tables.TEACH_TASK_CONTENT)
                .join(Tables.TEACH_TASK_TITLE)
                .on(Tables.TEACH_TASK_CONTENT.TEACH_TASK_TITLE_ID.eq(Tables.TEACH_TASK_TITLE.ID))
                .join(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACH_TASK_TITLE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .where(Tables.TEACH_TASK_INFO.ID.eq(id))
                .fetch();
        return record7s;
    }
}
