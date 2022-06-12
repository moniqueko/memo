package com.mon.memo.service;

import com.mon.memo.domain.Member;
import com.mon.memo.domain.Memo;
import com.mon.memo.domain.MemoCommand;
import com.mon.memo.domain.Paging;
import com.mon.memo.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public void delete(int memoNum) {
        mapper.deleteMemo(memoNum);
    }

    @Override
    public List<Memo> savedMemo(String memberId) {
        return mapper.memoAll(memberId);
    }


    @Override
    public int pagingCount(String memberId) {
        Integer count = mapper.pagingCount(memberId);
        return count;
    }

    @Override
    public int pagingCountSearch(Paging paging) {
        Integer count = mapper.pagingCountSearch(paging);
        return count;
    }

    @Override
    public List<Memo> selectSearchPaging(Paging paging) {
        List<Memo> list = mapper.selectSearchPaging(paging);
        return list;
    }

    @Override
    public List<Memo> selectAllMemo(Paging paging) { //리스트로 출력시
        List<Memo> list = mapper.selectAllMemo(paging);
        return list;
    }

    @Override
    public String totalCntJudge(int totalCnt) {
        String judge = "";
        if(totalCnt > 100) judge = "101";
        if(totalCnt == 100) judge = "100";
        if(totalCnt < 100) judge = "99";

        return judge;
    }


}
