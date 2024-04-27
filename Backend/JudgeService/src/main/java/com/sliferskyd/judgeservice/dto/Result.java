package com.sliferskyd.judgeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    private String id;
    private String status;
    private String message;
    private boolean isCorrect;
    private Double executionTime;
}
