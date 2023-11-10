package LicenseProject.License.controller;

import LicenseProject.License.dto.MemberDTO;
import LicenseProject.License.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입 자동적으로 만들어 준다
    private final MemberService memberService;

    //회원가입 페이지 출력 요청
    @GetMapping("/member/save")
        public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO)
    {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return "index";
    }

    @GetMapping("/member/login")
        public String loginForm(){
        return "login";
    }

}
