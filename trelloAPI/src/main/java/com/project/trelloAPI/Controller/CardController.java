package com.project.trelloAPI.Controller;

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
@RequestMapping("/boards/{boardId}/cards")
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

        return ResponseEntity.ok(savedCard);
    }


    @GetMapping
    public ResponseEntity<Card> getAllCards(@PathVariable("board_id") Long boardId) {
        List<Card> cards = cardService.getAllCards(boardId);
        return ResponseEntity.ok((Card) cards);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCardById(
            @PathVariable("boardId") Long boardId,
            @PathVariable Long cardId) {
        Optional<Card> cardOptional = cardService.getCardById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            return ResponseEntity.ok(card);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{cardId}")
    public ResponseEntity<Card> updateCard(
            @PathVariable Long boardId,
            @PathVariable Card cardId,
            @RequestBody CardRequest cardRequest) {

        Card updatedCard = cardService.updateCard(cardId);
        if (updatedCard != null) {
            return ResponseEntity.ok(updatedCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(
            @PathVariable Long boardId,
            @PathVariable Card cardId) {

        Optional<Card> card = cardService.getCardById(cardId.getCardId());
        if (card != null) {
            cardService.deleteCard(cardId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}