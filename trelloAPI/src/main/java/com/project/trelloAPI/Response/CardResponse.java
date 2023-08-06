package com.project.trelloAPI.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {
    Long cardId;
    String title;
    String description;
    String section;

}
