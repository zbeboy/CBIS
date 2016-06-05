package com.school.cbis.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by lenovo on 2015/9/11.
 */
public class FilesUtils {

    private final Logger log = LoggerFactory.getLogger(FilesUtils.class);

    public static boolean deleteFile(String path) throws IOException {
        if (Objects.isNull(path)) {
            return false;
        }
        File file = new File(path);
        return file.delete();
    }

    public static String sizeToString(long size) {
        String str = "";
        if (size < 1024) {
            str = size + "B";
        } else if (size >= 1024 && size < 1024 * 1024) {
            str = (size / 1024) + "KB";
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
            str = (size / (1024 * 1024)) + "MB";
        } else {
            str = (size / (1024 * 1024 * 1024)) + "GB";
        }

        return str;
    }

    public static HSSFWorkbook readHSSFFile(String filename) throws IOException {
        return new HSSFWorkbook(new FileInputStream(filename));
    }

    public static XSSFWorkbook readXSSFFile(String filename) throws IOException {
        return new XSSFWorkbook(new FileInputStream(filename));
    }
}
