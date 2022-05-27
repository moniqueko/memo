package com.mon.memo.controller;

import com.mon.memo.domain.Login;
import com.mon.memo.domain.LoginInfo;
import com.mon.memo.domain.Memo;
import com.mon.memo.domain.MemoCommand;
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
        save.setMemo(memo);
        save.setId(log.getId());

        memoService.write(save);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("result", save);

        return map;
    }

    @GetMapping(value = "/memo/memo") //저장된 메모 불러오기
    public String saved(Memo memo, Login login, Errors errors, Model model, HttpSession session,
                           HttpServletResponse response) {

        LoginInfo log = (LoginInfo) session.getAttribute("loginInfo");
        String memberId = log.getId();

            List<Memo> memoall = memoService.savedMemo(memberId);
            System.out.println(memoall+"아이디로 가져온 메모 출력");

            model.addAttribute("savedmemo",memoall);

            return "memo/memo";


    }
}
