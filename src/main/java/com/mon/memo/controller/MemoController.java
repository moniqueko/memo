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
        System.out.println(log+ "세션저장되어있음");
        System.out.println(memo+ "받아온 정보 출력");

        MemoCommand save = new MemoCommand();
        save.setMemo(memo.replaceAll("\"", ""));
        save.setId(log.getId());

        memoService.write(save);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("result", memo);

        return map;
    }

    @GetMapping(value = "/memo/memo") //저장된 메모 불러오기
    public String saved(Memo memo, Login login, Errors errors, Model model, HttpSession session,
    @RequestParam(value = "section", defaultValue="1") int section,
    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        LoginInfo log = (LoginInfo) session.getAttribute("loginInfo");
        String memberId = log.getId();

            //List<Memo> memoall = memoService.savedMemo(memberId);
            //System.out.println(memoall+"아이디로 가져온 메모 출력");
            //model.addAttribute("savedmemo",memoall);


            int totalCnt = memoService.pagingCount(memberId);
            Paging paging = new Paging(section, pageNum);

            System.out.println(totalCnt);
            List<Memo> memoall = memoService.selectAllMemo(paging);
        System.out.println(memoall);
            String totalCntJudge = memoService.totalCntJudge(totalCnt);
        System.out.println(totalCntJudge+" 토탈?");

            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("totalCntJudge", totalCntJudge);
            model.addAttribute("section", section);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("savedmemo", memoall);

            return "memo/memo";


    }
}
