package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.GradeRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2016-01-17.
 */
@Service("gradeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GradeServiceImpl implements GradeService {

    private final DSLContext create;

    @Autowired
    public GradeServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public GradeRecord getGradeInfo(int id) {
        GradeRecord gradeRecord = create.selectFrom(Tables.GRADE).where(Tables.GRADE.ID.equal(id)).fetchAny();
        return gradeRecord;
    }
}
