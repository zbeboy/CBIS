/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.school.cbis.cache;

import com.school.cbis.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Component
public class CacheManagerCheck implements CommandLineRunner {

    private static final Logger logger = LoggerFactory
            .getLogger(Application.class);

    private final CacheManager cacheManager;

    @Value("${cbis.jacob.version}")
    private String jacobDllVersion;

    @Autowired
    public CacheManagerCheck(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("\n\n" + "=========================================================\n"
                + "Using cache manager: " + this.cacheManager.getClass().getName() + "\n"
                + "=========================================================\n\n");
        jacobDllCheck();
    }

    /**
     * 检查用于转换pdf 的 jacob 的dll是否存在
     */
    public void jacobDllCheck() {
        /**
         * 支持64位
         */
        String jacobDllName = "jacob-" + jacobDllVersion + "-x64.dll";
        try {
            Properties properties = System.getProperties();
            logger.info("jacob version : {}", jacobDllVersion);
            String userDllPath = properties.get("sun.boot.library.path") + File.separator + jacobDllName;
            File userFile = new File(userDllPath);
            if (userFile.exists()) {
                logger.info("success load jacob {}.", jacobDllName);
            } else {
                String projectDllPath = properties.get("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "dll" + File.separator + jacobDllName;
                logger.debug("not found jacob in java lib path.Reading Copy {} to {}.", projectDllPath, userDllPath);
                File projectFile = new File(projectDllPath);
                if (projectFile.exists()) {
                    FileCopyUtils.copy(projectFile, userFile);
                    logger.info("success copy {} to {}.", jacobDllName, userDllPath);
                } else {
                    logger.error("fail copy ! not found {}.", projectDllPath);
                }
            }
        } catch (IOException e) {
            logger.error("copy {} is exception : {},check your project dll path or jacob version.", jacobDllName, e.getMessage());
        }

    }

}
