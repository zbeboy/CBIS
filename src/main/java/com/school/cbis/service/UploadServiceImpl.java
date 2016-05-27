package com.school.cbis.service;

import com.school.cbis.data.FileData;
import com.school.cbis.util.IPTimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2016-01-10.
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    private final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Override
    public List<FileData> upload(MultipartHttpServletRequest request, String path, String address) {
        List<FileData> list = new ArrayList<>();
        //1. build an iterator.
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = null;
        //2. get each file
        while (iterator.hasNext()) {
            FileData fileData = new FileData();
            //2.1 get next MultipartFile
            multipartFile = request.getFile(iterator.next());
            log.info(multipartFile.getOriginalFilename() + " uploaded!");
            fileData.setContentType(multipartFile.getContentType());
            IPTimeStamp ipTimeStamp = new IPTimeStamp(address);
            String[] words = multipartFile.getOriginalFilename().split("\\.");
            if (words.length > 1) {
                String ext = words[words.length - 1];
                String filename = ipTimeStamp.getIPTimeRand() + "." + ext;
                if (filename.contains(":")) {
                    filename = filename.substring(filename.lastIndexOf(":") + 1, filename.length());
                }
                fileData.setOriginalFilename(multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
                fileData.setExt(ext);
                fileData.setNewName(filename);
                fileData.setSize(multipartFile.getSize());
                //copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
                buildList(fileData, list, path, filename, multipartFile);

            } else {
                // no filename
                String filename = ipTimeStamp.getIPTimeRand();
                fileData.setOriginalFilename(multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
                fileData.setNewName(filename);
                fileData.setSize(multipartFile.getSize());
                // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
                buildList(fileData, list, path, filename, multipartFile);
            }
        }

        return list;
    }

    private String buildPath(String path, String filename, MultipartFile multipartFile) throws IOException {
        String lastPath = null;
        File saveFile = new File(path, filename);
        if (multipartFile.getSize() < new File(path.split(":")[0] + ":").getFreeSpace()) {// has space with disk
            if (!saveFile.getParentFile().exists()) {//create file
                saveFile.getParentFile().mkdirs();
            }
            log.info(path);
            FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(path + File.separator + filename));
            lastPath = path + File.separator + filename;
            lastPath = lastPath.replaceAll("\\\\","/");
        } else {
            log.info("not valiablespace!");
            return null;
        }
        return lastPath;
    }

    private List<FileData> buildList(FileData fileData, List<FileData> list, String path, String filename, MultipartFile multipartFile) {
        try {
            if (!StringUtils.isEmpty(path.split(":")[0])) {
                fileData.setLastPath(buildPath(path, filename, multipartFile));
                list.add(fileData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 文件下载
     * @param fileName 文件名 不带后缀
     * @param filePath 完整文件路径 如: files/c.doc
     * @param response
     * @param request
     */
    public void download(String fileName, String filePath, HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment; filename=\"" + new String((fileName + filePath.substring(filePath.lastIndexOf("."))).getBytes("gb2312"), "ISO8859-1") + "\"");
            String realPath = request.getSession().getServletContext().getRealPath("/");
            InputStream inputStream = new FileInputStream(realPath + filePath);
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error(" file is not found exception is {} ", e.getMessage());
        }
    }
}
