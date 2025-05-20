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

    public String boardName(int boardId){
        String sql = "select name from board where id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, boardId);
    }

    public List<Integer> articlesOnBoardId(int boardId){
        String sql = "select id from article where boardId = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, boardId);
    }
}
