package com.example.bcsd.services;

import com.example.bcsd.exceptions.DataConflictException;
import com.example.bcsd.exceptions.InvalidRequestException;
import com.example.bcsd.exceptions.ResourceNotFoundException;
import com.example.bcsd.models.Member;
import com.example.bcsd.repositories.ArticleRepository;
import com.example.bcsd.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public Member createMember(Member member) {
        if (member.getName() == null || member.getName().isBlank() ||
                member.getEmail() == null || member.getEmail().isBlank() ||
                member.getPassword() == null || member.getPassword().isBlank()) {
            throw new InvalidRequestException("사용자 생성 요청 시 필수 값이 누락되었습니다. (name, email, password)");
        }

        memberRepository.findByEmail(member.getEmail()).ifPresent(m -> {
            throw new DataConflictException("이미 사용 중인 이메일입니다: " + member.getEmail());
        });

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member getMemberById(int id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 " + id + "인 사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public void updateMember(int id, Member memberUpdateData) {
        Member existingMember = getMemberById(id);

        if (memberUpdateData.getName() != null && memberUpdateData.getName().isBlank()) {
            throw new InvalidRequestException("사용자 이름은 비워둘 수 없습니다.");
        }
        if (memberUpdateData.getEmail() != null && memberUpdateData.getEmail().isBlank()) {
            throw new InvalidRequestException("이메일은 비워둘 수 없습니다.");
        }
        if (memberUpdateData.getPassword() != null && memberUpdateData.getPassword().isBlank()) {
            throw new InvalidRequestException("비밀번호는 비워둘 수 없습니다.");
        }

        if (memberUpdateData.getEmail() != null && !memberUpdateData.getEmail().equalsIgnoreCase(existingMember.getEmail())) {
            Optional<Member> memberWithNewEmail = memberRepository.findByEmail(memberUpdateData.getEmail());
            if (memberWithNewEmail.isPresent() && memberWithNewEmail.get().getId() != id) {
                throw new DataConflictException("이미 다른 사용자가 사용 중인 이메일입니다: " + memberUpdateData.getEmail());
            }
            existingMember.setEmail(memberUpdateData.getEmail());
        }

        if (memberUpdateData.getName() != null && !memberUpdateData.getName().isBlank()) {
            existingMember.setName(memberUpdateData.getName());
        }
        if (memberUpdateData.getPassword() != null && !memberUpdateData.getPassword().isBlank()) {
            existingMember.setPassword(memberUpdateData.getPassword());
        }
    }

    @Transactional
    public void deleteMember(int id) {
        getMemberById(id);

        if (articleRepository.countByAuthorId(id) > 0) {
            throw new InvalidRequestException("ID가 " + id + "인 사용자는 작성한 게시물이 있어 삭제할 수 없습니다.");
        }
        memberRepository.deleteById(id);
    }
}