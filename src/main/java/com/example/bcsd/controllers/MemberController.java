package com.example.bcsd.controllers;

import com.example.bcsd.models.Member;
import com.example.bcsd.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        // 생성된 리소스의 URI를 반환하는 것이 RESTful API 디자인에 더 적합할 수 있으나,
        // 여기서는 생성된 객체와 함께 201 CREATED를 반환합니다.
        // 또는 ResponseEntity<Void>와 함께 Location 헤더를 사용할 수 있습니다.
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        Member member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable int id, @RequestBody Member member) {
        memberService.updateMember(id, member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable int id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}