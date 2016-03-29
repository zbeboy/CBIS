package com.school.cbis.web;

import com.school.cbis.data.AjaxData;
import com.school.cbis.data.FileData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Major;
import com.school.cbis.domain.tables.pojos.Tie;
import com.school.cbis.domain.tables.pojos.TieNoticeAffix;
import com.school.cbis.domain.tables.pojos.Yard;
import com.school.cbis.service.*;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.major.MajorListVo;
import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-01-10.
 */
@Controller
public class BackstageController {

    private static Logger logger = Logger.getLogger(BackstageController.class);

    @Resource
    private UploadService upload;

    @Resource
    private UsersService usersService;

    @Resource
    private TieService tieService;

    @Resource
    private YardService yardService;

    @Resource
    private MajorService majorService;

    @Resource
    private TieNoticeAffixService tieNoticeAffixService;

    /**
     * 系管理界面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tie/tieManager")
    public String tieManager(ModelMap map) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Tie tie = tieService.findById(tieId);
        List<Yard> yardList = yardService.findAll();
        Yard yard = new Yard();
        if (!yardList.isEmpty()) {
            for (int i = 0; i < yardList.size(); i++) {
                if (yardList.get(i).getId() == tie.getYardId()) {
                    yard = yardList.get(i);
                    yardList.remove(i);
                    break;
                }
            }
        }
        map.addAttribute("tie", tie);
        map.addAttribute("yardInfo", yard);
        map.addAttribute("yardList", yardList);
        return "/maintainer/tie/tiemanager";
    }

    /**
     * 专业管理界面
     *
     * @return
     */
    @RequestMapping("/maintainer/major/majorManager")
    public String majorManager() {
        return "/maintainer/major/majorlist";
    }

    /**
     * 专业管理界面
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/grade/gradeManager")
    public String gradeManager(ModelMap modelMap) {
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<Major> majors = majorService.findByTieId(tieId);
        modelMap.addAttribute("majorNames", majors);

        List<MajorListVo> majorListVos = new ArrayList<>();
        MajorListVo majorListVo = new MajorListVo();
        majorListVo.setId(0);
        majorListVo.setMajorName("");
        majorListVos.add(majorListVo);
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            List<MajorListVo> majorListVoList = record2s.into(MajorListVo.class);
            majorListVos.addAll(majorListVoList);
        }
        modelMap.addAttribute("majors", majorListVos);
        return "/maintainer/grade/gradelist";
    }

    /**
     * 上传文件
     *
     * @param multipartHttpServletRequest
     * @param request
     * @return 完整文件信息
     */
    @RequestMapping(value = "/maintainer/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData<FileData> uploadPicture(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) {
        AjaxData<FileData> data = new AjaxData();
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/");
            List<FileData> fileDatas = upload.upload(multipartHttpServletRequest, realPath + "files" + File.separator + multipartHttpServletRequest.getParameter("pathname"), request.getRemoteAddr());
            Map<String,Object> map = new HashMap<>();
            map.put("single",fileDatas.get(0));
            data.success().msg(fileDatas.get(0).getLastPath()).listData(fileDatas).mapData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 用户管理界面
     * @return
     */
    @RequestMapping("/maintainer/users/usersManager")
    public String usersManager() {
        return "redirect:/maintainer/users/studentManager";
    }

    /**
     * 删除硬盘中的文件
     *
     * @param path 文件路径
     * @return
     */
    @RequestMapping(value = "/maintainer/deleteFile")
    @ResponseBody
    public AjaxData deleteFile(@RequestParam("path") String path) {
        AjaxData data = new AjaxData();
        try {
            if (!StringUtils.isEmpty(path) && StringUtils.trimWhitespace(path).length() > 0) {
                if (FilesUtils.deleteFile(path)) {
                    data.success().msg("删除文件成功!");
                } else {
                    data.fail().msg("未找到文件!");
                }
            } else {
                data.fail().msg("删除文件失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            data.fail().msg("删除文件失败");
        }
        return data;
    }

    /**
     * 下载公告附件
     * @param id
     * @param response
     */
    @RequestMapping("/user/tie/downloadTieNoticeAffix")
    public void download(@RequestParam("id") int id, HttpServletResponse response) {
        try {
            TieNoticeAffix tieNoticeAffix = tieNoticeAffixService.findById(id);
            if (!StringUtils.isEmpty(tieNoticeAffix)) {
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-disposition", "attachment; filename=\"" + new String((tieNoticeAffix.getTieNoticeFileName() + tieNoticeAffix.getTieNoticeFileUrl().substring(tieNoticeAffix.getTieNoticeFileUrl().lastIndexOf("."))).getBytes("gb2312"), "ISO8859-1") + "\"");
                InputStream inputStream = new FileInputStream(tieNoticeAffix.getTieNoticeFileUrl());
                FileCopyUtils.copy(inputStream, response.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
