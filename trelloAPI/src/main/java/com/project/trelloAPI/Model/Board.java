package com.project.trelloAPI.Model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@Entity

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long boardId;
    public String name;
    public String columns;

    @OneToMany(fetch = FetchType.EAGER)
    public List<Card> cards;
    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId() {
        this.boardId = boardId;
    }

}


