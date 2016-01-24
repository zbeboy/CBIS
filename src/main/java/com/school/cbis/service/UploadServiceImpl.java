package com.school.cbis.service;

import com.school.cbis.util.IPTimeStamp;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2016-01-10.
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    private static Logger logger = Logger.getLogger(UploadServiceImpl.class);

    @Override
    public String upload(MultipartHttpServletRequest request, String path, String address) {
        String lastPath = null;
        try{
            //1. build an iterator.
            Iterator<String > iterator = request.getFileNames();
            MultipartFile multipartFile = null;
            List<String > list = new ArrayList<>();

            //2. get each file
            while(iterator.hasNext()){
                //2.1 get next MultipartFile
                multipartFile = request.getFile(iterator.next());
                logger.info(multipartFile.getOriginalFilename() + " uploaded!");
                list.add(multipartFile.getContentType());
                IPTimeStamp ipTimeStamp = new IPTimeStamp(address);
                String[] words = multipartFile.getOriginalFilename().split("\\.");
                if(words.length > 1){
                    String ext = words[words.length-1];
                    String filename = ipTimeStamp.getIPTimeRand() + "."+ext;
                    if(filename.contains(":")){
                        filename = filename.substring(filename.lastIndexOf(":")+1,filename.length());
                    }
                    //copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
                    if(!StringUtils.isEmpty(path.split(":")[0])){
                        lastPath = buildPath(path,filename,multipartFile);
                    } else {
                        logger.info("get disk fail!");
                        return null;
                    }
                } else {
                    // no filename
                    String filename = ipTimeStamp.getIPTimeRand();
                    // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
                    if(!StringUtils.isEmpty(path.split(":")[0])){
                        lastPath = buildPath(path,filename,multipartFile);
                    } else {
                        logger.info("get disk fail!");
                        return null;
                    }
                }
            }
        }catch (IOException e) {
            logger.error("upload fail! "+e.getMessage() + " "+e.getStackTrace());
            return null;
        }
        return lastPath;
    }

    private String buildPath(String path,String filename,MultipartFile multipartFile) throws IOException {
        String lastPath = null;
        File saveFile = new File(path,filename);
        if(multipartFile.getSize()<new File(path.split(":")[0]+":").getFreeSpace()){// has space with disk
            if(!saveFile.getParentFile().exists()){//create file
                saveFile.getParentFile().mkdirs();
            }
            logger.info(path);
            FileCopyUtils.copy(multipartFile.getBytes(),new FileOutputStream(path + File.separator + filename));
            lastPath = path + File.separator + filename;
        } else {
            logger.info("not valiablespace!");
            return null;
        }
        return lastPath;
    }
}
