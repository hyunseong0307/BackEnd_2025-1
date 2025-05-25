package com.example.bcsd.daos;

import com.example.bcsd.models.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDAO {
    private final JdbcTemplate jdbcTemplate;

    public ArticleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Article> findByBoardId(int boardId) {
        String sql = "SELECT * FROM article WHERE board_id = ?";
        return jdbcTemplate.query(sql, articleRowMapper(), boardId);
    }

    public Article findById(int id) {
        String sql = "SELECT * FROM article WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, articleRowMapper(), id);
    }

    public void insert(Article article) {
        String sql = "INSERT INTO article (author_id, board_id, title, content) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, article.getAuthorId(), article.getBoardId(), article.getTitle(), article.getContent());
    }

    public void update(int id, Article article) {
        String sql = "UPDATE article SET board_id = ?, title = ?, content = ?, modified_date = NOW() WHERE id = ?";
        jdbcTemplate.update(sql, article.getBoardId(), article.getTitle(), article.getContent(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM article WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int countByAuthorId(int authorId) {
        String sql = "SELECT COUNT(*) FROM article WHERE author_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, authorId);
        return (count != null) ? count : 0;
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article dto = new Article();
            dto.setId(rs.getInt("id"));
            dto.setAuthorId(rs.getInt("author_id"));
            dto.setBoardId(rs.getInt("board_id"));
            dto.setTitle(rs.getString("title"));
            dto.setContent(rs.getString("content"));
            dto.setCreatedAt(rs.getTimestamp("created_date").toLocalDateTime());
            dto.setUpdatedAt(rs.getTimestamp("modified_date").toLocalDateTime());
            return dto;
        };
    }
}
