package com.example.bcsd.repositories;

import com.example.bcsd.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findByBoardId(int boardId);

    long countByAuthorId(int authorId);
}