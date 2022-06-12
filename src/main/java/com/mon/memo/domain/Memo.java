package com.mon.memo.domain;

import lombok.Data;

@Data
public class Memo {
    private int memoNum;
    private String memberId;
    private String memo;


    public Memo(){}

    public Memo(int memoNum, String memberId, String memo) {
        this.memoNum = memoNum;
        this.memberId = memberId;
        this.memo = memo;
    }

    public int getMemoNum() {
        return memoNum;
    }

    public void setMemoNum(int memoNum) {
        this.memoNum = memoNum;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
