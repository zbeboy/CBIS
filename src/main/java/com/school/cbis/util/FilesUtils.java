package com.school.cbis.util;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by lenovo on 2015/9/11.
 */
public class FilesUtils {
    public static boolean deleteFile(String path) throws IOException {
        if (!Objects.isNull(path)) {
            return false;
        }
        File file = new File(path);
        return file.delete();
    }
}
