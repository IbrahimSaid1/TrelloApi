package com.project.trelloAPI.Services;

import com.project.trelloAPI.Model.Board;
import com.project.trelloAPI.Repository.BoardRepository;
import com.project.trelloAPI.Request.BoardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;



    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void save(Board newBoard) {
        boardRepository.save(newBoard);
    }


    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).get();
    }

    public Board updateBoard(Long boardId, BoardRequest boardRequest) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board != null) {
            board.setName(boardRequest.getName());
            return boardRepository.save(board);
        }
        return null;
    }
    public void updateBoardObject(Board board){
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}






