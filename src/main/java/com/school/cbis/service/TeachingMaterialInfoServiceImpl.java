package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeachingMaterialInfoDao;
import com.school.cbis.domain.tables.daos.TeachingMaterialTemplateDao;
import com.school.cbis.domain.tables.pojos.TeachingMaterialInfo;
import com.school.cbis.vo.eadmin.TeachingMaterialInfoListVo;
import com.school.cbis.vo.eadmin.TeachingMaterialListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-04.
 */
@Service("teachingMaterialInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachingMaterialInfoServiceImpl implements TeachingMaterialInfoService {

    private final Logger log = LoggerFactory.getLogger(TeachingMaterialInfoServiceImpl.class);

    private final DSLContext create;

    private TeachingMaterialInfoDao teachingMaterialInfoDao;

    @Autowired
    public TeachingMaterialInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachingMaterialInfoDao = new TeachingMaterialInfoDao(configuration);
    }

    @Override
    public Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> findByTieIdAndPage(TeachingMaterialInfoListVo teachingMaterialInfoListVo, int tieId) {
        Condition a = Tables.TEACHING_MATERIAL_INFO.TIE_ID.eq(tieId);
        if(StringUtils.hasLength(teachingMaterialInfoListVo.getTitle())){
            a = a.and(Tables.TEACHING_MATERIAL_INFO.TITLE.like("%"+teachingMaterialInfoListVo.getTitle()+"%"));
        }

        if(StringUtils.hasLength(teachingMaterialInfoListVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+teachingMaterialInfoListVo.getRealName()+"%"));
        }

        int pageNum = teachingMaterialInfoListVo.getPageNum();
        int pageSize = teachingMaterialInfoListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> record7s = create.select(Tables.TEACHING_MATERIAL_INFO.ID,Tables.TEACHING_MATERIAL_INFO.TITLE,
                Tables.TEACHING_MATERIAL_INFO.CREATE_TIME,Tables.TEACHING_MATERIAL_TEMPLATE.TITLE.as("templateTitle"),
                Tables.TEACHING_MATERIAL_INFO.START_TIME,Tables.TEACHING_MATERIAL_INFO.END_TIME,
                Tables.USERS.REAL_NAME)
                .from(Tables.TEACHING_MATERIAL_INFO)
                .leftJoin(Tables.TEACHING_MATERIAL_TEMPLATE)
                .on(Tables.TEACHING_MATERIAL_INFO.TEACHING_MATERIAL_TEMPLATE_ID.eq(Tables.TEACHING_MATERIAL_TEMPLATE.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHING_MATERIAL_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.TEACHING_MATERIAL_INFO.CREATE_TIME.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return record7s;
    }

    @Override
    public int findByTieIdAndPageCount(TeachingMaterialInfoListVo teachingMaterialInfoListVo, int tieId) {
        Condition a = Tables.TEACHING_MATERIAL_INFO.TIE_ID.eq(tieId);
        if(StringUtils.hasLength(teachingMaterialInfoListVo.getTitle())){
            a = a.and(Tables.TEACHING_MATERIAL_INFO.TITLE.like("%"+teachingMaterialInfoListVo.getTitle()+"%"));
        }

        if(StringUtils.hasLength(teachingMaterialInfoListVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+teachingMaterialInfoListVo.getRealName()+"%"));
        }

        Record1<Integer> record1 = create.selectCount()
                .from(Tables.TEACHING_MATERIAL_INFO)
                .leftJoin(Tables.TEACHING_MATERIAL_TEMPLATE)
                .on(Tables.TEACHING_MATERIAL_INFO.TEACHING_MATERIAL_TEMPLATE_ID.eq(Tables.TEACHING_MATERIAL_TEMPLATE.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHING_MATERIAL_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Override
    public void save(TeachingMaterialInfo teachingMaterialInfo) {
        teachingMaterialInfoDao.insert(teachingMaterialInfo);
    }

    @Override
    public TeachingMaterialInfo findById(int id) {
        TeachingMaterialInfo teachingMaterialInfo = teachingMaterialInfoDao.findById(id);
        return teachingMaterialInfo;
    }

    @Override
    public void update(TeachingMaterialInfo teachingMaterialInfo) {
        teachingMaterialInfoDao.update(teachingMaterialInfo);
    }

    @Override
    public Result<Record9<Integer, Integer, Integer, String, Timestamp, Timestamp, String, String, String>> findAllAndPage(TeachingMaterialListVo teachingMaterialListVo, int tieId, int teachTypeId) {
        Condition a = Tables.TEACHING_MATERIAL_INFO.TIE_ID.eq(tieId).and(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID.eq(teachTypeId));

        int pageNum = teachingMaterialListVo.getPageNum();
        int pageSize = teachingMaterialListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record9<Integer, Integer, Integer, String, Timestamp, Timestamp, String, String, String>> record9s = create.select(Tables.TEACHING_MATERIAL_INFO.ID,Tables.TEACHING_MATERIAL_TEMPLATE.ID.as("templateId"),
                Tables.TEACH_TASK_INFO.ID.as("taskInfoId"),Tables.TEACHING_MATERIAL_INFO.TITLE,
                Tables.TEACHING_MATERIAL_INFO.START_TIME,Tables.TEACHING_MATERIAL_INFO.END_TIME,
                Tables.USERS.REAL_NAME,Tables.TEACHING_MATERIAL_TEMPLATE.TITLE.as("templateTitle"),
                Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE.as("teachTaskTitle"))
                .from(Tables.TEACHING_MATERIAL_INFO)
                .leftJoin(Tables.TEACHING_MATERIAL_TEMPLATE)
                .on(Tables.TEACHING_MATERIAL_INFO.TEACHING_MATERIAL_TEMPLATE_ID.eq(Tables.TEACHING_MATERIAL_TEMPLATE.ID))
                .leftJoin(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACHING_MATERIAL_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHING_MATERIAL_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.TEACHING_MATERIAL_INFO.CREATE_TIME.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();

        return record9s;
    }

    @Override
    public int findAllAndPageCount(TeachingMaterialListVo teachingMaterialListVo, int tieId, int teachTypeId) {
        Condition a = Tables.TEACHING_MATERIAL_INFO.TIE_ID.eq(tieId).and(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID.eq(teachTypeId));
        Record1<Integer> record1 = create.selectCount()
                .from(Tables.TEACHING_MATERIAL_INFO)
                .leftJoin(Tables.TEACHING_MATERIAL_TEMPLATE)
                .on(Tables.TEACHING_MATERIAL_INFO.TEACHING_MATERIAL_TEMPLATE_ID.eq(Tables.TEACHING_MATERIAL_TEMPLATE.ID))
                .leftJoin(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACHING_MATERIAL_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHING_MATERIAL_INFO.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record1.value1();
    }
}
