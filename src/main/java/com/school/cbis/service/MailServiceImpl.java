package com.school.cbis.service;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.tables.pojos.Users;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/29.
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private MessageSource messageSource;

    @Resource
    private TemplateEngine templateEngine;

    @Async
    @Override
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(Wordbook.getMail_form());
            message.setSubject(subject);
            String str1=content.replaceAll("<!\\[CDATA\\[", "");
            String messages=str1.replaceAll("]]>", "");
            message.setText(messages, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendActivationEmail(Users users, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", users.getUsername());
        Locale locale = Locale.forLanguageTag(users.getLangKey());
        Context data = new Context();
        data.setLocale(locale);
        data.setVariable("user",users);
        data.setVariable("baseUrl", baseUrl);
        sendEmail(users.getUsername(), messageSource.getMessage("email.activation.title", null, locale), templateEngine.process("/mails/activationemail.html",data),false, true);
    }

    @Async
    @Override
    public void sendCreationEmail(Users users, String baseUrl) {
        log.debug("Sending creation e-mail to '{}'", users.getUsername());
        Locale locale = Locale.forLanguageTag(users.getLangKey());
        Context data = new Context();
        data.setLocale(locale);
        data.setVariable("user",users);
        data.setVariable("baseUrl",baseUrl);
        sendEmail(users.getUsername(), messageSource.getMessage("email.creation.title", null, locale),templateEngine.process("/mails/creationemail.html",data), false, true);

    }

    @Async
    @Override
    public void sendPasswordResetMail(Users users, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", users.getUsername());
        Locale locale = Locale.forLanguageTag(users.getLangKey());
        Context data = new Context();
        data.setLocale(locale);
        data.setVariable("user",users);
        data.setVariable("baseUrl",baseUrl);
        sendEmail(users.getUsername(), messageSource.getMessage("email.reset.title", null, locale),templateEngine.process("/mails/passwordresetemail.html",data), false, true);
    }
}
