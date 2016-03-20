package com.school.cbis.web.users;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Teacher;
import com.school.cbis.domain.tables.pojos.Users;
import com.school.cbis.service.TeacherService;
import com.school.cbis.service.UsersService;
import com.school.cbis.util.MD5Util;
import com.school.cbis.vo.users.TeacherVo;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-03-20.
 */
@Controller
public class UsersController {

    @Resource
    private UsersService usersService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private Wordbook wordbook;

    /**
     * 学生管理界面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/studentManager")
    public String studentManager(ModelMap map) {
        return "/maintainer/studentlist";
    }

    /**
     * 教师管理界面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/teacherManager")
    public String teacherManager(ModelMap map,TeacherVo teacherVo) {
        List<TeacherVo> teacherVos = new ArrayList<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<Record5<Integer, String, String, Byte, String>> record5s = teacherService.findByTieIdAndPage(teacherVo,tieId);
        if (record5s.isNotEmpty()) {
            teacherVos = record5s.into(TeacherVo.class);
        }
        if(!StringUtils.isEmpty(teacherVo)){
            teacherVo.setTotalData(teacherService.findByTieIdAndPageCount(teacherVo,tieId));
        }
        map.addAttribute("teachers", teacherVos);
        map.addAttribute("teacherVo",teacherVo);
        return "/maintainer/teacherlist";
    }

    /**
     * 重置密码
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/maintainer/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData resetPassword(@RequestParam("username") String username) {
        AjaxData ajaxData = new AjaxData();
        if (!StringUtils.isEmpty(username)) {
            Users users = usersService.findByUsername(username);
            users.setPassword(MD5Util.md5(username));
            usersService.update(users);
            ajaxData.setState(true);
            ajaxData.setMsg("更新密码成功，默认密码为账号!");
        } else {
            ajaxData.setState(false);
            ajaxData.setMsg("参数异常!");
        }
        return ajaxData;
    }

    /**
     * 更新用户状态
     *
     * @return
     */
    @RequestMapping(value = "/maintainer/resetEnable", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData resetEnable(@RequestParam("username") String username, @RequestParam("enable") Byte enable) {
        AjaxData ajaxData = new AjaxData();
        if (!StringUtils.isEmpty(username)) {
            Users users = usersService.findByUsername(username);
            users.setEnabled(enable);
            usersService.update(users);
            ajaxData.setState(true);
            ajaxData.setMsg("更新状态成功!");
        } else {
            ajaxData.setState(false);
            ajaxData.setMsg("参数异常!");
        }
        return ajaxData;
    }

    /**
     * 添加教师用户
     *
     * @param username
     * @param realname
     * @return
     */
    @RequestMapping(value = "/maintainer/addTeacher", method = RequestMethod.POST)
    public String addTeacher(@RequestParam("username") String username, @RequestParam("realname") String realname) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Teacher teacher = new Teacher();
        teacher.setTeacherJobNumber(username);
        teacher.setTeacherName(realname);
        teacher.setTieId(tieId);
        teacherService.save(teacher);
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(MD5Util.md5(username));
        Byte b = 1;
        users.setEnabled(b);
        users.setUserTypeId(wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER));
        usersService.save(users);
        return "redirect:/maintainer/teacherManager";
    }

    /**
     * 检验账号
     * @param username
     * @return
     */
    @RequestMapping(value = "/maintainer/validUsername", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> validUsername(@RequestParam("username") String username){
        Map<String,Object> map = new HashMap<>();
        Users users = usersService.findByUsername(username);
        if(StringUtils.isEmpty(users)){
            map.put("ok", "");
        } else {
            map.put("error", "该账号已存在!");
        }
        return map;
    }
}
