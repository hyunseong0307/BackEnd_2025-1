package com.example.bcsd.controllers;

import com.example.bcsd.dtos.LoginDto;
import com.example.bcsd.dtos.SignupDto;
import com.example.bcsd.models.Member;
import com.example.bcsd.repositories.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SessionController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @GetMapping("/signup")
    public String signupForm() {
        return "signup"; // templates/login.html 파일 필요
    }

    @PostMapping("/signup")
    public String signup(SignupDto request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "비밀번호가 일치하지 않습니다.";
        }

        if (memberRepository.findByAccount(request.getAccount()).isPresent()) {
            return "이미 존재하는 계정입니다.";
        }

        Member member = new Member();
        member.setAccount(request.getAccount());
        member.setPassword(request.getPassword());
        member.setEmail(request.getEmail());
        member.setName(request.getName());
        memberRepository.save(member);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(LoginDto request, HttpSession session) {
        Optional<Member> optionalMember = memberRepository.findByAccount(request.getAccount());

        if (optionalMember.isEmpty()) return "존재하지 않는 계정입니다.";
        Member member = optionalMember.get();

        if (!member.getPassword().equals(request.getPassword())) {
            return "비밀번호가 틀렸습니다.";
        }

        session.setAttribute("loginMember", member); // 세션 저장
        return "redirect:/";
    }
}
