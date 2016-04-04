package com.school.cbis.web.autonomicpractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lenovo on 2016-04-04.
 */
@Controller
public class AutonomicPractice {

    private final Logger log = LoggerFactory.getLogger(AutonomicPractice.class);

    @RequestMapping("/maintainer/users/autonomicPracticeTitle")
    public String autonomicPracticeTitle(){
        return "/administrator/autonomicpractice/autonomicpracticetitle";
    }

    @RequestMapping("/maintainer/users/autonomicPracticeWork")
    public String autonomicPracticeWork(){
        return "/administrator/autonomicpractice/autonomicpracticework";
    }
}
