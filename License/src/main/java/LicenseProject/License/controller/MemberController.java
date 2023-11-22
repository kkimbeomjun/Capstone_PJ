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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import java.io.IOException;
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
    public String generateRandomMixedValue(Model model, HttpSession session,@RequestParam("months") int months){
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
        String randomValue = memberService.generateAndSaveRandomValueWithExpiration(userEmail,months);
        model.addAttribute("randomValue",randomValue);


        String message = months + "달 구독 완료";
        model.addAttribute("message",message);
        return "sub";
    }

    @GetMapping("/member/main")
        public String mainForm(){
        return "main";

    }

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

        return "login";
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

    @GetMapping("/member/download")
    public String download(){
        return "download";

    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() throws IOException {
        String filePath = "C:\\Users\\JUN\\Desktop\\study\\spring.zip"; // 예시 경로

        // 파일 resource 읽기
        Resource resource = new FileSystemResource(filePath);
        MediaType mediaType = new MediaType("application", "zip");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(mediaType)
                .body(resource);
    }

    @GetMapping("/member/downloadmain")
    public String downloadmain(){
        return "downloadmain";

    }

    @GetMapping("/downloadmain")
    public ResponseEntity<Resource> downloadFile1() throws IOException {
        String filePath = "C:\\Users\\JUN\\Desktop\\study\\spring.zip"; // 예시 경로

        // 파일 resource 읽기
        Resource resource = new FileSystemResource(filePath);
        MediaType mediaType = new MediaType("application", "zip");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(mediaType)
                .body(resource);
    }


}





