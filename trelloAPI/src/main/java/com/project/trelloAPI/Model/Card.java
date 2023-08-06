package com.project.trelloAPI.Model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long cardId;

    @Column(nullable = false)
    public String title;
    public String description;
    public String section;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
//    @ManyToOne
//    @JoinColumn(name= "board_id", nullable = false)
//    @JoinColumn
//    public Board board;

}