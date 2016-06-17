package com.school.cbis.exception;

/**
 * Created by lenovo on 2016-05-31.
 */
public class TeachTaskExceptionHandle extends Exception {

    private String problem;

    public TeachTaskExceptionHandle(String problem) {
        super(" 教学任务书导入数据库发生异常 : " + problem);
        this.problem = problem;
    }

    public String getProblem() {
        return problem;
    }
}
