package com.example.bcsd.controllers;

import com.example.bcsd.models.Article;
import com.example.bcsd.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam int boardId, Model model) {
        List<Article> articles = postService.getArticlesByBoard(boardId);
        String boardName = postService.getBoardName(boardId);

        model.addAttribute("boardName", boardName);
        model.addAttribute("articles", articles);
        return "posts";
    }
}
