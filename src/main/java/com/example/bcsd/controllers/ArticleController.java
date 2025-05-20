package com.example.bcsd.controllers;

import com.example.bcsd.models.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ArticleController {

    private final Map<Integer, Article> articles = new HashMap<>();
    private Integer articleId = 1;

    @GetMapping("/posts")
    public String showArticlesView( @RequestParam(name = "boardId", required = false) Integer boardId, Model model) {

        List<Article> allArticles = new ArrayList<>(articles.values());
        allArticles.sort(Comparator.comparing(Article::getId));
        Map<String, Object> boardModelData = new HashMap<>();

        if (boardId != null) {
            boardModelData.put("id", boardId);
            boardModelData.put("name", "게시판 " + boardId);
            model.addAttribute("boardId", boardId);
        } else {
            boardModelData.put("name", "전체");
        }

        model.addAttribute("board", boardModelData);
        model.addAttribute("articles", allArticles);

        return "boards";
    }

    @GetMapping("/articles")
    @ResponseBody
    public ResponseEntity<List<Article>> getArticlesByBoardId(
            @RequestParam(name = "boardId", required = false) Integer boardId) { // boardId 파라미터는 받지만, 필터링에 사용되지 않음

        List<Article> allArticles = new ArrayList<>(articles.values());

        allArticles.sort(Comparator.comparing(Article::getId));

        return ResponseEntity.ok(allArticles);
    }

    @PostMapping("/articles")
    @ResponseBody
    public ResponseEntity<?> createArticle(@RequestBody Map<String, String> body) {
        try {
            Integer authorId = Integer.parseInt(body.get("author_id"));
            Integer boardId = Integer.parseInt(body.get("board_id"));
            String title = body.get("title");
            String content = body.get("content");

            if (title == null || content == null) {
                return ResponseEntity.badRequest().body("Title or content is missing");
            }

            Article article = new Article(articleId, authorId, boardId, title, content, LocalDateTime.now(), LocalDateTime.now());
            articles.put(articleId, article);
            articleId++;

            return ResponseEntity.status(HttpStatus.CREATED).body(article);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid input");
        }
    }

    @GetMapping("/articles")
    @ResponseBody
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(new ArrayList<>(articles.values()));
    }

    @GetMapping("/articles/{id}")
    @ResponseBody
    public ResponseEntity<?> getArticle(@PathVariable Integer id) {
        Article article = articles.get(id);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }
        return ResponseEntity.ok(article);
    }

    @PutMapping("/articles/{id}")
    @ResponseBody
    public ResponseEntity<?> updateArticle(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        Article article = articles.get(id);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }

        if (body.containsKey("title")) {
            article.setTitle(body.get("title"));
        }
        if (body.containsKey("content")) {
            article.setContent(body.get("content"));
        }

        article.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/articles/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteArticle(@PathVariable Integer id) {
        if (!articles.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }
        articles.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
