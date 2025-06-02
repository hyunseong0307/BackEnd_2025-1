package com.example.bcsd.services;

import com.example.bcsd.daos.ArticleDAO;
import com.example.bcsd.daos.BoardDAO;
import com.example.bcsd.daos.MemberDAO;
import com.example.bcsd.exceptions.InvalidRequestException;
import com.example.bcsd.exceptions.ResourceNotFoundException;
import com.example.bcsd.models.Article;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final ArticleDAO articleDao;
    private final BoardDAO boardDao;
    private final MemberDAO memberDao;

    public PostService(ArticleDAO articleDao, BoardDAO boardDao, MemberDAO memberDao) {
        this.articleDao = articleDao;
        this.boardDao = boardDao;
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<Article> getArticlesByBoard(int boardId) {
        boardDao.findNameById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 " + boardId + "인 게시판을 찾을 수 없습니다."));
        return articleDao.findByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public String getBoardName(int boardId) {
        return boardDao.findNameById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 " + boardId + "인 게시판을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Article getArticleById(int id) {
        Article article = articleDao.findById(id);
        if (article == null) {
            throw new ResourceNotFoundException("ID가 " + id + "인 게시물을 찾을 수 없습니다.");
        }
        return article;
    }

    @Transactional
    public void createArticle(Article article) {
        if (article.getAuthorId() == 0 || article.getBoardId() == 0 ||
                article.getTitle() == null || article.getTitle().isBlank() ||
                article.getContent() == null || article.getContent().isBlank()) {
            throw new InvalidRequestException("게시물 생성 요청 시 필수 값이 누락되었습니다. (authorId, boardId, title, content)");
        }

        if (memberDao.findById(article.getAuthorId()) == null) {
            throw new InvalidRequestException("ID가 " + article.getAuthorId() + "인 사용자를 찾을 수 없습니다.");
        }

        boardDao.findNameById(article.getBoardId())
                .orElseThrow(() -> new InvalidRequestException("ID가 " + article.getBoardId() + "인 게시판을 찾을 수 없습니다."));

        articleDao.insert(article);
    }

    @Transactional
    public void updateArticle(int id, Article article) {
        getArticleById(id); // 존재 확인

        if (article.getBoardId() != 0) {
            boardDao.findNameById(article.getBoardId())
                    .orElseThrow(() -> new InvalidRequestException("ID가 " + article.getBoardId() + "인 게시판을 찾을 수 없습니다."));
        }

        if (article.getTitle() != null && article.getTitle().isBlank()) {
            throw new InvalidRequestException("게시물 제목은 비워둘 수 없습니다.");
        }

        if (article.getContent() != null && article.getContent().isBlank()) {
            throw new InvalidRequestException("게시물 내용은 비워둘 수 없습니다.");
        }

        articleDao.update(id, article);
    }

    @Transactional
    public void deleteArticle(int id) {
        getArticleById(id); // 존재 확인
        articleDao.delete(id);
    }
}
