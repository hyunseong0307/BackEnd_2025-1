package com.example.bcsd.controllers;

import com.example.bcsd.models.Article;
import com.example.bcsd.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final PostService postService;

    public ArticleController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Article> getArticlesByBoard(@RequestParam("boardId") int boardId) {
        return postService.getArticlesByBoard(boardId);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable int id) {
        return postService.getArticleById(id);
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@RequestBody Article article) {
        postService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArticle(@PathVariable int id, @RequestBody Article article) {
        postService.updateArticle(id, article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
        postService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
