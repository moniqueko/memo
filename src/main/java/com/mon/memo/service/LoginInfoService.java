package com.mon.memo.service;

import com.mon.memo.domain.LoginInfo;
import com.mon.memo.domain.Member;

import java.util.List;

public interface LoginInfoService {
    LoginInfo check(String id, String pw);
    Member checkId(String id);

}
