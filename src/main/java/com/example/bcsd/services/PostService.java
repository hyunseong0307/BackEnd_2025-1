package com.example.bcsd.services;

import com.example.bcsd.daos.ArticleDAO;
import com.example.bcsd.daos.BoardDAO;
import com.example.bcsd.models.Article;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final ArticleDAO articleDao;
    private final BoardDAO boardDao;

    public PostService( ArticleDAO articleDao, BoardDAO boardDao) {
        this.articleDao = articleDao;
        this.boardDao = boardDao;
    }

    @Transactional(readOnly = true)
    public List<Article> getArticlesByBoard(int boardId) {
        return articleDao.findByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public String getBoardName(int boardId) {
        return boardDao.findNameById(boardId);
    }
    @Transactional(readOnly = true)
    public Article getArticleById(int id) {
        return articleDao.findById(id);
    }

    @Transactional
    public void createArticle(Article article) {
        articleDao.insert(article);
    }

    @Transactional
    public void updateArticle(int id, Article article) {
        articleDao.update(id, article);
    }

    @Transactional
    public void deleteArticle(int id) {
        articleDao.delete(id);
    }

}
