package com.school.cbis.test;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.cbis.Application;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lenovo on 2016-05-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort = true)
public class TestActuator {
    @Autowired
    WebApplicationContext context;

    @Autowired
    FilterChainProxy filterChain;

    @Value("${local.server.port}")
    private int port;

    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(this.context).addFilters(this.filterChain).build();
        SecurityContextHolder.clearContext();
    }

    @Test
    @Ignore
    public void accessingRootUriPossibleWithUserAccount() throws Exception {
        String header = "Basic " + new String(Base64.encode("greg:turnquist".getBytes()));
        this.mvc.perform(
                get("/").accept(MediaTypes.HAL_JSON).header("Authorization", header))
                .andExpect(
                        header().string("Content-Type", MediaTypes.HAL_JSON.toString()))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void useAppSecretsPlusUserAccountToGetBearerToken() throws Exception {
        String header = "Basic " + new String(Base64.encode("zbeboy:123456".getBytes()));
        MvcResult result = this.mvc
                .perform(get("/autoconfig").header("Authorization", header))
                .andExpect(status().isOk()).andDo(print()).andReturn();
//        Object accessToken = this.objectMapper
//                .readValue(result.getResponse().getContentAsString(), Map.class)
//                .get("status");
//        System.out.println(accessToken);
    }

    @Test
    public void testHttpClient(){
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod("http://localhost:"+this.port+"/autoconfig");
        String header = "Basic " + new String(Base64.encode("zbeboy:123456".getBytes()));
        getMethod.setRequestHeader("Authorization", header);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            // 处理内容
            String html = getMethod.getResponseBodyAsString();
            System.out.println(html);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }
    }
}
