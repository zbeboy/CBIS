package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.scheduling.annotation.Async;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by lenovo on 2016-05-17.
 */
public class MobileServiceImpl implements MobileService {

    @Async
    @Override
    public void sendShortMessage(String mobile, String content) {
        try {
            String httpUrl = "http://apis.baidu.com/kingtto_media/106sms/106sms";
            content = URLEncoder.encode(content,CharEncoding.UTF_8);
            String httpArg = "mobile="+mobile+"&content="+content+"&tag=2";
            BufferedReader reader = null;
            String result = null;
            StringBuffer sbf = new StringBuffer();
            httpUrl = httpUrl + "?" + httpArg;
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", Wordbook.MOBILE_APIKEY);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, CharEncoding.UTF_8));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendValidMobileShortMessage(String mobile, String verificationCode) {
        String content = "[信息工程系信息平台] 您的验证码:"+verificationCode;
        sendShortMessage(mobile,content);
    }
}
