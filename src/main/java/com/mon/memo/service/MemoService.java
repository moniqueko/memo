package com.mon.memo.service;

import com.mon.memo.domain.Memo;
import com.mon.memo.domain.MemoCommand;

import java.util.HashMap;

public interface MemoService {
    void write(MemoCommand memoCommand);

}
