package com.mon.memo.service;

import com.mon.memo.domain.Login;
import com.mon.memo.domain.Member;
import com.mon.memo.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class MemberRegisterServiceImpl implements MemberRegisterService{

    @Autowired
    Mapper mapper;

    private final HttpSession httpSession;


    @Override
    public void register(Login login){
        Member newMember = new Member(login.getNo(), login.getId(), login.getPw(), login.getEmail());
        mapper.insertMember(newMember);

        httpSession.setAttribute("newMember",newMember);
    }

}
