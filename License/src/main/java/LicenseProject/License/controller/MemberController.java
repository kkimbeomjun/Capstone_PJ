package LicenseProject.License.controller;

import LicenseProject.License.dto.MemberDTO;
import LicenseProject.License.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult= memberService.login(memberDTO);
        if(loginResult != null){
            session.setAttribute("loginEmail",loginResult.getMemberEmail());
            return "main";
        }else {
            return "login";
        }
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
       String myEmail = (String)session.getAttribute("loginEmail");
       MemberDTO memberDTO =memberService.updateForm(myEmail);
       model.addAttribute("updateMember",memberDTO);
       return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/";
    }

    @GetMapping("/member/logout")
        public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }


    @GetMapping("/member/login")
        public String loginForm(){
        return "login";
    }

    @PostMapping("/member/email-check")
    public  @ResponseBody String emailCheck(@RequestParam("memberEmail")String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
        //        if (checkResult != null){
//            return "ok";
//        }else {
//            return "no";
//        }
    }
    @GetMapping("/member")
    public ResponseEntity<Boolean> checkDuplicate(@RequestParam("memberEmail")String memberEmail){
        boolean isUnique = memberService.isEmailUnique(memberEmail);
        return ResponseEntity.ok(isUnique);
    }

}





