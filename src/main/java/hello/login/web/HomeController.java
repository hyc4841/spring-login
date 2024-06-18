package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 로그인 처리까지 되는 홈 화면
//    @GetMapping("/")
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

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        // 로그인
        if (member == null) { // 데이터베이스에 없을 수도 있으니까 확인
            return "home";
        }

        model.addAttribute("member", member); // 로그인한 유저를 표시하기 위해 뷰로 데이터를 보냄
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false); // 로그인 안한 유저가 홈 화면에 접속 했는데 세션을 생성해버리면 안되므로 false로 한다.
        if (session == null) {
            return "home";
        }

        // 세션 관리자에 저장된 회원 정보 조회
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션에 값이 없으면 홈으로
        if (loginMember == null) { // 데이터베이스에 없을 수도 있으니까 확인
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember); // 로그인한 유저를 표시하기 위해 뷰로 데이터를 보냄
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        // 세션에 값이 없으면 홈으로
        if (loginMember == null) { // 데이터베이스에 없을 수도 있으니까 확인
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember); // 로그인한 유저를 표시하기 위해 뷰로 데이터를 보냄
        return "loginHome";
    }


}