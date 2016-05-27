package com.school.cbis.web;

import com.school.cbis.data.AjaxData;
import com.school.cbis.data.FileData;
import com.school.cbis.service.UploadService;
import com.school.cbis.util.FilesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-01-10.
 * 后台管理共同方法
 */
@Controller
public class BackstageController {

    private final Logger log = LoggerFactory.getLogger(BackstageController.class);

    @Resource
    private UploadService upload;

    /**
     * 上传文件
     *
     * @param multipartHttpServletRequest
     * @param request
     * @return 完整文件信息
     */
    @RequestMapping(value = "/student/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData<FileData> uploadPicture(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) {
        AjaxData<FileData> data = new AjaxData();
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/");
            List<FileData> fileDatas = upload.upload(multipartHttpServletRequest, realPath + "files" + File.separator + multipartHttpServletRequest.getParameter("pathname"), request.getRemoteAddr());
            Map<String, Object> map = new HashMap<>();
            map.put("single", fileDatas.get(0));
            data.success().msg(fileDatas.get(0).getLastPath()).listData(fileDatas).mapData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 删除硬盘中的文件
     *
     * @param path 文件路径
     * @return
     */
    @RequestMapping(value = "/student/deleteFile")
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
}
