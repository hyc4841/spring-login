package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 로그인 처리까지 되는 홈 화면
    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        // 로그인 안한 상태
        if (memberId == null) {
            return "home";
        }

        // 로그인 한 상태
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) { // 데이터베이스에 없을 수도 있으니까 확인
            return "home";
        }

        model.addAttribute("member", loginMember); // 로그인한 유저를 표시하기 위해 뷰로 데이터를 보냄
        return "loginHome";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}