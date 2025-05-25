package com.example.bcsd.daos;


import com.example.bcsd.models.Board;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BoardDAO {
    private final JdbcTemplate jdbcTemplate;

    public BoardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String findNameById(int id) {
        String sql = "SELECT name FROM board WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }
}
