package com.school.cbis.web;

import com.school.cbis.service.UsersService;
import com.school.cbis.util.MD5Util;
import com.school.cbis.vo.AjaxData;
import com.school.cbis.vo.RevisePasswordVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by lenovo on 2016-01-09.
 */
@Controller
public class PersonalController {

    @Resource
    private UsersService usersService;// 用户表

    /**
     * 修改密码页面
     *
     * @return 页面地址
     */
    @RequestMapping("/student/revisepassword")
    public String revisePassword(ModelMap map) {
        map.addAttribute("passworderror", false);//校验旧密码
        map.addAttribute("revisePasswordVo", new RevisePasswordVo());//页面参数
        map.addAttribute("validationerror", false);//校验页面参数有错
        map.addAttribute("validationsuccess", false);//校验成功
        map.addAttribute("msg", "");//错误消息
        return "/student/revisepassword";
    }

    /**
     * 更新密码
     *
     * @return 消息
     */
    @RequestMapping(value = "/student/updatepassword", method = RequestMethod.POST)
    public String updatePassword(@Valid RevisePasswordVo passwordVo, BindingResult bindingResult, ModelMap map) {
        if (!bindingResult.hasErrors()) {
            String oldPassword = StringUtils.trimWhitespace(passwordVo.getOldPassword());
            if (!StringUtils.isEmpty(usersService.getPassword())) {//用户登录密码
                if (MD5Util.md5(oldPassword).equals(usersService.getPassword())) {//校验旧密码
                    if (StringUtils.trimWhitespace(passwordVo.getNewPassword()).equals(StringUtils.trimWhitespace(passwordVo.getOkPassword()))) {//确认密码一致
                        if (usersService.updatePassword(StringUtils.trimWhitespace(usersService.getUserName()),MD5Util.md5(passwordVo.getOkPassword()))) {//存入数据库
                            map.addAttribute("passworderror", false);//校验旧密码
                            map.addAttribute("revisePasswordVo", new RevisePasswordVo());//页面参数
                            map.addAttribute("validationerror", false);//校验页面参数有错
                            map.addAttribute("validationsuccess", true);//校验成功
                            map.addAttribute("msg", "修改成功，请点击退出按钮，重新登录！");//错误消息
                        } else {
                            map.addAttribute("passworderror", false);//校验旧密码
                            map.addAttribute("revisePasswordVo", new RevisePasswordVo());//页面参数
                            map.addAttribute("validationerror", true);//校验页面参数有错
                            map.addAttribute("validationsuccess", false);//校验成功
                            map.addAttribute("msg", "保存失败！");//错误消息
                        }
                    } else {
                        map.addAttribute("passworderror", false);//校验旧密码
                        map.addAttribute("revisePasswordVo", new RevisePasswordVo());//页面参数
                        map.addAttribute("validationerror", true);//校验页面参数有错
                        map.addAttribute("validationsuccess", false);//校验成功
                        map.addAttribute("msg", "密码不一致！");//错误消息
                    }
                } else {
                    map.addAttribute("passworderror", true);//校验旧密码
                    map.addAttribute("revisePasswordVo", new RevisePasswordVo());//页面参数
                    map.addAttribute("validationerror", false);//校验页面参数有错
                    map.addAttribute("validationsuccess", false);//校验成功
                    map.addAttribute("msg", "");//错误消息
                }
            } else {
                return "/login";
            }
        }
        return "/student/revisepassword";
    }

    /**
     * 验证原来的密码是否正确
     * @param oldPassword
     * @return ajax
     */
    @RequestMapping(value = "/student/validpassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData validPassword(@RequestParam("oldPassword") String oldPassword) {
        String regex = "^[\\w]{6,20}$";
        String op = StringUtils.trimWhitespace(oldPassword);
        AjaxData ajaxData = new AjaxData();
        if (op.matches(regex)) {
            if (StringUtils.trimWhitespace(usersService.getPassword()).equals(MD5Util.md5(op))) {
                ajaxData.setState(true);
            } else {
                ajaxData.setState(false);
                ajaxData.setMsg("*密码错误！");
            }
        } else {
            ajaxData.setState(false);
            ajaxData.setMsg("*密码长度为6~20长度！");
        }
        return ajaxData;
    }
}
