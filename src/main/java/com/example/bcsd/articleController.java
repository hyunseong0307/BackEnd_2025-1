package com.example.bcsd;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class articleController {
    private final Map<Integer, String> articles = new HashMap<>();
    private Integer articleId = 1;

    @PostMapping
    public String postArticle(@RequestBody String articleName) {
        articles.put(articleId, articleName);
        return "Item created " + articleId;
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable("id") int id) {
        return articles.get(id);
    }

    @PutMapping("/{id}")
    public String putArticle(@PathVariable int id, @RequestBody String articleName) {
        if(!articles.containsKey(id)) {
            return "Item not found";
        }
        articles.put(id, articleName);
        return "Item updated " + articleName;
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable int id) {
        if(!articles.containsKey(id)) {
            return "Item not found";
        }
        articles.remove(id);
        return "Item deleted " + articleId;
    }

}
