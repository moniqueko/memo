package com.mon.memo.service;

import com.mon.memo.domain.Memo;
import com.mon.memo.domain.MemoCommand;
import com.mon.memo.domain.Paging;

import java.util.List;

public interface MemoService {
    void write(MemoCommand memoCommand);
    void delete(Integer memoNum);

    List<Memo> savedMemo(String memberId);
    List<Memo> selectAllMemo(Paging paging);

    String totalCntJudge(int totalCnt);
    Integer pagingCount(String memberId);

    Integer pagingCountSearch(Paging paging);
    List<Memo> selectSearchPaging(Paging paging);




}
