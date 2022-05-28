package com.mon.memo.mapper;

import com.mon.memo.domain.Member;
import com.mon.memo.domain.Memo;
import com.mon.memo.domain.Paging;

import java.util.List;


@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    List<Member> selectAllMember();
    Member selectByIdAll(String id);
    void insertMember(Member member);
    void insertMemo(Memo memo);

    List<Memo> memoAll(String memberId);

    List<Memo> selectBoardPaging(Paging paging);
    Integer pagingCount(String memberId);
    Integer pagingCountSearch(Paging paging);

    void updateMember(Member member);
    void deleteMember(Integer memberNum);
    Member selectByEmail(String memberEmail); //이메일 존재 여부만 판단
    Member selectByEmailOnly(String memberEmail); //이메일로 회원정보 찾기

}
