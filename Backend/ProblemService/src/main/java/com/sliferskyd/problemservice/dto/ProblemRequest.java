package com.sliferskyd.problemservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemRequest {
    private String id;
    private String title;
    private String description;
    private String source;
    private List<TestCase> testCases;
    private Double timeLimit; // in seconds
    private Integer memoryLimit; // in MB
}
