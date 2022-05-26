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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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

//    @RequestMapping(value = "/write", method = RequestMethod.POST) //회원가입 실행-db전송
//    public String register(Login login, Errors errors, Model model, HttpSession session,
//                           HttpServletResponse response) throws IOException {
//
//        System.out.println("폼 정보받아오기 테스트" + login.getId());
//
//        if (errors.hasErrors()) {
//            return "signin/regi";
//        }
//
//        try {
//            memberRegisterService.register(login);
//            System.out.println("세션저장/회원가입 완료");
//
//            return "memo";
//
//        } catch (Exception e) {
//            response.setContentType("text/html; charset=euc-kr");
//            PrintWriter out = response.getWriter();
//            out.println("<script>alert('아이디 or 이메일 중복'); </script>");
//            //out.println("<script>location.href='/qnaBoard' </script>");
//            out.flush();
//            return "signin/regi";
//        }
//
//    }
}
