package com.mon.memo.domain;

import lombok.Data;

@Data
public class LoginInfo { //로그인시
    private String id;
    private int no;
    private String pw;
    private String email;

    public LoginInfo(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public LoginInfo(String id, int no, String pw, String email) {
        this.id = id;
        this.no = no;
        this.pw = pw;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public LoginInfo () {}
}
