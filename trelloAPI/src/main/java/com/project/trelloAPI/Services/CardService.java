package com.project.trelloAPI.Services;

import com.project.trelloAPI.Model.Board;
import com.project.trelloAPI.Model.Card;
import com.project.trelloAPI.Repository.BoardRepository;
import com.project.trelloAPI.Repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardService {


    @Autowired
    CardRepository cardRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    public void CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card saveCard(Card newCard) {
        return cardRepository.save(newCard);
    }
    public List<Card> getAllCards(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        return board.getCards();
    }

    public Optional<Card> getCardById(Long cardId) {
        return cardRepository.findById(cardId);
    }

    public Card updateCard(Card card) {
        return cardRepository.save(card);
    }


    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }

}