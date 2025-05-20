package com.example.bcsd.controllers;

import com.example.bcsd.services.boardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BoardController {
    private final boardService boardService;

    public BoardController(boardService boardService) {
        this.boardService = boardService;
    }
}
