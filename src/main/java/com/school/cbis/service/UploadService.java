package com.school.cbis.service;

import com.school.cbis.data.FileData;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * Created by lenovo on 2016-01-10.
 */
public interface UploadService {
    /**
     * 上传文件
     *
     * @param request 请求对象
     * @param path    根路径
     * @param address 地址
     * @return
     */
    List<FileData> upload(MultipartHttpServletRequest request, String path, String address);
}
