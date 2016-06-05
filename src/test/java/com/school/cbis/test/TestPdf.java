package com.school.cbis.test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

/**
 * Created by lenovo on 2016-06-05.
 */
public class TestPdf {

    @Test
    public void testPdf(){
//        excel2PDF("f:/task.xls","f:/task.pdf");
        Properties properties = System.getProperties();
        System.out.println(properties);
    }

}
