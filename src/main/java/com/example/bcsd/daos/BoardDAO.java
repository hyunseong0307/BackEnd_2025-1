package com.example.bcsd.daos;


import com.example.bcsd.models.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BoardDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Board> boardMapper = (rs, rowNum) -> new Board(
            rs.getLong("id"),
            rs.getString("name") // DB의 'name' 컬럼과 매핑
    );

    public BoardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Board> findById(Long id) {
        String sql = "select * from board where id = ?";
        Board board = (Board) jdbcTemplate.queryForObject(sql, boardMapper, id);
        return Optional.ofNullable(board);
    }


}
