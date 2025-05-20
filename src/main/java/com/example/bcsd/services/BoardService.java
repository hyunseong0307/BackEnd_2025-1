package com.example.bcsd.services;

import com.example.bcsd.daos.BoardDAO;
import com.example.bcsd.dtos.BoardDTO;
import com.example.bcsd.models.Board;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BoardService {
    private final BoardDAO boardDao;
    public BoardService(BoardDAO boardDao) {
        this.boardDao = boardDao;
    }

    @Transactional(readOnly = true)
    public BoardDTO getBoardsById(Long id) {
        Board board = boardDao.findById(id);

        return new BoardDTO(board.getId(), board.getName());
    }

}
