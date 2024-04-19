package com.sliferskyd.problemservice.dto;

import com.sliferskyd.problemservice.model.TestCase;
import org.springframework.data.annotation.Id;

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
    @Id
    private String id;
    private String title;
    private String description;
    private String source;
    private List<TestCase> testCases;
}
