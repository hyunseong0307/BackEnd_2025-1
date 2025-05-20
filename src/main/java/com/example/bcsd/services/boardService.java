package com.example.bcsd.services;

import com.example.bcsd.daos.boardDao;
import com.example.bcsd.models.Board;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class boardService {
    private final boardDao boardDao;
    public boardService(boardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards() {
    }

}
