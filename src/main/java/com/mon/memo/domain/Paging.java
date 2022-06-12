package com.mon.memo.domain;

import lombok.Data;

@Data
public class Paging {
    private String memberId;
    private String keyword;
    private int section, pageNum;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Paging() {}

    public Paging(String memberId, String keyword, int section, int pageNum) {
        this.memberId = memberId;
        this.keyword = keyword;
        this.section = section;
        this.pageNum = pageNum;
    }

    public Paging(String memberId, int section, int pageNum) {
        this.memberId = memberId;
        this.section = section;
        this.pageNum = pageNum;
    }

    public Paging(int section, int pageNum) {
        this.section = section;
        this.pageNum = pageNum;
    }

//    public Paging(String keyword, int section, int pageNum) {
//        this.keyword = keyword;
//        this.section = section;
//        this.pageNum = pageNum;
//    }


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