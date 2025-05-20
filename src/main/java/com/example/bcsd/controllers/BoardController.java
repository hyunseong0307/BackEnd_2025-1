package com.example.bcsd.controllers;

import ch.qos.logback.core.model.Model;
import com.example.bcsd.dtos.BoardDTO;
import com.example.bcsd.services.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/posts")
    public ResponseEntity<?> posts(@RequestParam("boardId") Long boardId, Model model) {
        BoardDTO boardDto = boardService.getBoardsById(boardId);

        return new ResponseEntity<>(boardDto, HttpStatus.OK);

    }
}
