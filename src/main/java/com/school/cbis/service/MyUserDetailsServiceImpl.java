package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Users;
import com.school.cbis.domain.tables.records.AuthoritiesRecord;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lenovo on 2016-04-30.
 */
@Service("myUserDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(MyUserDetailsServiceImpl.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("username is : {}"+s);
        String username = StringUtils.trimWhitespace(s);
        Users users = usersService.findByUsername(username);
        List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(username);
        List<GrantedAuthority> authorities = buildUserAuthority(authoritiesRecords);
        return buildUserForAuthentication(users, authorities);
    }

    /**
     * 返回验证角色
     * @param authoritiesRecords
     * @return
     */
    private List<GrantedAuthority> buildUserAuthority(List<AuthoritiesRecord> authoritiesRecords){
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        for(AuthoritiesRecord userRole:authoritiesRecords){
            setAuths.add(new SimpleGrantedAuthority(userRole.getAuthority()));
        }
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
        return result;
    }

    /**
     * 返回验证用户
     * @param users
     * @param authorities
     * @return
     */
    private MyUserImpl buildUserForAuthentication(Users users,List<GrantedAuthority> authorities){
        boolean enable = false;
        if(users.getEnabled() == 1){
            enable = true;
        }
        return new MyUserImpl(users.getUsername(),users.getPassword(),users.getUserTypeId(),users,enable,true,true,true,authorities);
    }
}
