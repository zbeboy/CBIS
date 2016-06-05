package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeachingMaterialContentDao;
import com.school.cbis.domain.tables.daos.TeachingMaterialHeadDao;
import com.school.cbis.domain.tables.pojos.TeacherFillTaskContent;
import com.school.cbis.domain.tables.pojos.TeachingMaterialContent;
import com.school.cbis.domain.tables.records.TeacherFillTaskContentRecord;
import com.school.cbis.domain.tables.records.TeachingMaterialContentRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2016-06-04.
 */
@Service("teachingMaterialContentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachingMaterialContentServiceImpl implements TeachingMaterialContentService {

    private final Logger log = LoggerFactory.getLogger(TeachingMaterialContentServiceImpl.class);

    private final DSLContext create;

    private TeachingMaterialContentDao teachingMaterialContentDao;

    @Autowired
    public TeachingMaterialContentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachingMaterialContentDao = new TeachingMaterialContentDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TeachingMaterialContent teachingMaterialContent) {
        teachingMaterialContentDao.insert(teachingMaterialContent);
    }

    @Override
    public void deleteByTeachingMaterialHeadId(int teachingMaterialHeadId) {
        create.deleteFrom(Tables.TEACHING_MATERIAL_CONTENT)
                .where(Tables.TEACHING_MATERIAL_CONTENT.TEACHING_MATERIAL_HEAD_ID.eq(teachingMaterialHeadId))
                .execute();
    }

    @Override
    public List<TeachingMaterialContent> findByTeachingMaterialHeadId(int teachingMaterialHeadId) {
        List<TeachingMaterialContent> teachingMaterialContents = teachingMaterialContentDao.fetchByTeachingMaterialHeadId(teachingMaterialHeadId);
        return teachingMaterialContents;
    }

    @Override
    public void update(TeachingMaterialContent teachingMaterialContent) {
        teachingMaterialContentDao.update(teachingMaterialContent);
    }

    @Override
    public Result<TeachingMaterialContentRecord> findInTeachingMaterialHeadId(List<Integer> id) {
        Result<TeachingMaterialContentRecord> records =  create.selectFrom(Tables.TEACHING_MATERIAL_CONTENT)
                .where(Tables.TEACHING_MATERIAL_CONTENT.TEACHING_MATERIAL_HEAD_ID.in(id))
                .fetch();
        return records;
    }

    @Override
    public Result<TeachingMaterialContentRecord> findInTeachingMaterialHeadIdAndContentX(List<Integer> id, int contentX) {
        Result<TeachingMaterialContentRecord> records =  create.selectFrom(Tables.TEACHING_MATERIAL_CONTENT)
                .where(Tables.TEACHING_MATERIAL_CONTENT.TEACHING_MATERIAL_HEAD_ID.in(id).and(Tables.TEACHING_MATERIAL_CONTENT.CONTENT_X.eq(contentX)))
                .fetch();
        return records;
    }

    @Override
    public TeachingMaterialContentRecord findByTeachingMaterialHeadIdAndContentX(int teachingMaterialHeadId, int contentX) {
        TeachingMaterialContentRecord record = create.selectFrom(Tables.TEACHING_MATERIAL_CONTENT)
                .where(Tables.TEACHING_MATERIAL_CONTENT.TEACHING_MATERIAL_HEAD_ID.eq(teachingMaterialHeadId).and(Tables.TEACHING_MATERIAL_CONTENT.CONTENT_X.eq(contentX)))
                .fetchOne();
        return record;
    }
}
