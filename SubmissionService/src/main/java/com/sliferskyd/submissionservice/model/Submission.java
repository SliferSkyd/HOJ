package com.sliferskyd.submissionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "submissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Submission {
    @Id
    private String id;
    private String problemId;
    private String userId;
    private String language;
    private String code;
    private String status;
    private List<Result> results;
}
