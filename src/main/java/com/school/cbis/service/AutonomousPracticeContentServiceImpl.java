package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeContentDao;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeContent;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeHead;
import com.school.cbis.domain.tables.pojos.Major;
import com.school.cbis.domain.tables.records.AutonomousPracticeContentRecord;
import com.school.cbis.domain.tables.records.AutonomousPracticeHeadRecord;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2016-04-12.
 */
@Service("autonomousPracticeContentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AutonomousPracticeContentServiceImpl implements AutonomousPracticeContentService {

    private final Logger log = LoggerFactory.getLogger(AutonomousPracticeContentServiceImpl.class);

    private final DSLContext create;

    private AutonomousPracticeContentDao autonomousPracticeContentDao;

    @Resource
    private AutonomousPracticeHeadService autonomousPracticeHeadService;

    @Autowired
    public AutonomousPracticeContentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.autonomousPracticeContentDao = new AutonomousPracticeContentDao(configuration);
    }

    @Override
    public void deleteByAutonomousPracticeHeadId(int autonomousPracticeHeadId) {
        create.deleteFrom(Tables.AUTONOMOUS_PRACTICE_CONTENT).where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(autonomousPracticeHeadId)).execute();
    }

    @Override
    public Result<Record4<Integer, String, Integer, Integer>> findByAutonomousPracticeTemplateIdAndStudentId(int autonomousPracticeTemplateId, int studentId) {
        Result<Record4<Integer, String, Integer, Integer>> record4s = create.select(Tables.AUTONOMOUS_PRACTICE_CONTENT.ID, Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT,
                Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID, Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                .from(Tables.AUTONOMOUS_PRACTICE_HEAD)
                .join(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .on(Tables.AUTONOMOUS_PRACTICE_HEAD.ID.eq(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID))
                .where(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(autonomousPracticeTemplateId).and(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(studentId)))
                .fetch();
        return record4s;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(AutonomousPracticeContent autonomousPracticeContent) {
        autonomousPracticeContentDao.insert(autonomousPracticeContent);
    }

    @Override
    public void deleteByAutonomousPracticeHeadIdAndStudentId(int autonomousPracticeHeadId, int studentId) {
        create.deleteFrom(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(autonomousPracticeHeadId).and(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(studentId)))
                .execute();
    }

    @Override
    public Result<Record1<Integer>> findByAutonomousPracticeTemplateIdDistinctAndPage(HttpServletRequest request) {
        int pageNum = 1;
        int pageSize = 10;
        int autonomousPracticeTemplateId = 0;
        if (StringUtils.hasLength(request.getParameter("pageNum"))) {
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }
        if (StringUtils.hasLength(request.getParameter("pageSize"))) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        if (StringUtils.hasLength(request.getParameter("autonomousPracticeTemplateId"))) {
            autonomousPracticeTemplateId = Integer.parseInt(request.getParameter("autonomousPracticeTemplateId"));
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        Byte bytes = 1;
        Condition a = Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(autonomousPracticeTemplateId).and(Tables.AUTONOMOUS_PRACTICE_HEAD.IS_SHOW_HIGHLY_ACTIVE.eq(bytes));
        if (StringUtils.hasLength(request.getParameter("content"))) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(Integer.parseInt(request.getParameter("autonomousPracticeHeadId"))));
        }

        Condition b = null;//content
        List<Integer> headIds = new ArrayList<>();
        int conditionCount = 0;
        Map<String, String[]> map = request.getParameterMap();
        Set<Map.Entry<String, String[]>> set = map.entrySet();
        for (Map.Entry<String, String[]> m : set) {
            if (m.getKey().startsWith("v_")) {
                if (!StringUtils.isEmpty(m.getValue()) && m.getValue().length > 0 && StringUtils.hasLength(m.getValue()[0])) {
                    AutonomousPracticeHeadRecord autonomousPracticeHeadRecord =
                            autonomousPracticeHeadService.findByAutonomousPracticeTemplateIdAndTitleVariable(autonomousPracticeTemplateId, m.getKey());
                    if (conditionCount > 0) {
                        b = b.or(Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT.le(m.getValue()[0]));
                    } else {
                        b = Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT.le(m.getValue()[0]);
                    }
                    headIds.add(autonomousPracticeHeadRecord.getId());
                    conditionCount++;
                }
            }
        }
        Result<Record1<Integer>> record1s;
        if (conditionCount > 0) {
            a = a.and(b);
            record1s = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                    .from(Tables.AUTONOMOUS_PRACTICE_HEAD)
                    .join(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                    .on(Tables.AUTONOMOUS_PRACTICE_HEAD.ID.eq(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID))
                    .where(a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.in(headIds)))
                    .limit((pageNum - 1) * pageSize, pageSize)
                    .fetch();
        } else {
            record1s = create.selectDistinct(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)
                    .from(Tables.AUTONOMOUS_PRACTICE_HEAD)
                    .join(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                    .on(Tables.AUTONOMOUS_PRACTICE_HEAD.ID.eq(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID))
                    .where(a)
                    .limit((pageNum - 1) * pageSize, pageSize)
                    .fetch();
        }
        return record1s;
    }

    @Override
    public int findByAutonomousPracticeTemplateIdDistinctCount(HttpServletRequest request) {

        int autonomousPracticeTemplateId = 0;

        if (StringUtils.hasLength(request.getParameter("autonomousPracticeTemplateId"))) {
            autonomousPracticeTemplateId = Integer.parseInt(request.getParameter("autonomousPracticeTemplateId"));
        }

        Byte bytes = 1;
        Condition a = Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(autonomousPracticeTemplateId).and(Tables.AUTONOMOUS_PRACTICE_HEAD.IS_SHOW_HIGHLY_ACTIVE.eq(bytes));
        if (StringUtils.hasLength(request.getParameter("content"))) {
            a = a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(Integer.parseInt(request.getParameter("autonomousPracticeHeadId"))));
        }

        Condition b = null;//content
        List<Integer> headIds = new ArrayList<>();
        int conditionCount = 0;
        Map<String, String[]> map = request.getParameterMap();
        Set<Map.Entry<String, String[]>> set = map.entrySet();
        for (Map.Entry<String, String[]> m : set) {
            if (m.getKey().startsWith("v_")) {
                if (!StringUtils.isEmpty(m.getValue()) && m.getValue().length > 0 && StringUtils.hasLength(m.getValue()[0])) {
                    AutonomousPracticeHeadRecord autonomousPracticeHeadRecord =
                            autonomousPracticeHeadService.findByAutonomousPracticeTemplateIdAndTitleVariable(autonomousPracticeTemplateId, m.getKey());
                    if (conditionCount > 0) {
                        b = b.or(Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT.le(m.getValue()[0]));
                    } else {
                        b = Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT.le(m.getValue()[0]);
                    }
                    headIds.add(autonomousPracticeHeadRecord.getId());
                    conditionCount++;
                }
            }
        }
        Record1<Integer> count;
        if (conditionCount > 0) {
            a = a.and(b);
            count = create.selectCount()
                    .from(Tables.AUTONOMOUS_PRACTICE_HEAD)
                    .join(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                    .on(Tables.AUTONOMOUS_PRACTICE_HEAD.ID.eq(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID))
                    .where(a.and(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.in(headIds)))
                    .fetchOne();
        } else {
            count = create.selectCount()
                    .from(Tables.AUTONOMOUS_PRACTICE_HEAD)
                    .join(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                    .on(Tables.AUTONOMOUS_PRACTICE_HEAD.ID.eq(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID))
                    .where(a)
                    .fetchOne();
        }
        return count.value1();
    }

    @Override
    public Result<Record3<String, String, String>> findByAutonomousPracticeTemplateIdAndStudentIdWithAuthority(int autonomousPracticeTemplateId, int studentId) {
        Result<Record3<String, String, String>> record3s = create.select(Tables.AUTONOMOUS_PRACTICE_CONTENT.CONTENT,
                 Tables.AUTONOMOUS_PRACTICE_HEAD.TITLE_VARIABLE,
                Tables.AUTONOMOUS_PRACTICE_HEAD.AUTHORITY)
                .from(Tables.AUTONOMOUS_PRACTICE_HEAD)
                .join(Tables.AUTONOMOUS_PRACTICE_CONTENT)
                .on(Tables.AUTONOMOUS_PRACTICE_HEAD.ID.eq(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID))
                .join(Tables.HEAD_TYPE)
                .on(Tables.AUTONOMOUS_PRACTICE_HEAD.HEAD_TYPE_ID.eq(Tables.HEAD_TYPE.ID))
                .where(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(autonomousPracticeTemplateId).and(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID.eq(studentId)))
                .fetch();
        return record3s;
    }

    @Override
    public void update(AutonomousPracticeContent autonomousPracticeContent) {
        autonomousPracticeContentDao.update(autonomousPracticeContent);
    }
}
