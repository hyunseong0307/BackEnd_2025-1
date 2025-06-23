package com.example.bcsd.services;

import com.example.bcsd.exceptions.InvalidRequestException;
import com.example.bcsd.exceptions.ResourceNotFoundException;
import com.example.bcsd.models.Article;
import com.example.bcsd.models.Board;
import com.example.bcsd.models.Member;
import com.example.bcsd.repositories.ArticleRepository;
import com.example.bcsd.repositories.BoardRepository;
import com.example.bcsd.repositories.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public PostService(ArticleRepository articleRepository, BoardRepository boardRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<Article> getArticlesByBoard(int boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new ResourceNotFoundException("ID가 " + boardId + "인 게시판을 찾을 수 없습니다.");
        }
        return articleRepository.findByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public String getBoardName(int boardId) {
        return boardRepository.findById(boardId)
                .map(Board::getName)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 " + boardId + "인 게시판을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Article getArticleById(int id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 " + id + "인 게시물을 찾을 수 없습니다."));
        return article;
    }

    @Transactional
    public void createArticle(Article article) {
        if (article.getAuthor() == null || article.getAuthor().getId() == 0 ||
                article.getBoard() == null || article.getBoard().getId() == 0 ||
                article.getTitle() == null || article.getTitle().isBlank() ||
                article.getContent() == null || article.getContent().isBlank()) {
            throw new InvalidRequestException("게시물 생성 요청 시 필수 값이 누락되었습니다. (authorId, boardId, title, content)");
        }

        Member author = memberRepository.findById(article.getAuthor().getId())
                .orElseThrow(() -> new InvalidRequestException("ID가 " + article.getAuthor().getId() + "인 사용자를 찾을 수 없습니다."));

        Board board = boardRepository.findById(article.getBoard().getId())
                .orElseThrow(() -> new InvalidRequestException("ID가 " + article.getBoard().getId() + "인 게시판을 찾을 수 없습니다."));

        article.setAuthor(author);

        board.addArticle(article);
        boardRepository.save(board);
    }

    @Transactional
    public void updateArticle(int id, Article articleUpdateData) {
        Article existingArticle = getArticleById(id);

        if (articleUpdateData.getTitle() != null) {
            if (articleUpdateData.getTitle().isBlank()) {
                throw new InvalidRequestException("게시물 제목은 비워둘 수 없습니다.");
            }
            existingArticle.setTitle(articleUpdateData.getTitle());
        }

        if (articleUpdateData.getContent() != null) {
            if (articleUpdateData.getContent().isBlank()) {
                throw new InvalidRequestException("게시물 내용은 비워둘 수 없습니다.");
            }
            existingArticle.setContent(articleUpdateData.getContent());
        }
        existingArticle.setUpdatedAt(java.time.LocalDateTime.now());
    }

    @Transactional
    public void deleteArticle(int id) {
        Article article = getArticleById(id);
        Board board = article.getBoard();
        if (board != null) {
            board.getArticles().remove(article);
        } else {
            articleRepository.delete(article);
        }
    }
}