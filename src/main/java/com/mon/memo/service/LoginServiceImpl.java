package com.mon.memo.service;

import com.mon.memo.domain.Member;
import com.mon.memo.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    Mapper mapper;

}
