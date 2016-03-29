package com.school.cbis.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by lenovo on 2015/9/11.
 */
public class FilesUtils {

    private static Logger logger = Logger.getLogger(FilesUtils.class);

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
}
