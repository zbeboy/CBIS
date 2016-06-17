package com.school.cbis.interceptor;

import com.octo.captcha.service.CaptchaServiceException;
import com.school.cbis.util.CaptchaServiceSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2016-06-16.
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return (validCaptcha(httpServletRequest.getParameter("j_captcha_response"), httpServletRequest));
        } else {
            return Boolean.TRUE;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 登录时检验
     *
     * @param captcha
     * @param request
     * @return
     */
    public boolean validCaptcha(String captcha, HttpServletRequest request) {
        Boolean isResponseCorrect = Boolean.FALSE;
        // remenber that we need an id to validate!
        String captchaId = request.getSession().getId();
        log.debug("validateCaptchaForId captchaId : {}", captchaId);
        log.debug(" j_captcha_response : {} ", captcha);
        // call the service method
        try {
            isResponseCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, captcha);
        } catch (CaptchaServiceException e) {
            log.error(" validateCaptchaForId exception : {} ", e.getMessage());
        }
        return isResponseCorrect;
    }
}
