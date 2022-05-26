package com.mon.memo.service;

import com.mon.memo.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService{
    @Autowired
    Mapper mapper;

    @Override
    public void write(String id, String memo) {
        mapper.insertMemo(id, memo);
    }


}
