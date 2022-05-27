package com.mon.memo.controller;

import com.mon.memo.domain.Login;
import com.mon.memo.domain.LoginInfo;
import com.mon.memo.domain.Member;
import com.mon.memo.domain.Memo;
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
    public String submit(@ModelAttribute Login login, Errors errors, HttpSession session,
                         @RequestParam(value="rememberlogin",required=false) Boolean rememberlogin,
                         HttpServletResponse response, Model model, BindingResult bindingResult
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

            List<Memo> memoall = memoService.savedMemo(memberId);
            System.out.println(memoall+"아이디로 가져온 메모 출력");

            model.addAttribute("savedmemo",memoall);

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
