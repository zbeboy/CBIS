package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.TeachCourseInfo;
import com.school.cbis.service.FileService;
import com.school.cbis.service.TeachCourseInfoService;
import com.school.cbis.service.UsersService;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.eadmin.AddTeachingProcessVo;
import com.school.cbis.vo.eadmin.TeachingProcessListVo;
import org.jooq.Record;
import org.jooq.Record13;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lenovo on 2016-06-05.
 */
@Controller
public class TeachingProcessController {

    private final Logger log = LoggerFactory.getLogger(TeachingProcessController.class);

    @Resource
    private TeachCourseInfoService teachCourseInfoService;

    @Resource
    private UsersService usersService;

    @Resource
    private FileService fileService;

    @Resource
    private Wordbook wordbook;

    /**
     * 教学进程表管理
     *
     * @param teachingProcessListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingProcessList")
    public String teachingProcessList(TeachingProcessListVo teachingProcessListVo, ModelMap modelMap) {
        if (!StringUtils.hasLength(teachingProcessListVo.getTeachCourseInfoTerm())) {
            teachingProcessListVo.setTeachCourseInfoTerm("");
        }
        modelMap.addAttribute("teachingProcessListVo", teachingProcessListVo);
        return "/administrator/eadmin/teachingprocesslist";
    }

    /**
     * 教学进程表管理数据
     *
     * @param teachingProcessListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingProcessData")
    @ResponseBody
    public AjaxData<TeachingProcessListVo> teachingProcessData(TeachingProcessListVo teachingProcessListVo) {
        AjaxData<TeachingProcessListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            Result<Record13<Integer, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date, String, String>> record13 =
                    teachCourseInfoService.findByTieIdAndPageWithUsers(teachingProcessListVo, tieId);
            if (record13.isNotEmpty()) {
                List<TeachingProcessListVo> list = record13.into(TeachingProcessListVo.class);
                list.forEach(r -> {
                    r.setTeachCourseInfoFileSize(FilesUtils.sizeToString(Long.parseLong(r.getTeachCourseInfoFileSize())));
                });
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teachingProcessListVo.getPageNum());
                paginationData.setPageSize(teachingProcessListVo.getPageSize());
                paginationData.setTotalDatas(teachCourseInfoService.findByTieIdAndPageWithUsersCount(teachingProcessListVo, tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.fail();
            }

        } else {
            ajaxData.fail();
        }
        return ajaxData;
    }

    /**
     * 教学进程表管理添加
     *
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingProcessAdd")
    public String teachingProcessAdd() {
        return "/administrator/eadmin/teachingprocessadd";
    }

    /**
     * 添加教学进程表
     *
     * @param addTeachingProcessVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeachingProcess")
    @ResponseBody
    public AjaxData addTeachingProcess(AddTeachingProcessVo addTeachingProcessVo, HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        try {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                String inputFile = request.getSession().getServletContext().getRealPath("/") + addTeachingProcessVo.getFilePath();
                String outputFile =
                        request.getSession().getServletContext().getRealPath("/") + addTeachingProcessVo.getFilePath().substring(0, addTeachingProcessVo.getFilePath().lastIndexOf(".")) + ".pdf";
                File file = new File(inputFile);
                if (fileService.convert2PDF(inputFile, outputFile)) {
                    TeachCourseInfo teachCourseInfo = new TeachCourseInfo();
                    teachCourseInfo.setTeachCourseInfoTerm(addTeachingProcessVo.getTeachCourseInfoTerm());
                    teachCourseInfo.setFileUser(usersService.getUserName());
                    teachCourseInfo.setTeachCourseInfoFileDate(new Timestamp(System.currentTimeMillis()));
                    teachCourseInfo.setTeachCourseInfoFileName(addTeachingProcessVo.getTeachCourseInfoFileName());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    teachCourseInfo.setTermStartTime(new Date(sdf.parse(addTeachingProcessVo.getTermStartTime()).getTime()));
                    teachCourseInfo.setTermEndTime(new Date(sdf.parse(addTeachingProcessVo.getTermEndTime()).getTime()));
                    teachCourseInfo.setTeachCourseInfoFileUrl(addTeachingProcessVo.getFilePath());

                    teachCourseInfo.setFileType(addTeachingProcessVo.getFilePath().substring(addTeachingProcessVo.getFilePath().lastIndexOf(".") + 1));
                    teachCourseInfo.setTeachTypeId(wordbook.getTeachTypeMap().get(addTeachingProcessVo.getTeachType()));
                    teachCourseInfo.setTeachCourseInfoFileDownTimes(0);
                    teachCourseInfo.setTeachCourseInfoFileSize(file.length() + "");
                    teachCourseInfo.setTieId(tieId);
                    teachCourseInfo.setTeachCourseInfoFilePdf(addTeachingProcessVo.getFilePath().substring(0, addTeachingProcessVo.getFilePath().lastIndexOf(".")) + ".pdf");
                    teachCourseInfoService.save(teachCourseInfo);
                    ajaxData.success().msg("保存信息成功!");
                } else {
                    ajaxData.fail().msg("转换为pdf时异常!");
                }
            } else {
                ajaxData.fail().msg("未获取到用户信息!");
            }
        } catch (ParseException e) {
            ajaxData.fail().msg("转换时间异常!");
        }
        return ajaxData;
    }

    /**
     * 预览教学进程表
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingProcessLook")
    public String teachingProcessLook(@RequestParam("id") int id, ModelMap modelMap) {
        TeachCourseInfo teachCourseInfo = teachCourseInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teachCourseInfo)) {
            modelMap.addAttribute("file_path", teachCourseInfo.getTeachCourseInfoFilePdf());
            return "/administrator/eadmin/teachingprocesslook";
        }
        return "redirect:/administrator/eadmin/teachingProcessList";
    }

    /**
     * 编辑教学进程表界面
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingProcessUpdate")
    public String teachingProcessUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        TeachCourseInfo teachCourseInfo = teachCourseInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teachCourseInfo)) {
            modelMap.addAttribute("teachCourseInfo", teachCourseInfo);
            return "/administrator/eadmin/teachingprocessupdate";
        }
        return "redirect:/administrator/eadmin/teachingProcessList";
    }

    /**
     * 编辑教学进程表
     *
     * @param addTeachingProcessVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateTeachingProcess")
    @ResponseBody
    public AjaxData updateTeachingProcess(AddTeachingProcessVo addTeachingProcessVo, HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        try {
            TeachCourseInfo teachCourseInfo = teachCourseInfoService.findById(addTeachingProcessVo.getId());
            teachCourseInfo.setTeachCourseInfoTerm(addTeachingProcessVo.getTeachCourseInfoTerm());
            teachCourseInfo.setTeachCourseInfoFileDate(new Timestamp(System.currentTimeMillis()));
            teachCourseInfo.setTeachCourseInfoFileName(addTeachingProcessVo.getTeachCourseInfoFileName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            teachCourseInfo.setTermStartTime(new Date(sdf.parse(addTeachingProcessVo.getTermStartTime()).getTime()));
            teachCourseInfo.setTermEndTime(new Date(sdf.parse(addTeachingProcessVo.getTermEndTime()).getTime()));

            if (!teachCourseInfo.getTeachCourseInfoFileUrl().equals(StringUtils.trimWhitespace(addTeachingProcessVo.getFilePath()))) {
                String inputFile = request.getSession().getServletContext().getRealPath("/") + addTeachingProcessVo.getFilePath();
                String outputFile =
                        request.getSession().getServletContext().getRealPath("/") + addTeachingProcessVo.getFilePath().substring(0, addTeachingProcessVo.getFilePath().lastIndexOf(".")) + ".pdf";
                File file = new File(inputFile);
                if (fileService.convert2PDF(inputFile, outputFile)) {
                    teachCourseInfo.setTeachCourseInfoFileUrl(addTeachingProcessVo.getFilePath());
                    teachCourseInfo.setFileType(addTeachingProcessVo.getFilePath().substring(addTeachingProcessVo.getFilePath().lastIndexOf(".") + 1));
                    teachCourseInfo.setTeachCourseInfoFileSize(file.length() + "");
                    teachCourseInfo.setTeachCourseInfoFilePdf(addTeachingProcessVo.getFilePath().substring(0, addTeachingProcessVo.getFilePath().lastIndexOf(".")) + ".pdf");
                } else {
                    ajaxData.fail().msg("转换为pdf时异常!");
                    return ajaxData;
                }
            }
            teachCourseInfoService.update(teachCourseInfo);
            ajaxData.success().msg("保存信息成功!");
        } catch (ParseException e) {
            ajaxData.fail().msg("转换时间异常!");
        }
        return ajaxData;
    }

    /**
     * 删除
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/deleteTeachingProcess")
    @ResponseBody
    public AjaxData deleteTeachingProcess(@RequestParam("id") int id, HttpServletRequest request) {
        try {
            TeachCourseInfo teachCourseInfo = teachCourseInfoService.findById(id);
            if (!ObjectUtils.isEmpty(teachCourseInfo)) {
                String webPath = request.getSession().getServletContext().getRealPath("/");
                FilesUtils.deleteFile(webPath + teachCourseInfo.getTeachCourseInfoFileUrl());
                FilesUtils.deleteFile(webPath + teachCourseInfo.getTeachCourseInfoFilePdf());
                teachCourseInfoService.deleteById(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AjaxData().success().msg("删除成功!");
    }
}
