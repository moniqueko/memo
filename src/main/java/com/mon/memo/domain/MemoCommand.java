package com.mon.memo.domain;

import lombok.Data;

@Data
public class MemoCommand {
        private int mno;
        private String id;

        public MemoCommand(int mno, String id, String memo) {
                this.mno = mno;
                this.id = id;
                this.memo = memo;
        }

        private String memo;

        public MemoCommand(String id, String memo) {
                this.id = id;
                this.memo = memo;
        }

        public int getMno() {
                return mno;
        }

        public void setMno(int mno) {
                this.mno = mno;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getMemo() {
                return memo;
        }

        public void setMemo(String memo) {
                this.memo = memo;
        }

        public MemoCommand(){}
}
