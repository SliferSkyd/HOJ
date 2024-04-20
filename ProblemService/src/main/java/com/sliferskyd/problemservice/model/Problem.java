package com.sliferskyd.problemservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "problems")
public class Problem {
    @Id
    private String id;
    private String title;
    private String description;
    private String source;
    private List<TestCase> testCases;
}
