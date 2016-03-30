package com.school.cbis.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lenovo on 2016-01-22.
 * 全局分页数据
 */
public class PaginationData {

    private final Logger log = LoggerFactory.getLogger(PaginationData.class);

    private int totalPages;//总页数
    private int totalDatas;//数据总数
    private int pageNum;//当前页
    private int pageSize;//每页大小

    public PaginationData() {
    }

    public PaginationData(int totalPages, int totalDatas, int pageNum, int pageSize) {
        this.totalPages = totalPages;
        this.totalDatas = totalDatas;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalDatas() {
        return totalDatas;
    }

    public void setTotalDatas(int totalDatas) {
        this.totalDatas = totalDatas;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PaginationData{" +
                "totalPages=" + totalPages +
                ", totalDatas=" + totalDatas +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
