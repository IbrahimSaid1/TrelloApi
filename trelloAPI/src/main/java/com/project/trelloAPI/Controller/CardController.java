package com.project.trelloAPI.Controller;

import com.project.trelloAPI.Model.Board;
import com.project.trelloAPI.Model.Card;
import com.project.trelloAPI.Request.CardRequest;
import com.project.trelloAPI.Services.BoardService;
import com.project.trelloAPI.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards/{board_id}/cards")
@CrossOrigin("*")
public class CardController {


    @Autowired
    private CardService cardService;

    @Autowired
    BoardService boardService;

    @PostMapping
    public ResponseEntity<Card> createCard(
            @PathVariable("board_id") Long boardId,
            @RequestBody CardRequest cardRequest) {
        Card newCard = new Card();
        newCard.setTitle(cardRequest.getTitle());
        newCard.setDescription(cardRequest.getDescription());
        newCard.setSection(cardRequest.getSection());
        Card savedCard = cardService.saveCard(newCard);
        Board board = boardService.getBoardById(boardId);
        List<Card> cardsOfThisBoard = board.getCards();
        cardsOfThisBoard.add(savedCard);
        board.setCards(cardsOfThisBoard);
        boardService.updateBoardObject(board);
        if (board == null) {
            // Log an error or throw an exception to indicate that the board is not found.
        }

        return ResponseEntity.ok(savedCard);
    }



    @GetMapping
    public ResponseEntity<List<Card>> getAllCards(@PathVariable("board_id") Long boardId) {
        List<Card> cards = cardService.getAllCards(boardId);
        return ResponseEntity.ok(cards);
    }


    @GetMapping("/{card_Id}")
    public ResponseEntity<Card> getCardById(
            @PathVariable("board_id") Long boardId,
            @PathVariable Long cardId) {
        Optional<Card> cardOptional = cardService.getCardById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            return ResponseEntity.ok(card);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{card_Id}")
    public ResponseEntity<Card> updateCard(
            @PathVariable("board_id") Long boardId,
            @PathVariable("card_Id") Long cardId,
            @RequestBody CardRequest cardRequest) {

        Optional<Card> cardOptional = cardService.getCardById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            card.setTitle(cardRequest.getTitle());
            card.setDescription(cardRequest.getDescription());
            card.setSection(cardRequest.getSection());

            Card updatedCard = cardService.updateCard(card);
            return ResponseEntity.ok(updatedCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{card_Id}")
    public ResponseEntity<Void> deleteCard(
            @PathVariable("board_id") Long boardId,
            @PathVariable("card_Id") Long cardId) {

        Optional<Card> cardOptional = cardService.getCardById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();

            // Remove the card from the associated board
            Board board = boardService.getBoardById(boardId);
            List<Card> cardsOfThisBoard = board.getCards();
            cardsOfThisBoard.remove(card);
            board.setCards(cardsOfThisBoard);
            boardService.updateBoardObject(board);

            // Delete the card after removing its associations
            cardService.deleteCard(cardId);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}