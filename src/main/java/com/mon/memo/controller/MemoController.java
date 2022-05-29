package com.mon.memo.controller;

import com.mon.memo.domain.*;
import com.mon.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemoController {

    private final MemoService memoService;

    @RequestMapping("/memo/write")
    @ResponseBody
    public HashMap<String, Object> memo(Model model, @RequestBody String memo, HttpSession session) { //json으로 받아서 저장.

        LoginInfo log = (LoginInfo) session.getAttribute("loginInfo");
        System.out.println(log + "세션저장되어있음");
        System.out.println(memo + "받아온 정보 출력");

        MemoCommand save = new MemoCommand();
        save.setMemo(memo.replaceAll("\"", ""));
        save.setId(log.getId());

        memoService.write(save);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("result", memo);

        return map;
    }

    @GetMapping(value = "/memo") //저장된 메모 불러오기
    public String saved(Memo memo, Login login, Errors errors, Model model, HttpSession session,
                        @RequestParam(value = "section", defaultValue="1") int section,
                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                        @RequestParam(value="keyword", required = false) String keyword,
                        @ModelAttribute("Paging") Paging paging) {

        LoginInfo log = (LoginInfo) session.getAttribute("loginInfo");
        String memberId = log.getId();

        if (keyword==null) {
            paging = new Paging(memberId, section, pageNum);

            int totalCnt = memoService.pagingCount(memberId);

            List<Memo> memoall = memoService.selectAllMemo(paging);
            String totalCntJudge = memoService.totalCntJudge(totalCnt);

            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("totalCntJudge", totalCntJudge);
            model.addAttribute("section", section);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("savedmemo", memoall);
            System.out.println("키워드 없음 실행");

        }else if(keyword!=null){

            paging = new Paging(memberId, keyword, section, pageNum);
            int totalCnt = memoService.pagingCountSearch(paging);

            List<Memo> memoall = memoService.selectSearchPaging(paging);
            String totalCntJudge =  memoService.totalCntJudge(totalCnt);

            model.addAttribute("totalCntJudge", totalCntJudge);
            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("section", section);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("savedmemo", memoall);
            model.addAttribute("keyword", keyword);
            System.out.println("키워드 있음 실행");

        }

        return "memo/memo";


    }

    @RequestMapping(value = "/memo/delete/{memoNum}") //게시글 삭제
    public String delete(@PathVariable("memoNum") int memoNum, Model model,
                         @RequestParam(value = "section", defaultValue = "1") int section,
                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                         @RequestParam(value="keyword", required = false) String keyword,
                         @ModelAttribute("Paging") Paging paging,
                         HttpSession session){

        LoginInfo log = (LoginInfo) session.getAttribute("loginInfo");
        String memberId = log.getId();

        if (keyword==null) {
            memoService.delete(memoNum);
            System.out.println("삭제완료");

            paging = new Paging(memberId, section, pageNum);

            int totalCnt = memoService.pagingCount(memberId);

            List<Memo> memoall = memoService.selectAllMemo(paging);
            String totalCntJudge = memoService.totalCntJudge(totalCnt);

            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("totalCntJudge", totalCntJudge);
            model.addAttribute("section", section);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("savedmemo", memoall);
            System.out.println("키워드 없음 실행");


        }else if(keyword!=null){
            memoService.delete(memoNum);
            System.out.println("삭제완료");

            paging = new Paging(memberId, keyword, section, pageNum);
            int totalCnt = memoService.pagingCountSearch(paging);

            List<Memo> memoall = memoService.selectSearchPaging(paging);
            String totalCntJudge =  memoService.totalCntJudge(totalCnt);

            model.addAttribute("totalCntJudge", totalCntJudge);
            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("section", section);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("savedmemo", memoall);
            model.addAttribute("keyword", keyword);
            System.out.println("키워드 있음 실행");

        }

        return "memo/memo";
    }

    @RequestMapping(value="/memo/search")
    public String qnaBoardSearch(@RequestParam(value="keyword", required = false) String keyword, Model model,
                                 @RequestParam(value = "section", defaultValue = "1") int section,
                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 HttpSession session) {

        LoginInfo log = (LoginInfo) session.getAttribute("loginInfo");
        String memberId = log.getId();

        Paging paging = new Paging(memberId, keyword, section, pageNum);
        int totalCnt = memoService.pagingCountSearch(paging);

        List<Memo> memoall = memoService.selectSearchPaging(paging);
        String totalCntJudge =  memoService.totalCntJudge(totalCnt);

        model.addAttribute("totalCntJudge", totalCntJudge);
        model.addAttribute("totalCnt", totalCnt);
        model.addAttribute("section", section);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("savedmemo", memoall);
        model.addAttribute("keyword", keyword);

        return "memo/memo";
    }
}


