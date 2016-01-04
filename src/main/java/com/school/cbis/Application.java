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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Map;

@EnableAutoConfiguration
@ComponentScan
@Controller
public class Application extends WebMvcConfigurerAdapter {

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        return "/sadmin/index";
    }

    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }



    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class).run(args);
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            JdbcTokenRepositoryImpl j = new JdbcTokenRepositoryImpl();
            j.setDataSource(dataSource);
            http.authorizeRequests().antMatchers("/css/**","/js/**","/fonts/**","/images/**","/files/**").permitAll()
                    .and().formLogin().loginPage("/login").defaultSuccessUrl("/",true)
                    .failureUrl("/login?error").permitAll().and().sessionManagement().invalidSessionUrl("/login")
                    .and().rememberMe().tokenValiditySeconds(2419200).rememberMeParameter("remember-me").tokenRepository(j)
                    .and().authorizeRequests().antMatchers("/sadmin/**").hasRole("SUPER")
                    .and().authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN","SUPER")
                    .and().authorizeRequests().antMatchers("/teacher/**").hasAnyRole("TEA","ADMIN","SUPER")
                    .and().authorizeRequests().antMatchers("/student/**").hasAnyRole("STU","TEA","ADMIN","SUPER")
                    .and().authorizeRequests().antMatchers("/user/**").permitAll();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            Md5PasswordEncoder md5 = new Md5PasswordEncoder();
            md5.setEncodeHashAsBase64(false);
            auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder(md5).and().eraseCredentials(false);
        }
    }

}
