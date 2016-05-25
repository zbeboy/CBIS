package com.school.cbis;
/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.school.cbis.service.MyUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@EnableAuthorizationServer
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

    private final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * 生成war包时需要
     */
   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }*/
    @Bean
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }

    @Bean
    public static Md5PasswordEncoder md5() {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        md5.setEncodeHashAsBase64(false);
        return md5;
    }

    @Bean
    public static JdbcTokenRepositoryImpl jdbcTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl j = new JdbcTokenRepositoryImpl();
        j.setDataSource(dataSource);
        return j;
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class).run(args);
         /*SpringApplication.run(Application.class, args);//生成war包时需要*/
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Autowired
        private MyUserDetailsServiceImpl myUserDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                    .ignoringAntMatchers("/metrics/**","/health/**","/trace/**","/dump/**","/shutdown/**",
                            "/beans/**","/configprops/**","/info/**","/autoconfig/**","/env/**","/mappings/**")
                    .and()
                    .authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/images/**", "/files/**").permitAll()
                    .and().formLogin().loginPage("/login").defaultSuccessUrl("/backstage", true)
                    .failureUrl("/login?error").permitAll().and().sessionManagement().invalidSessionUrl("/login")
                    .and().logout().logoutSuccessUrl("/")
                    .permitAll().invalidateHttpSession(true)
                    .and().rememberMe().tokenValiditySeconds(2419200).rememberMeParameter("remember-me").tokenRepository(jdbcTokenRepository(dataSource))
                    .and().authorizeRequests().antMatchers("/administrator/**").hasRole("ADMIN")
                    .and().authorizeRequests().antMatchers("/maintainer/**").hasAnyRole("MAI", "ADMIN")
                    .and().authorizeRequests().antMatchers("/semi/**").hasAnyRole("SEMI", "MAI", "ADMIN")
                    .and().authorizeRequests().antMatchers("/teacher/**").hasAnyRole("TEA", "SEMI", "MAI", "ADMIN")
                    .and().authorizeRequests().antMatchers("/student/**").hasAnyRole("STU", "TEA", "ADMIN", "MAI", "SEMI")
                    .and().authorizeRequests().antMatchers("/user/**").permitAll()
                    .antMatchers("/metrics/**").hasRole("ADMIN")
                    .antMatchers("/health/**").hasRole("ADMIN")
                    .antMatchers("/trace/**").hasRole("ADMIN")
                    .antMatchers("/dump/**").hasRole("ADMIN")
                    .antMatchers("/shutdown/**").hasRole("ADMIN")
                    .antMatchers("/beans/**").hasRole("ADMIN")
                    .antMatchers("/configprops/**").hasRole("ADMIN")
                    .antMatchers("/info/**").hasRole("ADMIN")
                    .antMatchers("/autoconfig/**").hasRole("ADMIN")
                    .antMatchers("/env/**").hasRole("ADMIN")
                    .antMatchers("/mappings/**").hasRole("ADMIN");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(myUserDetailsService).passwordEncoder(md5()).and().eraseCredentials(false);
//            auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder(md5()).and().eraseCredentials(false);
        }
    }

}
