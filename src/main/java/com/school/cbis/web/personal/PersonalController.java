package com.school.cbis.web.personal;

import com.school.cbis.domain.tables.pojos.Users;
import com.school.cbis.service.AuthoritiesService;
import com.school.cbis.service.TeacherService;
import com.school.cbis.service.UsersService;
import com.school.cbis.util.MD5Util;
import com.school.cbis.data.AjaxData;
import com.school.cbis.vo.personal.RevisePasswordVo;
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
import java.util.HashMap;
import java.util.Map;

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
    @RequestMapping("/student/revisePassword")
    public String revisePassword(ModelMap map) {
        buildMap(map, false, new RevisePasswordVo(), false, false, "");
        return "/student/revisepassword";
    }

    /**
     * 更新密码
     *
     * @return 消息
     */
    @RequestMapping(value = "/student/updatePassword", method = RequestMethod.POST)
    public String updatePassword(@Valid RevisePasswordVo passwordVo, BindingResult bindingResult, ModelMap map) {
        if (!bindingResult.hasErrors()) {
            String oldPassword = StringUtils.trimWhitespace(passwordVo.getOldPassword());
            if (!StringUtils.isEmpty(usersService.getPassword())) {//用户登录密码
                if (MD5Util.md5(oldPassword).equals(usersService.getPassword())) {//校验旧密码
                    if (StringUtils.trimWhitespace(passwordVo.getNewPassword()).equals(StringUtils.trimWhitespace(passwordVo.getOkPassword()))) {//确认密码一致
                        Users users = usersService.findByUsername(usersService.getUserName());
                        users.setPassword(MD5Util.md5(passwordVo.getOkPassword()));
                        users.setUsername(usersService.getUserName());
                        usersService.update(users);//存入数据库
                        buildMap(map, false, new RevisePasswordVo(), false, true, "修改成功，请点击退出按钮，重新登录！");

                    } else {
                        buildMap(map, false, new RevisePasswordVo(), true, false, "密码不一致！");
                    }
                } else {
                    buildMap(map, true, new RevisePasswordVo(), false, false, "");
                }
            } else {
                return "/login";
            }
        }
        return "/student/revisepassword";
    }

    /**
     * 验证原来的密码是否正确
     *
     * @param oldPassword
     * @return ajax
     */
    @RequestMapping(value = "/student/validPassword", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> validPassword(@RequestParam("oldPassword") String oldPassword) {
        Map<String,Object> map = new HashMap<>();
        String regex = "^[\\w]{6,20}$";
        String op = StringUtils.trimWhitespace(oldPassword);
        AjaxData ajaxData = new AjaxData();
        if (op.matches(regex)) {
            if (StringUtils.trimWhitespace(usersService.getPassword()).equals(MD5Util.md5(op))) {
                map.put("ok","");
            } else {
                map.put("error","密码错误!");
            }
        } else {
            map.put("error","密码由6到20位任意字符组成!");
        }
        return map;
    }

    /**
     * 组装消息 modelMap
     *
     * @param map               ModelMap
     * @param passwordError     密码错误
     * @param revisePasswordVo  页面数据对象
     * @param validationError   校验数据错误
     * @param validationSuccess 校验数据成功
     * @param msg               回调消息
     */
    private void buildMap(ModelMap map, boolean passwordError, RevisePasswordVo revisePasswordVo,
                          boolean validationError, boolean validationSuccess, String msg) {
        map.addAttribute("passwordError", passwordError);//校验旧密码
        map.addAttribute("revisePasswordVo", revisePasswordVo);//页面参数
        map.addAttribute("validationError", validationError);//校验页面参数有错
        map.addAttribute("validationSuccess", validationSuccess);//校验成功
        map.addAttribute("msg", msg);//错误消息
    }
}
