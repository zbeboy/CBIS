package com.school.cbis.web;

import com.school.cbis.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2016-01-05.
 */
@Controller
public class CbisController {

    @Resource
    private UsersService usersService;

    @RequestMapping("/")
    public String root() {
        return "/user/index";
    }

    @RequestMapping("/backstage")
    public String backstage() {
        //是否已登录
        System.out.println("########################");
        System.out.println(StringUtils.isEmpty(usersService.getUserName()) );
        if(StringUtils.isEmpty(usersService.getUserName())){
            return "/login";
        }
        return "/sadmin/backstagemanagement";
    }
}
