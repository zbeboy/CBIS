package com.school.cbis.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.ArticleInfoRecord;
import com.school.cbis.domain.tables.records.ArticleSubRecord;
import com.school.cbis.domain.tables.records.TieElegantRecord;
import com.school.cbis.domain.tables.records.TieRecord;
import com.school.cbis.service.ArticleService;
import com.school.cbis.service.TieService;
import com.school.cbis.service.UploadService;
import com.school.cbis.service.UsersService;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.*;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Record5;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016-01-10.
 */
@Controller
public class BackstageController {

    @Resource
    private UploadService upload;

    @Resource
    private ArticleService articleService;

    @Resource
    private UsersService usersService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private TieService tieService;

    /**
     * 系风采管理页面
     *
     * @return 页面地址
     */
    @RequestMapping("/admin/tieelegant")
    public String backstageTieElegant(ModelMap map) {
        map.addAttribute("pagination", new PaginationData(0, 1, 10));
        map.addAttribute("startSearch", false);//不启用搜索，使用angularjs获取数据
        return "/admin/tieelegantlist";
    }

    /**
     * 上传图片
     *
     * @param multipartHttpServletRequest
     * @param request
     * @return 图片保存完整路径
     */
    @RequestMapping(value = "/admin/uploadPicture", method = RequestMethod.POST)
    @ResponseBody
    public String uploadTieElegantPicture(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) {
        AjaxData data = null;
        String lastPath = null;
        try {
            data = new AjaxData();
            String realPath = request.getSession().getServletContext().getRealPath("/");
            lastPath = upload.upload(multipartHttpServletRequest, realPath + "files" + File.separator + multipartHttpServletRequest.getParameter("pathname"), request.getRemoteAddr());
            data.setState(true);
            data.setMsg(lastPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastPath;
    }

    /**
     * 删除硬盘中的图片
     *
     * @param path 真实图片路径
     * @return
     */
    @RequestMapping(value = "/admin/deletePictue", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deleteTieElegantPicture(@RequestParam("path") String path) {
        AjaxData data = null;
        try {
            data = new AjaxData();
            if (!StringUtils.isEmpty(path) && StringUtils.trimWhitespace(path).length() > 0) {
                if (FilesUtils.deleteFile(path)) {
                    data.setState(true);
                    data.setMsg("删除图片成功！");
                } else {
                    data.setState(false);
                    data.setMsg("未找到图片！");
                }
            } else {
                data.setState(false);
                data.setMsg("删除图片失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            data.setState(false);
            data.setMsg("删除图片失败！");
        }
        return data;
    }
}
