package com.example.bcsd.controllers;

import com.example.bcsd.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam int boardId, Model model) {
        model.addAttribute("boardName", postService.getBoardName(boardId));
        model.addAttribute("articles", postService.getArticlesByBoard(boardId));
        return "posts";
    }
}

