package com.example.bcsd;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final Map<Integer, String> articles = new HashMap<>();
    private Integer articleId = 1;

    @PostMapping
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

    @GetMapping("/{id}")
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

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        if (articles.containsKey(id)) {
            articles.remove(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}