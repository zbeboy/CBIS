package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Authorities;
import com.school.cbis.domain.tables.records.AuthoritiesRecord;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2016-02-21.
 */
@Service("authoritiesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AuthoritiesServiceImpl implements AuthoritiesService {
    private final DSLContext create;

    @Autowired
    public AuthoritiesServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public Result<AuthoritiesRecord> findByUsername(String username) {
        Result<AuthoritiesRecord> authoritiesRecords = create.selectFrom(Tables.AUTHORITIES).where(Tables.AUTHORITIES.USERNAME.eq(username)).fetch();
        return authoritiesRecords;
    }

    @Override
    public void delete(String username) {
        create.deleteFrom(Tables.AUTHORITIES).where(Tables.AUTHORITIES.USERNAME.eq(username));
    }

    @Override
    public void save(List<Authorities> authorities) {
        BatchBindStep bindStep = create.batch(create.insertInto(Tables.AUTHORITIES,
                Tables.AUTHORITIES.USERNAME,
                Tables.AUTHORITIES.AUTHORITY).values("", ""));
        for (Authorities r : authorities) {
            bindStep.bind(r.getUsername(), r.getAuthority());
        }
        bindStep.execute();
    }
}
