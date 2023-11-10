package LicenseProject.License.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    //회원가입 페이지 출력 요청
    @GetMapping("/member/save")
        public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@RequestParam("memberEmail") String memberEmail
                       ,@RequestParam("memberPassword") String memberPassword)
    {
        System.out.println("memberEmail" + memberEmail);
        System.out.println("MemberController.save");
        return "index";
    }

    @GetMapping("/member/login")
        public String loginForm(){
        return "login";
    }

}
