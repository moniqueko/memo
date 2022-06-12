package com.mon.memo.service;

import com.mon.memo.domain.LoginInfo;
import com.mon.memo.domain.Member;
import com.mon.memo.exception.IdPasswordNotMatchingException;
import com.mon.memo.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginInfoServiceImpl implements LoginInfoService{

    @Autowired
    Mapper mapper;

    @Override
    public LoginInfo check(String id, String pw) {

        Member member = mapper.selectByIdAll(id);

        if(member==null) {
            throw new IdPasswordNotMatchingException();
        }
        if(!member.getMemberPw().equals(pw)) {
            throw new IdPasswordNotMatchingException();
        }

        return new LoginInfo(member.getMemberId(), member.getMemberNum(), member.getMemberPw(),member.getMemberEmail());
    }

    @Override
    public Member checkId(String id) {
        Member member = mapper.selectByIdAll(id);
        return member;
    }

}
