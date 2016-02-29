package com.school.cbis.data;

/**
 * Created by lenovo on 2016-01-22.
 */
public class PaginationData {
    private int totalPages;
    private int pageNum;
    private int pageSize;

    public PaginationData(int totalPages, int pageNum, int pageSize) {
        this.totalPages = totalPages;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
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
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
