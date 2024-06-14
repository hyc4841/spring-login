package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    /***
     * @return null이면 로그인 실패
     */
    public Member Login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
        // Optional에 filter를 바로 걸 수 있음. 또한 람다식 이용 안하면 아래 코드처럼 길어지게 됨.

        /*
        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        Member member = findMemberOptional.get(); // Optional걸려있는 경우 get()으로 꺼내줘야함.
        if (member.getPassword().equals(password)) {
            return member;
        }
        else {
            return null;
        }
         */
    }
}
