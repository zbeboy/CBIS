package com.school.cbis.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by lenovo on 2016-01-10.
 */
public interface UploadService {
    String upload(MultipartHttpServletRequest request,String path,String address);
}
