package com.xx.xxtest.influxdb.dto;

public class Pagination {

    private int pageSize;

    private  int pageNum;

    private int pageIndex;

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

    public int getPageIndex() {
        return (pageNum - 1) * pageSize;
    }

}
