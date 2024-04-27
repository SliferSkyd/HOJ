package com.sliferskyd.submissionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    private String id;
    private String status;
    private String message;
    private boolean isCorrect;
    private Double executionTime;
}
