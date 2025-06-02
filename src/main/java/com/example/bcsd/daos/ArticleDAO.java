package com.example.bcsd.daos;

import com.example.bcsd.models.Article;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Repository
public class ArticleDAO {

    @PersistenceContext
    private EntityManager em;

    public Article findById(int id) {
        return em.find(Article.class, id);
    }

    public List<Article> findByBoardId(int boardId) {
        return em.createQuery("SELECT a FROM Article a WHERE a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public void insert(Article article) {
        em.persist(article);
    }

    public void update(int id, Article article) {
        Article target = em.find(Article.class, id);
        if (target != null) {
            target.setBoardId(article.getBoardId());
            target.setTitle(article.getTitle());
            target.setContent(article.getContent());
            target.setUpdatedAt(java.time.LocalDateTime.now());
        }
    }

    public void delete(int id) {
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        }
    }

    public int countByAuthorId(int authorId) {
        Long count = em.createQuery("SELECT COUNT(a) FROM Article a WHERE a.authorId = :authorId", Long.class)
                .setParameter("authorId", authorId)
                .getSingleResult();
        return count.intValue();
    }
}
