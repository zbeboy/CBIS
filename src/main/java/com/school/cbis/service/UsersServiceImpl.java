package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by lenovo on 2016-01-05.
 */
@Service("usersService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final DSLContext create;

    @Autowired
    public UsersServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public String getUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = StringUtils.trimWhitespace(((UserDetails) principal).getUsername());
        }
        return username;
    }

    @Override
    public String getPassword() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = null;
        if (principal instanceof UserDetails) {
            password = StringUtils.trimWhitespace(((UserDetails) principal).getPassword());
        }
        return password;
    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        int count = create.update(Tables.USERS).set(Tables.USERS.PASSWORD, newPassword).where(Tables.USERS.USERNAME.equal(username)).execute();
        System.out.println("count:" + count);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
