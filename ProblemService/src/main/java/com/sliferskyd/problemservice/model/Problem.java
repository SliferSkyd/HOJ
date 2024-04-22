package com.sliferskyd.problemservice.model;

import com.sliferskyd.problemservice.dto.TestCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "problems")
public class Problem {
    @MongoId
    private String id;
    private String title;
    private String description;
    private String source;
    private List<TestCase> testCases;
    private Double timeLimit; // in seconds
    private Integer memoryLimit; // in MB
}
