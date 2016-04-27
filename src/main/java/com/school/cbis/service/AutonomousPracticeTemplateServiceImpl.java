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
    public Result<Record4<Integer, String, Timestamp, String>> findAllAndPage(TemplateVo templateVo,int tieId) {
        Condition a = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.TIE_ID.eq(tieId);

        SortField<Timestamp> b = Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME.desc();

        SortField<String> c = null;

        if (StringUtils.hasLength(templateVo.getAutonomousPracticeTemplateTitle())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE.like("%" + templateVo.getAutonomousPracticeTemplateTitle() + "%"));
        }

        if (StringUtils.hasLength(templateVo.getCreateTime())) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME.like("%" + templateVo.getCreateTime() + "%"));
        }

        if (StringUtils.hasLength(templateVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + templateVo.getUsername() + "%"));
        }

        SelectConditionStep<Record4<Integer, String, Timestamp, String>> e =
        create.select(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.ID,Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE,
                Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME,Tables.USERS.USERNAME)
                .from(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                .join(Tables.USERS)
                .on(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.USERS_ID.eq(Tables.USERS.USERNAME))
                .where(a);

        if (StringUtils.hasLength(templateVo.getSortField())) {
            if (templateVo.getSortField().equals("create_time")) {
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
            } else if (templateVo.getSortField().equals("username")) {
                if (templateVo.getSortOrder().equals("desc")) {
                    c = Tables.USERS.USERNAME.desc();
                } else {
                    c = Tables.USERS.USERNAME.asc();
                }
            }

            if (!StringUtils.isEmpty(b)) {
                e.orderBy(b);
            } else {
                e.orderBy(c);
            }
        } else {
            e.orderBy(b);
        }

        return e.limit((templateVo.getPageIndex() - 1) * templateVo.getPageSize(), templateVo.getPageSize()).fetch();
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

        if (StringUtils.hasLength(templateVo.getUsername())) {
            a = a.and(Tables.USERS.USERNAME.like("%" + templateVo.getUsername() + "%"));
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
                .set(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE,autonomousPracticeTemplate.getAutonomousPracticeTemplateTitle())
                .set(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.USERS_ID,autonomousPracticeTemplate.getUsersId())
                .set(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.TIE_ID,autonomousPracticeTemplate.getTieId())
                .returning(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.ID)
                .fetchOne();
        return autonomousPracticeTemplateRecord.getId();
    }
}
