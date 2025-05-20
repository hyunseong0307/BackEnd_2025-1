package com.example.bcsd.daos;

import com.example.bcsd.models.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class boardDao {
    private final JdbcTemplate jdbcTemplate;

    public boardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getboardName(int boardId){
        String sql = "SELECT name FROM board WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, boardId);
    }


}
