package com.example.bcsd.services;

import com.example.bcsd.daos.ArticleDAO;
import com.example.bcsd.daos.BoardDAO;
import com.example.bcsd.exceptions.InvalidRequestException;
import com.example.bcsd.exceptions.ResourceNotFoundException;
import com.example.bcsd.models.Article;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try {
            boardDao.findNameById(boardId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID가 " + boardId + "인 게시판을 찾을 수 없습니다.");
        }
        return articleDao.findByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public String getBoardName(int boardId) {
        try {
            return boardDao.findNameById(boardId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID가 " + boardId + "인 게시판을 찾을 수 없습니다.");
        }
    }
    @Transactional(readOnly = true)
    public Article getArticleById(int id) {
        try {
            return articleDao.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID가 " + id + "인 게시물을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void createArticle(Article article) {
        if (article.getAuthorId() == 0 || article.getBoardId() == 0 ||
                article.getTitle() == null || article.getTitle().isBlank() ||
                article.getContent() == null || article.getContent().isBlank()) {
            throw new InvalidRequestException("게시물 생성 요청 시 필수 값이 누락되었습니다. (authorId, boardId, title, content)");
        }

//         try { //멤버가 있는지 없는지 검사함. 아직 작성전
//             memberDao.findById(article.getAuthorId());
//         } catch (EmptyResultDataAccessException e) {
//             throw new InvalidRequestException("ID가 " + article.getAuthorId() + "인 사용자를 찾을 수 없습니다.");
//         }

        try {
            boardDao.findNameById(article.getBoardId());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidRequestException("ID가 " + article.getBoardId() + "인 게시판을 찾을 수 없습니다.");
        }

        articleDao.insert(article);
    }

    @Transactional
    public void updateArticle(int id, Article article) {
        Article existingArticle = getArticleById(id);
        if (article.getBoardId() != 0) { // boardId가 업데이트 요청에 포함된 경우에만 확인
            try {
                boardDao.findNameById(article.getBoardId());
            } catch (EmptyResultDataAccessException e) {
                throw new InvalidRequestException("ID가 " + article.getBoardId() + "인 게시판을 찾을 수 없습니다.");
            }
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
        getArticleById(id); //없으면 예외발생
        articleDao.delete(id);
    }

}
