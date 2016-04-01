package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.AuthoritiesRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(AuthoritiesServiceImpl.class);

    private final DSLContext create;

    @Autowired
    public AuthoritiesServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public List<AuthoritiesRecord> findByUsername(String username) {
        List<AuthoritiesRecord> authoritiesRecords = create.selectFrom(Tables.AUTHORITIES).where(Tables.AUTHORITIES.USERNAME.eq(username)).fetch();
        return authoritiesRecords;
    }

    @Override
    public void delete(String username) {
        create.deleteFrom(Tables.AUTHORITIES).where(Tables.AUTHORITIES.USERNAME.eq(username)).execute();
    }

    @Override
    public void update(List<AuthoritiesRecord> authoritiesRecords) {
        authoritiesRecords.forEach(a->{
            create.update(Tables.AUTHORITIES)
                    .set(Tables.AUTHORITIES.AUTHORITY, a.getAuthority())
                    .where(Tables.AUTHORITIES.USERNAME.eq(a.getUsername())).execute();
        });
    }

    @Override
    public void save(List<AuthoritiesRecord> authoritiesRecords) {
        authoritiesRecords.forEach(a->{
            create.insertInto(Tables.AUTHORITIES)
                    .set(Tables.AUTHORITIES.USERNAME, a.getUsername())
                    .set(Tables.AUTHORITIES.AUTHORITY, a.getAuthority())
                    .execute();
        });
    }
}
