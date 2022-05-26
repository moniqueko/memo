package com.mon.memo.service;

import com.mon.memo.domain.Member;
import com.mon.memo.domain.Memo;
import com.mon.memo.domain.MemoCommand;
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
    public void write(MemoCommand memoCommand) {

        Memo newMemo = new Memo(memoCommand.getMno(), memoCommand.getId(), memoCommand.getMemo());
        mapper.insertMemo(newMemo);

    }


}
