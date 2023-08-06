package com.project.trelloAPI.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    Long boardId;
    String name;
    String[] columns;
    //Map<Integer, String> columns;



}
