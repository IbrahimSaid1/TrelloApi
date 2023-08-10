package com.project.trelloAPI.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardRequest {
        String title;
        String description;
        int section;
    }


