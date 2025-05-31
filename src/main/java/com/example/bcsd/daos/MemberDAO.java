package com.example.bcsd.daos;

import com.example.bcsd.models.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberDAO {

    @PersistenceContext
    private EntityManager em;

    public Member findById(int id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    public void insert(Member member) {
        em.persist(member);
    }

    public void update(Member member) {
        Member target = em.find(Member.class, member.getId());
        if (target != null) {
            target.setName(member.getName());
            target.setEmail(member.getEmail());
        }
    }

    public void delete(int id) {
        Member member = em.find(Member.class, id);
        if (member != null) {
            em.remove(member);
        }
    }
    public Optional<Member> findByEmail(String email) {
        List<Member> result = em.createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findFirst();
    }
}