package com.example.bcsd;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    private final Map<Integer, String> articles = new HashMap<>();
    private Integer articleId = 1;

    @PostMapping("/article")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> postArticle(@RequestBody Map<String, String> requestBody) {
        String description = requestBody.get("description");
        if (description == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Description is missing");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Integer currentIdToUse = articleId;
        articles.put(currentIdToUse, description);
        Map<String, Object> response = new HashMap<>();
        response.put("id", currentIdToUse);
        response.put("description", description);
        articleId++;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getArticle(@PathVariable("id") Integer id) {
        String description = articles.get(id);
        if (description != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", id);
            response.put("description", description);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> putArticle(@PathVariable Integer id, @RequestBody Map<String, String> requestBody) {
        String newDescription = requestBody.get("description");
        if (newDescription == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Description is missing for update");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (!articles.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        articles.put(id, newDescription);
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("description", newDescription);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        if (articles.containsKey(id)) {
            articles.remove(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/articles")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getAllArticlesApi() {
        List<Map<String, Object>> allArticlesList = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : articles.entrySet()) {
            Map<String, Object> articleMap = new HashMap<>();
            articleMap.put("id", entry.getKey());
            articleMap.put("description", entry.getValue());
            allArticlesList.add(articleMap);
        }
        return ResponseEntity.ok(allArticlesList);
    }

    @GetMapping("/posts")
    public String getAllArticlesView(Model model) {
        List<Map<String, Object>> articlesForView = articles.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> article = new HashMap<>();
                    article.put("id", entry.getKey());
                    article.put("description", entry.getValue());
                    return article;
                })
                .collect(Collectors.toList());

        model.addAttribute("articles", articlesForView);
        return "articles";
    }
}