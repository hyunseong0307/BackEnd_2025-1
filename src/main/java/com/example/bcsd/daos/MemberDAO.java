package com.example.bcsd.daos;

import com.example.bcsd.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member dto = new Member();
            dto.setId(rs.getInt("id"));
            dto.setEmail(rs.getString("email"));
            dto.setName(rs.getString("name"));
            dto.setPassword(rs.getString("password"));
            return dto;
        };
    }

    public Member findById(int id) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM Member WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper(), id);
    }

    public List<Member> findAll() {
        String sql = "SELECT * FROM Member";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    public void insert(Member member) {
        String sql = "INSERT INTO Member (name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword());
    }

    public void updateMember(Member member) {
        String sql = "UPDATE Member SET name = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword(), member.getId());
    }

    public void deleteMember(int id) {
        String sql = "DELETE FROM Member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}