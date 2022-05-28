package com.mon.memo.controller;

import com.mon.memo.domain.*;
import com.mon.memo.exception.IdPasswordNotMatchingException;
import com.mon.memo.service.LoginInfoService;
import com.mon.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final LoginInfoService loginInfoService;
    private final MemoService memoService;

    @RequestMapping("/signin")
    public String login(Model model, @CookieValue(value="rememberId", required=false) Cookie cookie) {

        model.addAttribute("loginCommand", new Login()); //이것 지우면 작동 안함...

        if(cookie!=null) {
            System.out.println("쿠키값"+cookie.getValue());
            String cookieval=cookie.getValue();
            model.addAttribute("cookie",cookieval); //쿠키저장되어있으면 모델에 전달
        }

        return "signin/form";
    }

    @RequestMapping("/signout")
    public String logout(HttpSession session) {
        session.invalidate(); //세션에 저장된 모든 데이터를 제거
        return "redirect:/";
    }

    @RequestMapping(value = "/signin/loginExecute", method = RequestMethod.POST)
    public String submit(@ModelAttribute("Paging") Paging paging,
            @ModelAttribute Login login, Errors errors, HttpSession session,
                         @RequestParam(value="rememberlogin",required=false) Boolean rememberlogin,
                         HttpServletResponse response, Model model, BindingResult bindingResult,
                         @RequestParam(value = "section", defaultValue="1") int section,
                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                         @RequestParam(value="keyword", required = false) String keyword
    ) { // 폼에서 로그인 기능을 요청

        if (errors.hasErrors()) {
            return "signin/error";
        }

        try {
            if(rememberlogin!=null) {// 아이디 비밀번호 기억 체크 되어있다면 쿠키생성
                Cookie rememberId = new Cookie("rememberId", login.getId());
                rememberId.setMaxAge(60 * 10);
                rememberId.setPath("/");
                response.addCookie(rememberId);

            }else if(rememberlogin==null){
                Cookie deleteId = new Cookie("rememberId", null) ;
                deleteId.setMaxAge(0) ;
                response.addCookie(deleteId) ;
            }

            LoginInfo loginInfo = loginInfoService.check(login.getId(), login.getEmail(), login.getNo(),
                    login.getPw());

            // 로그인 정보를 기록할 세션 코드
            session.setAttribute("loginInfo", loginInfo);
            String memberId = loginInfo.getId();

            if (keyword==null) {
                paging = new Paging(memberId, section, pageNum);

                int totalCnt = memoService.pagingCount(memberId);

                List<Memo> memoall = memoService.selectSearchPaging(paging);
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

        } catch (IdPasswordNotMatchingException e) {

            Member member = loginInfoService.checkId(login.getId());
            System.out.println(member+"IdPasswordNotMatchingException 멤버정보 출력");

            String valid = member.getMemberId();

            if(!valid.equals("1")){ //회원은 존재할때
                bindingResult.addError(new FieldError("loginCommand","id","비밀번호를 확인해 주세요."));

            }else if(valid.equals("1")){ //회원이 없을때
                bindingResult.addError(new FieldError("loginCommand","id","존재하지 않는 회원입니다."));
            }

            return "signin/form";
        }

    }

}
