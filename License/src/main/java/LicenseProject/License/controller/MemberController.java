package LicenseProject.License.controller;

import LicenseProject.License.dto.MemberDTO;
import LicenseProject.License.entity.MemberEntity;
import LicenseProject.License.repository.MemberRepository;
import LicenseProject.License.service.MemberService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.security.SecureRandom;


import java.security.Security;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;



@Controller
@RequiredArgsConstructor
public class MemberController {


    // 생성자 주입 자동적으로 만들어 준다
    private final MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("generateRandomMixedValue")
    public String generateRandomMixedValue(Model model, HttpSession session){
        String userEmail = (String) session.getAttribute("loginEmail");

        if (userEmail == null){



            return "main";
        }

        Optional<MemberEntity> existingMember = memberRepository.findByMemberEmail(userEmail);
        if (existingMember.isPresent() && existingMember.get().getRandomMixedValue() != null){
            String message = "이미 구독중 입니다";
            model.addAttribute("message",message);
            return "sub";
        }

        String randomValue = memberService.generateAndSaveRandomValue(userEmail);

        model.addAttribute("randomValue",randomValue);

        String message = "구독 완료";
        model.addAttribute("message",message);

        return "sub";
    }

//    @GetMapping("generateRandomMixedValue")
//    public String generateRandomMixedValue(Model model, HttpSession session){
//        String userEmail = (String) session.getAttribute("loginEmail");
//
//        if (userEmail == null){
//            return "redirect:/member/login";
//        }
//
//        String randomValue = memberService.generateAndSaveRandomValue(userEmail);
//
//        model.addAttribute("randomValue",randomValue);
//
//        return "main";
//    }

//    @GetMapping("/member/subscribe")
//    public String subscribe(Model model, HttpSession session){
//        String userEmail = (String) session.getAttribute("loginEmail");
//
//        MemberEntity existingMember = memberRepository.findByMemberEmail(userEmail);
//
//        if (existingMember !=null && existingMember.getRandomMixedValue() !=null){
//
//            model.addAttribute("message","이무 구독하셨습니다");
//            return "main";
//        }
//
//        String randomValue = generateRandomMixedValue();
//        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.setMemberEmail(userEmail);
//        memberEntity.setRandomMixedValue(randomValue);
//        memberRepository.save(memberEntity);
//
//        model.addAttribute("message","구독이 완료되었습니다. 생성된 키값 : " + randomValue);
//        return "main";
//    }


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

    @GetMapping("/member/sub")
    public String sub()
    {
        return "sub";
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





