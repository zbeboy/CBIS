package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeTemplateDao;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeTemplate;
import com.school.cbis.domain.tables.records.AutonomousPracticeTemplateRecord;
import com.school.cbis.vo.autonomicpractice.TemplateVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
@Service("autonomousPracticeTemplateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AutonomousPracticeTemplateServiceImpl implements AutonomousPracticeTemplateService {

    private final Logger log = LoggerFactory.getLogger(AutonomousPracticeTemplateServiceImpl.class);

    private final DSLContext create;

    private AutonomousPracticeTemplateDao autonomousPracticeTemplateDao;

    @Autowired
    public AutonomousPracticeTemplateServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.autonomousPracticeTemplateDao = new AutonomousPracticeTemplateDao(configuration);
    }


    @Override
    public List<AutonomousPracticeTemplate> findAllByTieId(int tieId) {
        List<AutonomousPracticeTemplate> autonomousPracticeTemplates = autonomousPracticeTemplateDao.fetchByTieId(tieId);
        return autonomousPracticeTemplates;
    }

    @Override
    public Result<Record4<Integer, String, Timestamp, String>> findAllAndPage(TemplateVo templateVo, int tieId) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.TIE_ID.eq(tieId);

        SortField<Timestamp> b = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME.desc();

        SortField<String> c = null;

        if (StringUtils.hasLength(templateVo.getAutonomousPracticeTemplateTitle())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE.like("%" + templateVo.getAutonomousPracticeTemplateTitle() + "%"));
        }

        if (StringUtils.hasLength(templateVo.getCreateTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME.like("%" + templateVo.getCreateTime() + "%"));
        }

        if (StringUtils.hasLength(templateVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + templateVo.getRealName() + "%"));
        }

        int pageNum = templateVo.getPageNum();
        int pageSize = templateVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        SelectConditionStep<Record4<Integer, String, Timestamp, String>> e =
                create.select(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.ID, Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE,
                        Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME, Tables.USERS.REAL_NAME)
                        .from(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                        .join(Tables.USERS)
                        .on(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.USERS_ID.eq(Tables.USERS.USERNAME))
                        .where(a);

        if (StringUtils.hasLength(templateVo.getSortField())) {
            if (templateVo.getSortField().equals("createTime")) {
                if (templateVo.getSortOrder().equals("desc")) {
                    b = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME.desc();
                } else {
                    b = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME.asc();
                }
            } else if (templateVo.getSortField().equals("autonomousPracticeTemplateTitle")) {
                if (templateVo.getSortOrder().equals("desc")) {
                    c = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE.desc();
                } else {
                    c = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE.asc();
                }
            } else if (templateVo.getSortField().equals("realName")) {
                if (templateVo.getSortOrder().equals("desc")) {
                    c = Tables.USERS.REAL_NAME.desc();
                } else {
                    c = Tables.USERS.REAL_NAME.asc();
                }
            }

            if (!StringUtils.isEmpty(c)) {
                e.orderBy(c);
            } else {
                e.orderBy(b);
            }
        } else {
            e.orderBy(b);
        }

        return e.limit((pageNum - 1) * pageSize, pageSize).fetch();
    }

    @Override
    public int findAllAndCount(TemplateVo templateVo, int tieId) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(templateVo.getAutonomousPracticeTemplateTitle())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE.like("%" + templateVo.getAutonomousPracticeTemplateTitle() + "%"));
        }

        if (StringUtils.hasLength(templateVo.getCreateTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME.like("%" + templateVo.getCreateTime() + "%"));
        }

        if (StringUtils.hasLength(templateVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + templateVo.getRealName() + "%"));
        }
        Record1<Integer> count = create.selectCount()
                .from(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                .join(Tables.USERS)
                .on(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a).fetchOne();
        return count.value1();
    }

    @Override
    public void deleteById(int id) {
        autonomousPracticeTemplateDao.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int save(AutonomousPracticeTemplate autonomousPracticeTemplate) {
        AutonomousPracticeTemplateRecord autonomousPracticeTemplateRecord = create.insertInto(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                .set(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE, autonomousPracticeTemplate.getAutonomousPracticeTemplateTitle())
                .set(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.USERS_ID, autonomousPracticeTemplate.getUsersId())
                .set(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.TIE_ID, autonomousPracticeTemplate.getTieId())
                .returning(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.ID)
                .fetchOne();
        return autonomousPracticeTemplateRecord.getId();
    }

    @Override
    public AutonomousPracticeTemplate findById(int id) {
        AutonomousPracticeTemplate autonomousPracticeTemplate = autonomousPracticeTemplateDao.findById(id);
        return autonomousPracticeTemplate;
    }

    @Override
    public void update(AutonomousPracticeTemplate autonomousPracticeTemplate) {
        autonomousPracticeTemplateDao.update(autonomousPracticeTemplate);
    }

    @Override
    public AutonomousPracticeTemplateRecord findByAutonomousPracticeTemplateTitleAndTieIdEq(String autonomousPracticeTemplateTitle, int tieId) {
        AutonomousPracticeTemplateRecord autonomousPracticeTemplateRecord = create.selectFrom(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                .where(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE.eq(autonomousPracticeTemplateTitle).and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.TIE_ID.eq(tieId))).fetchOne();
        return autonomousPracticeTemplateRecord;
    }

    @Override
    public AutonomousPracticeTemplateRecord findByAutonomousPracticeTemplateTitleAndTieIdAndNeId(int id, String autonomousPracticeTemplateTitle, int tieId) {
        AutonomousPracticeTemplateRecord autonomousPracticeTemplateRecord = create.selectFrom(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                .where(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE.eq(autonomousPracticeTemplateTitle).and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.TIE_ID.eq(tieId))
                        .and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.ID.ne(id))).fetchOne();
        return autonomousPracticeTemplateRecord;
    }


}
