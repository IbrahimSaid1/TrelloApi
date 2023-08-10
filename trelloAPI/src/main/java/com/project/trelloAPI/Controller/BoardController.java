package com.project.trelloAPI.Controller;


import com.project.trelloAPI.Model.Board;
import com.project.trelloAPI.Repository.BoardRepository;
import com.project.trelloAPI.Request.BoardRequest;
import com.project.trelloAPI.Response.BoardResponse;
import com.project.trelloAPI.Services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/api/board")
@CrossOrigin("*")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @PostMapping
    public Board createBoard(@RequestBody Board newBoard) {
        boardRepository.save(newBoard);
        return newBoard;
    }

    @GetMapping
    public List<Board> getAllBoards() {

            return boardRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long boardId) {
        Board board = boardService.getBoardById(boardId);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequest boardRequest) {
        Board updatedBoard = boardService.updateBoard(boardId, boardRequest);
        if (updatedBoard != null) {
            BoardResponse response = new BoardResponse();
            response.setBoardId(updatedBoard.getBoardId());
            response.setName(updatedBoard.getName());
            //response.setColumns(updatedBoard.getColumns());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        Board board = boardService.getBoardById(boardId);
        if (board != null) {
            boardService.deleteBoard(boardId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
