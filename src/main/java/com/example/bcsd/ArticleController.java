package com.example.bcsd;

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

    @PostMapping("/article")
    @ResponseBody
    public ResponseEntity<?> createArticle(@RequestBody Map<String, String> body) {
        try {
            Integer authorId = Integer.parseInt(body.get("authorId"));
            Integer boardId = Integer.parseInt(body.get("boardId"));
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

    @GetMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<?> getArticle(@PathVariable Integer id) {
        Article article = articles.get(id);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }
        return ResponseEntity.ok(article);
    }

    @PutMapping("/article/{id}")
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

    @DeleteMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteArticle(@PathVariable Integer id) {
        if (!articles.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }
        articles.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/posts")
    public String showAllArticlesView(Model model) {
        model.addAttribute("articles", new ArrayList<>(articles.values()));
        return "articles"; 
    }
}
