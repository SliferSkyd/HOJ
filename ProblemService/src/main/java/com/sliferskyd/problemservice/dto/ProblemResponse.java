package com.sliferskyd.problemservice.dto;

import com.sliferskyd.problemservice.model.TestCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemResponse {
    @Id
    private String id;
    private String title;
    private String description;
    private String source;
    private List<TestCase> testCases;
}