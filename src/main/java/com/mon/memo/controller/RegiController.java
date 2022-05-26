package com.mon.memo.controller;

import com.mon.memo.domain.Login;
import com.mon.memo.domain.LoginInfo;
import com.mon.memo.domain.Member;
import com.mon.memo.service.MemberRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@RequiredArgsConstructor
@Controller
public class RegiController {
    private final MemberRegisterService memberRegisterService;

//    @RequestMapping("/register/validateId")
//    @ResponseBody
//    public HashMap<String, Object> validate(Model model, @RequestBody LoginInfo loginInfo,
//                                            HttpServletRequest request) {
//
//        String memberId=loginInfo.getId(); //제이슨에서 받아온 정보=입력한 아이디
//
//        Member member= memberRegisterService.(memberId);
//
//        String valid = member.getMemberId();
//
//        if(!valid.equals("1")){
//            System.out.print("아이디 중복");
//        }else if(valid.equals("1")){
//            System.out.print("아이디 사용 가능");
//        }
//
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("valid",valid); // DB에 존재하는 아이디인지?
//        System.out.print(map);
//
//
//        return map;
//    }
//
//    @RequestMapping("/register/validateEmail")
//    @ResponseBody
//    public HashMap<String, Object> validateEmail(Model model, @RequestBody RegisterRequest regReq,
//                                                 HttpServletRequest request) {
//
//        String memberEmail=regReq.getEmail(); //제이슨에서 받아온 정보=입력한 이메일
//
//        Member member= memberRegisterService.selectByEmail(memberEmail); //이메일 여부만 0,1로 뽑아올때
//
//        String validEmail = member.getMemberEmail();
//
//        if(!validEmail.equals("1")){
//            System.out.print("이메일 중복");
//        }else if(validEmail.equals("1")){
//            System.out.print("이메일 사용 가능");
//        }
//
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("validEmail",validEmail); // DB에 존재하는 아이디인지?
//        System.out.print(map);
//
//
//        return map;
//    }

    @GetMapping("/register/register")
    public String selection(Model model) {
        model.addAttribute("registerForm",new LoginInfo());  //작성폼 받아오기
        return "signin/regi";
    }

    @RequestMapping(value="/register/register", method= RequestMethod.POST) //회원가입 실행-db전송
    public String register(Login login, Errors errors, Model model, HttpSession session,
                           HttpServletResponse response) throws IOException {

        System.out.println("폼 정보받아오기 테스트"+ login.getId());

        if(errors.hasErrors()) {
            return "signin/regi";
        }

        try {
            memberRegisterService.register(login);
            System.out.println("세션저장/회원가입 완료");

            return "memo";

        }catch(Exception e) {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('아이디 or 이메일 중복'); </script>");
            //out.println("<script>location.href='/qnaBoard' </script>");
            out.flush();
            return "signin/regi";
        }


    }

}
