package com.mon.memo.domain;

public class Paging {

    public Paging() {}

    public Paging(int section, int pageNum, int productType) {
        this.section = section;
        this.pageNum = pageNum;
        this.productType = productType;
    }

    public Paging(int section, int pageNum) {
        this.section = section;
        this.pageNum = pageNum;
    }

    public Paging(String keyword, int section, int pageNum) {
        this.keyword = keyword;
        this.section = section;
        this.pageNum = pageNum;
    }

    private int productType;
    private String keyword;
    private int section, pageNum;

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

}