package com.project.trelloAPI.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {

    Long BoardId;
    String name;
    String columns;
}
