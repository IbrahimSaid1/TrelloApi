package com.project.trelloAPI.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardRequest {

        Long cardId;
        String title;
        String description;
        String section;
    }


